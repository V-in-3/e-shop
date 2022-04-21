package com.coding.product.controllers;

import java.util.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.coding.product.services.OrderService;
import com.coding.product.services.ProductService;
import com.coding.product.models.Product;
import com.coding.product.models.User;
import com.coding.product.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.coding.product.controllers.OrderController.log;
import static com.coding.product.controllers.UserController.USER_ID;

@Controller
@RequestMapping("products")
public class ProductController {

  private final ProductService productService;
  private final UserService userService;
  private final OrderService orderService;

  private static final String CALL_ENDPOINT_PRODUCTS = "redirect:/products";

  public ProductController(
      ProductService productService, UserService userService, OrderService orderService) {
    this.productService = productService;

    this.userService = userService;
    this.orderService = orderService;
  }

  @RequestMapping("/new")
  public String createForm(
      @ModelAttribute("product") Product product, Model model, HttpSession session) {
    Long uID = (Long) session.getAttribute(USER_ID);
    User user = userService.findById(uID);
    model.addAttribute("user", user);
    return "new";
  }

  @PostMapping(value = "/new")
  public String create(
      @Valid @ModelAttribute("product") Product product,
      BindingResult result,
      HttpSession session) {
    if (result.hasErrors()) {
      return "new";
    }
    Long uID = (Long) session.getAttribute(USER_ID);
    String userName = userService.findById(uID).getName();
    product.setCreatedBy(userName);
    product.setLikes(0);
    productService.createProduct(product);
    log.debug("User {} liked product {}", uID, product.getTitle());

    return CALL_ENDPOINT_PRODUCTS;
  }

  @RequestMapping(value = "/{id}")
  public String show(@PathVariable("id") Long id, Model model, HttpSession session) {
    Product product = productService.findProduct(id);
    Long uID = (Long) session.getAttribute(USER_ID);
    User user = userService.findById(uID);
    model.addAttribute("user", user);
    model.addAttribute("product", product);

    return "show";
  }

  @PostMapping(value = "/{id}/delete")
  public String delete(@PathVariable("id") Long id) {
    productService.deleteProduct(id);
    log.debug("Product id={} has been deleted", id);

    return CALL_ENDPOINT_PRODUCTS;
  }

  @GetMapping(value = "/{id}/edit")
  public String edit(@PathVariable("id") Long id, Model model) {
    Product product = productService.findProduct(id);
    model.addAttribute("product", product);

    return "edit";
  }

  @PostMapping(value = "/{id}/edit")
  public String update(
      @Valid @ModelAttribute("product") Product product,
      BindingResult result,
      @PathVariable("id") Long id) {
    if (result.hasErrors()) {
      return "edit";
    } else {
      // TODO if manager did not create product use it
      // Long uID = (Long) session.getAttribute("userId");
      // String userName = userService.findById(uID).getName();
      // if (!product1.getCreatedBy().equals(userName)) return "redirect:/e-shop/products";
      Product product1 = productService.findProduct(id);
      product1.setTitle(product.getTitle());
      product1.setPrice(product.getPrice());
      productService.updateProduct(product1);
      log.debug("Product {} has been updated", product.getTitle());

      return CALL_ENDPOINT_PRODUCTS + "/" + product1.getId();
    }
  }

  @RequestMapping(value = "/{id}/like")
  public String like(@PathVariable("id") Long id, HttpSession session) {
    var product = productService.findProduct(id);
    var uID = (Long) session.getAttribute(USER_ID);
    var user = userService.findById(uID);
    var users = product.getUsers();
    product.setLikes(product.getLikes() + 1);
    users.add(user);
    product.setUsers(users);
    productService.updateProduct(product);
    orderService.setOrderStatus(user, product);
    log.debug("User {} likes product {}", uID, product.getTitle());

    return CALL_ENDPOINT_PRODUCTS;
  }

  @RequestMapping(value = "/{id}/unlike")
  public String unlike(@PathVariable("id") Long id, HttpSession session) {
    Product product = productService.findProduct(id);
    User user = userService.findById((Long) session.getAttribute(USER_ID));
    List<User> users = product.getUsers();
    product.setLikes(product.getLikes() - 1);
    users.remove(user);
    product.setUsers(users);
    productService.updateProduct(product);
    orderService.setOrderStatus(user, product);
    log.debug("User {} unlikes product {}}", user.getId(), product.getTitle());

    return CALL_ENDPOINT_PRODUCTS;
  }
}
