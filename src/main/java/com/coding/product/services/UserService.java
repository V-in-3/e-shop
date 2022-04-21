package com.coding.product.services;

import com.coding.product.models.Orders;
import com.coding.product.models.User;
import com.coding.product.models.UserRole;
import com.coding.product.repositories.OrderRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.coding.product.repositories.UserRepository;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional
public class UserService {
  private final UserRepository userRepository;
  private final OrderRepository orderRepository;

  public UserService(UserRepository userRepository, OrderRepository orderRepository) {
    this.userRepository = userRepository;
    this.orderRepository = orderRepository;
  }

  public User findById(Long id) {
    return this.userRepository.findById(id).orElse(null);
  }

  public User registerUser(User user) {
    String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
    user.setPassword(hashed);
    if (user.getName().toLowerCase().contains("manager".toLowerCase()))
      user.setUserRole(UserRole.MANAGER);
    else if (user.getName().toLowerCase().contains("admin".toLowerCase()))
      user.setUserRole(UserRole.ADMIN);
    else user.setUserRole(UserRole.AUTHORIZED_USER);
    return this.userRepository.saveAndFlush(user);
  }

  public void block(Long id, HttpSession session) {
    Long uID = (Long) session.getAttribute("userId");
    User admin = this.userRepository.findById(uID).orElse(null);
    Orders order = this.orderRepository.getById(id);
    User user = this.userRepository.findById(order.getUser().getId()).orElse(null);
    if (admin != null && Objects.equals(admin.getUserRole(), UserRole.ADMIN) && user != null) {
      user.setUserRole(UserRole.BLOCKED_USER);
      this.userRepository.saveAndFlush(user);
    }
  }

  public void unblock(Long id, HttpSession session) {
    Long uID = (Long) session.getAttribute("userId");
    User admin = this.userRepository.findById(uID).orElse(null);
    Orders order = this.orderRepository.getById(id);
    User user = this.userRepository.findById(order.getUser().getId()).orElse(null);
    if (admin != null && Objects.equals(admin.getUserRole(), UserRole.ADMIN) && user != null) {
      user.setUserRole(UserRole.AUTHORIZED_USER);
      this.userRepository.saveAndFlush(user);
    }
  }

  public User getUserByEmail(String email) {
    return this.userRepository.findByEmail(email);
  }

  public boolean authenticateUser(String email, String password) {
    User user = this.userRepository.findByEmail(email);
    if (user == null) return false;
    return BCrypt.checkpw(password, user.getPassword());
  }
}
