package com.coding.product.controllers;

import com.coding.product.models.Orders;
import com.coding.product.models.User;
import com.coding.product.services.OrderService;
import com.coding.product.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.coding.product.controllers.UserController.USER_ID;

@Controller
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;
  private final UserService userService;
  static final Logger log = LogManager.getLogger(OrderController.class);
  private final List<String> managerOperations =
      new ArrayList<>(Arrays.asList("PROCESSED", "COMPLETED"));

  private static final String STATUS_FIELD = "status";
  private static final String CURRENT_PAGE = "currentPage";
  private static final String PAGE_LIKED_PRODUCTS = "pageLikedProducts";
  private static final String PRODUCTS = "products";
  private static final String OPERATIONS = "operations";
  private static final String PAGE_NUMBERS = "pageNumbers";
  private final List<String> adminOperations = new ArrayList<>(List.of("CANCELED"));

  public OrderController(OrderService orderService, UserService userService) {
    this.orderService = orderService;
    this.userService = userService;
  }

  @GetMapping("/manager")
  public String indexManager(
      @ModelAttribute("registration") User user,
      Model model,
      HttpSession session,
      @RequestParam(required = false, defaultValue = "id") String sort,
      @RequestParam("page") Optional<Integer> page,
      @RequestParam("size") Optional<Integer> size) {

    Long uID = (Long) session.getAttribute(USER_ID);
    User currentUser = userService.findById(uID);

    int currentPage = page.orElse(1);
    int pageSize = size.orElse(5);

    String order =
        String.valueOf(
            ("id".equals(sort) || STATUS_FIELD.equals(sort) || "processStatus".equals(sort)
                ? Sort.by(sort).descending()
                : Sort.by(sort).ascending()));

    PageRequest pageable = PageRequest.of(currentPage - 1, pageSize);

    try {
      Page<Orders> pageLikedProducts =
          orderService.findPaginated(currentUser, pageable, sort, order);

      Iterable<Orders> products = pageLikedProducts.getContent();

      int totalPages = pageLikedProducts.getTotalPages();

      model.addAttribute(PAGE_LIKED_PRODUCTS, pageLikedProducts);
      model.addAttribute(CURRENT_PAGE, currentPage);
      model.addAttribute(PRODUCTS, products);
      model.addAttribute("sort", sort);
      model.addAttribute(OPERATIONS, managerOperations);

      if (totalPages > 0) {
        List<Integer> pageNumbers =
            IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
        model.addAttribute(PAGE_NUMBERS, pageNumbers);
      }
    } catch (RuntimeException exception) {
      log.error(exception.getMessage());
      return "redirect:/";
    }
    log.debug("Orders data received.");
    return "cabinet-manager";
  }

  @GetMapping("/customer")
  public String indexCustomer(
      @ModelAttribute("registration") User user,
      Model model,
      HttpSession session,
      @RequestParam(required = false, defaultValue = "id") String sort,
      @RequestParam("page") Optional<Integer> page,
      @RequestParam("size") Optional<Integer> size) {

    Long uID = (Long) session.getAttribute(USER_ID);
    User currentUser = userService.findById(uID);

    List<String> operations = new ArrayList<>(Arrays.asList("PROCESSED", "COMPLETED"));

    int currentPage = page.orElse(1);
    int pageSize = size.orElse(5);

    String order =
        String.valueOf(
            ("id".equals(sort) || STATUS_FIELD.equals(sort) || "processStatus".equals(sort)
                ? Sort.by(sort).descending()
                : Sort.by(sort).ascending()));

    PageRequest pageable = PageRequest.of(currentPage - 1, pageSize);

    try {
      Page<Orders> pageLikedProducts =
          orderService.findPaginated(currentUser, pageable, sort, order);

      Iterable<Orders> products = pageLikedProducts.getContent();

      int totalPages = pageLikedProducts.getTotalPages();

      model.addAttribute(PAGE_LIKED_PRODUCTS, pageLikedProducts);
      model.addAttribute(CURRENT_PAGE, currentPage);
      model.addAttribute(PRODUCTS, products);
      model.addAttribute("sort", sort);
      model.addAttribute(OPERATIONS, operations);

      if (totalPages > 0) {
        List<Integer> pageNumbers =
            IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
        model.addAttribute(PAGE_NUMBERS, pageNumbers);
      }
    } catch (RuntimeException exception) {
      log.error(exception.getMessage());
    }
    return "cabinet-customer";
  }

  @GetMapping("/admin")
  public String indexAdmin(
      @ModelAttribute("registration") User user,
      Model model,
      HttpSession session,
      @RequestParam(required = false, defaultValue = "id") String sort,
      @RequestParam("page") Optional<Integer> page,
      @RequestParam("size") Optional<Integer> size) {

    Long uID = (Long) session.getAttribute(USER_ID);
    User currentUser = userService.findById(uID);

    int currentPage = page.orElse(1);
    int pageSize = size.orElse(5);

    String order =
        String.valueOf(
            ("id".equals(sort) || STATUS_FIELD.equals(sort)
                ? Sort.by(sort).descending()
                : Sort.by(sort).ascending()));

    PageRequest pageable = PageRequest.of(currentPage - 1, pageSize);

    try {
      Page<Orders> pageLikedProducts =
          orderService.findPaginated(currentUser, pageable, sort, order);

      Iterable<Orders> products = pageLikedProducts.getContent();

      int totalPages = pageLikedProducts.getTotalPages();

      List<String> operations = new ArrayList<>();
      operations.addAll(adminOperations);
      operations.addAll(managerOperations);

      model.addAttribute(PAGE_LIKED_PRODUCTS, pageLikedProducts);
      model.addAttribute(CURRENT_PAGE, currentPage);
      model.addAttribute(PRODUCTS, products);
      model.addAttribute("sort", sort);
      model.addAttribute(OPERATIONS, operations);

      if (totalPages > 0) {
        List<Integer> pageNumbers =
            IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
        model.addAttribute(PAGE_NUMBERS, pageNumbers);
      }
    } catch (RuntimeException exception) {
      log.error(exception.getMessage());

      return "redirect:/";
    }
    return "cabinet-admin";
  }

  @PostMapping(value = "/{id}/change-manager")
  public String updateManager(@PathVariable("id") Long id, @RequestParam String oper) {
    orderService.updateOrder(id, oper);
    log.debug("Order {} has been updated by manager to status {}", id, oper);

    return "redirect:/orders/manager";
  }

  @PostMapping(value = "/{id}/change-admin")
  public String updateAdmin(@PathVariable("id") Long id, @RequestParam String oper) {
    orderService.updateOrder(id, oper);
    log.debug("Order {} has been updated by admin to status {}", id, oper);

    return "redirect:/orders/admin";
  }
}
