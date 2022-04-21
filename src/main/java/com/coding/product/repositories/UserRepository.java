package com.coding.product.repositories;

import com.coding.product.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByEmail(String email);

  User findByName(String name);

  List<User> findAllByEmail(String email);
}
