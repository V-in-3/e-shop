package com.coding.product.controllers;

import com.coding.product.models.Product;
import com.coding.product.models.User;
import com.coding.product.models.UserRole;
import com.coding.product.repositories.OrderRepository;
import com.coding.product.repositories.ProductRepository;
import com.coding.product.repositories.UserRepository;
import com.coding.product.services.OrderService;
import com.coding.product.services.ProductService;
import com.coding.product.services.UserService;
import com.coding.product.validator.UserValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;
    @MockBean
    private OrderService orderService;
    @MockBean
    private ProductService productService;
    @MockBean UserRepository uRepo;
    @MockBean
    ProductRepository pRepo;
    @MockBean
    OrderRepository oRepo;
    @MockBean UserValidator validator;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test show view-products page Success")
    void index() throws Exception {
        Product p1 = new Product(1L,"Big Rony", BigDecimal.valueOf(243.43));
        Product p2 = new Product(4L,"Old car FORD",BigDecimal.valueOf(800.00));

        List<Product> products = List.of(p1, p2);
        PageImpl<Product> page = new PageImpl<>(products);

        User user = new User(10L, "name customer", "Stringemail@gmail.com", "12345678", UserRole.AUTHORIZED_USER, "12345678");

        when(userService.registerUser(any())).thenReturn(user);
        when(productService.findPaginated(any(), any(), any())).thenReturn(page);
        this.mockMvc
            .perform(MockMvcRequestBuilders.get("/"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("view-products"))
            .andExpect(MockMvcResultMatchers.model().attribute("pageProduct", page))
            .andExpect(MockMvcResultMatchers.model().attribute("currentPage", 1))
            .andExpect(MockMvcResultMatchers.model().attribute("products", products));
    }
}