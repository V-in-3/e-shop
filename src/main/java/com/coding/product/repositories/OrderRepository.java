package com.coding.product.repositories;

import com.coding.product.models.Orders;
import com.coding.product.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
  List<Orders> findAll();

  Page<Orders> findAll(Pageable pageable);

  Orders findOrdersByUserAndProduct_Id(User user, long productId);

  List<Orders> findOrdersByUser(User user);
}
