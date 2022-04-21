package com.coding.product.services;

import com.coding.product.models.*;
import com.coding.product.repositories.OrderRepository;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public Page<Orders> findPaginated(
      User currentUser, PageRequest pageable, String sortField, String order) {
    int pageSize = pageable.getPageSize();
    int currentPage = pageable.getPageNumber();
    int startItem = currentPage * pageSize;

    List<Orders> list;
    List<Orders> products = null;
    List<Orders> productList = null;

    if (currentUser.getUserRole().equals(UserRole.MANAGER)
        || currentUser.getUserRole().equals(UserRole.ADMIN)) {
      Page<Orders> page = orderRepository.findAll(pageable);
      productList = page.getContent();
    } else if (currentUser.getUserRole().equals(UserRole.AUTHORIZED_USER)) {
      productList = orderRepository.findOrdersByUser(currentUser);
    }

    if (productList != null) {
      if (sortField.equals("id")) {
        products =
            productList.stream()
                .sorted(Comparator.comparing(Orders::getId))
                .collect(Collectors.toList());
      }

      if (sortField.equals("title")) {
        products =
            productList.stream()
                .sorted(Comparator.comparing(o -> o.getProduct().getTitle()))
                .collect(Collectors.toList());
      }

      if (sortField.equals("customer")) {
        products =
            productList.stream()
                .sorted(Comparator.comparing(o -> o.getUser().getEmail()))
                .collect(Collectors.toList());
      }

      if (sortField.equals("discount")) {
        products =
            productList.stream()
                .sorted(
                    (o1, o2) -> {
                      if (o1.getProduct().getDiscount() == null
                          && o2.getProduct().getDiscount() == null) {
                        return 0;
                      }
                      if (o1.getProduct().getDiscount() != null
                          && o2.getProduct().getDiscount() == null) {
                        return -1;
                      }
                      if (o1.getProduct().getDiscount() == null
                          && o2.getProduct().getDiscount() != null) {
                        return 1;
                      }
                      return o2.getProduct().getDiscount().compareTo(o1.getProduct().getDiscount());
                    })
                .collect(Collectors.toList());
      }

      if (sortField.equals("price")) {
        products =
            productList.stream()
                .sorted(Comparator.comparing(o -> o.getProduct().getPrice()))
                .collect(Collectors.toList());
      }
    }

    assert products != null;

    if (products.size() < startItem) {
      list = Collections.emptyList();
    } else {
      int toIndex = Math.min(startItem + pageSize, products.size());
      list = products.subList(startItem, toIndex);
    }

    return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), products.size());
  }

  public void setOrderStatus(User user, Product product) {
    Orders order = orderRepository.findOrdersByUserAndProduct_Id(user, product.getId());
    if (order == null) return;
    if (user.getUserRole().equals(UserRole.AUTHORIZED_USER)
        && (null == order.getStatus() || order.getStatus().equals(OrderStatus.CANCELED))) {
      order.setStatus(OrderStatus.ADDED);
      order.setProcessStatus(OrderStatus.ADDED);
      orderRepository.saveAndFlush(order);
      return;
    }
    if (order.getStatus().equals(OrderStatus.ADDED)
        && user.getUserRole().equals(UserRole.AUTHORIZED_USER)) {
      order.setStatus(OrderStatus.CANCELED);
      order.setProcessStatus(OrderStatus.NOT_ADDED);
      orderRepository.saveAndFlush(order);
    }
  }

  public void updateOrder(Long id, String status) {
    Optional<Orders> optionalOrders = orderRepository.findById(id);
    if (optionalOrders.isPresent() && EnumUtils.isValidEnum(OrderStatus.class, status)) {
      Orders o = optionalOrders.get();
      o.setProcessStatus(OrderStatus.valueOf(status));
      orderRepository.saveAndFlush(o);
    }
  }
}
