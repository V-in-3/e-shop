package com.coding.product.controllers;

import com.coding.product.models.Product;
import com.coding.product.models.User;
import com.coding.product.models.UserRole;
import com.coding.product.services.ProductService;
import com.coding.product.services.UserService;
import com.coding.product.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
public class UserController {

  private final UserService userService;
  private final UserValidator validator;
  private final ProductService productService;
  public static final String USER_ID = "userId";

  public UserController(
      UserService userService, UserValidator validator, ProductService productService) {
    this.userService = userService;
    this.validator = validator;
    this.productService = productService;
  }

  @GetMapping
  public String index(
      Model model,
      @RequestParam(required = false, defaultValue = "title") String sort,
      @RequestParam("page") Optional<Integer> page,
      @RequestParam("size") Optional<Integer> size) {

    int currentPage = page.orElse(1);
    int pageSize = size.orElse(5);

    String order =
        String.valueOf(
            ("likes".equals(sort) || "discount".equals(sort)
                ? Sort.by(sort).descending()
                : Sort.by(sort).ascending()));

    PageRequest pageable = PageRequest.of(currentPage - 1, pageSize);

    Page<Product> pageProduct = productService.findPaginated(pageable, sort, order);

    Iterable<Product> products = pageProduct.getContent();

    int totalPages = pageProduct.getTotalPages();

    model.addAttribute("pageProduct", pageProduct);
    model.addAttribute("currentPage", currentPage);
    model.addAttribute("products", products);
    model.addAttribute("sort", sort);

    if (totalPages > 0) {
      List<Integer> pageNumbers =
          IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
      model.addAttribute("pageNumbers", pageNumbers);
    }

    return "view-products";
  }

  @RequestMapping("/redirect")
  public String redirect(@ModelAttribute("registration") User user) {
    return "login-register";
  }

  @PostMapping("/")
  public String register(
      @Valid @ModelAttribute("registration") User user,
      BindingResult result,
      HttpSession session,
      RedirectAttributes redirs) {
    validator.validate(user, result);
    if (result.hasErrors()) {
      redirs.addFlashAttribute("errorEmail", "User with such an email is registered already");
      return "login-register";
    }
    User newUser = userService.registerUser(user);
    session.setAttribute(USER_ID, newUser.getId());
    log.debug("New user {} was registered", newUser);

    return "redirect:/products";
  }

  @PostMapping("/login")
  public String login(
      @RequestParam("email") String email,
      @RequestParam("password") String password,
      HttpSession session,
      RedirectAttributes redirs) {

    if (this.userService.authenticateUser(email, password)) {
      User userCurrent = userService.getUserByEmail(email);
      if (!userCurrent.getUserRole().equals(UserRole.BLOCKED_USER)) {
        session.setAttribute(USER_ID, userCurrent.getId());
        log.debug("User {} was login", userCurrent);
        return "redirect:/products";
      } else {
        redirs.addFlashAttribute("error", "You are blocked. Please call to administrator");
        log.debug(
            "Blocked user {} tried to log in {}",
            userCurrent,
            ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME));
        return "redirect:/block-page";
      }
    } else {
      redirs.addFlashAttribute("error", "Invalid Email/Password");
      log.debug("User {} login error", email);
      return "redirect:/login-error";
    }
  }

  @RequestMapping("/products")
  public String home(
      HttpSession session,
      Model model,
      @RequestParam(required = false, defaultValue = "title") String sort,
      @RequestParam("page") Optional<Integer> page,
      @RequestParam("size") Optional<Integer> size) {
    Long uID = (Long) session.getAttribute(USER_ID);
    User user = userService.findById(uID);
    int currentPage = page.orElse(1);
    int pageSize = size.orElse(5);
    String order =
        String.valueOf(
            ("likes".equals(sort) || "discount".equals(sort)
                ? Sort.by(sort).descending()
                : Sort.by(sort).ascending()));
    PageRequest pageable = PageRequest.of(currentPage - 1, pageSize);
    Page<Product> pageProduct = productService.findPaginated(pageable, sort, order);
    Iterable<Product> products = pageProduct.getContent();
    int totalPages = pageProduct.getTotalPages();
    if (totalPages > 0) {
      List<Integer> pageNumbers =
          IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
      model.addAttribute("pageNumbers", pageNumbers);
    }
    model.addAttribute("sort", sort);
    model.addAttribute("currentPage", currentPage);
    model.addAttribute("pageProduct", pageProduct);
    model.addAttribute("products", products);
    model.addAttribute("user", user);
    return "make-order";
  }

  @RequestMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    log.debug("User {} was logout", session.getId());

    return "redirect:/";
  }

  @RequestMapping("/block-page")
  public String blockedPageShow() {
    return "blocked-page";
  }

  @RequestMapping("/login-error")
  public String loginErrorPageShow() {
    return "login-error";
  }

  @RequestMapping("/user/{id}/blocked")
  public String blockedUser(@PathVariable Long id, HttpSession session) {
    userService.block(id, session);
    log.debug("User {} was blocked by admin", id);

    return "redirect:/orders/admin";
  }

  @RequestMapping("/user/{id}/unblocked")
  public String unblockedUser(@PathVariable Long id, HttpSession session) {
    userService.unblock(id, session);
    log.debug("User {} was unblocked by admin", id);

    return "redirect:/orders/admin";
  }

  @ExceptionHandler(HttpServerErrorException.class)
  public String httpServerErrorException(HttpServerErrorException ex, HttpServletRequest request) {
    log.debug("Error was occurred !!!");

    return "redirect:/login-register";
  }
}
