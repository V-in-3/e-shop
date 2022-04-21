package com.coding.product.repositories;

import com.coding.product.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository
    extends JpaRepository<Product, Long> {
  List<Product> findAll();

  List<Product> findAll(Sort sort);

  Page<Product> findAll(Pageable pageable);

  Product findProductByTitle(String title);
}
