package com.coding.product.controllers;

import com.coding.product.models.Product;
import com.coding.product.models.User;
import com.coding.product.models.UserRole;
import com.coding.product.repositories.ProductRepository;
import com.coding.product.services.OrderService;
import com.coding.product.services.ProductService;
import com.coding.product.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

  @MockBean UserService userService;
  @MockBean OrderService orderService;
  @MockBean ProductRepository productRepository;
  @Autowired ProductService productService;
  //private MockMvc mockMvc;

  @Test
  @DisplayName("Test create new product Successfully")
  void validateCreateProduct() {
    Product product = new Product(1L, "Title product", BigDecimal.valueOf(100.90), 3, 0);
    User user =
        new User(1L, "name", "name@gmail.com", "12345678", UserRole.AUTHORIZED_USER, "12345678");
    when(userService.findById(any())).thenReturn(new User());
    product.setCreatedBy(user.getName());
    product.setLikes(0);
    productService.createProduct(product);
    verify(productRepository, times(1)).saveAndFlush(product);
  }

  @Test
  @DisplayName("Test create new product Fail")
  void notValidateCreateProduct() {
    Product product = new Product();
    User user =
        new User(1L, "name", "name@gmail.com", "12345678", UserRole.AUTHORIZED_USER, "12345678");
    when(userService.findById(any())).thenReturn(user);
    product.setCreatedBy(user.getName());
    product.setLikes(0);
    productService.createProduct(null);
    verify(productRepository, times(0)).saveAndFlush(product);
  }
  //
  //  @Test
  //  @DisplayName("Test delete product by Id Successfully")
  //  void validateDeleteProduct() {
  //    String title = new Date().toString();
  //    String resultTitle = UUID.nameUUIDFromBytes(title.getBytes()).toString();
  //    Product product = new Product(resultTitle, BigDecimal.valueOf(100.90), 3, 0);
  //    productService.createProduct(product);
  //    Product product1 = productRepository.findProductByTitle(resultTitle);
  //    productService.deleteProduct(product1.getId());
  //    Assertions.assertThat(productRepository.findById(product.getId())).isEmpty();
  //  }
}
