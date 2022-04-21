package com.coding.product.services;

import com.coding.product.models.Product;
import com.coding.product.repositories.ProductRepository;
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
public class ProductService {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Page<Product> findPaginated(PageRequest pageable, String sortField, String sortOrder) {

    int pageSize = pageable.getPageSize();
    int currentPage = pageable.getPageNumber();
    int startItem = currentPage * pageSize;

    List<Product> list;
    List<Product> products;
    List<Product> productList;

    productList = productRepository.findAll();

    products = sortListByField(productList, sortField);

    assert products != null;

    if (products.size() < startItem) {
      list = Collections.emptyList();
    } else {
      int toIndex = Math.min(startItem + pageSize, products.size());
      list = products.subList(startItem, toIndex);
    }

    return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), products.size());
  }

  public Product findProduct(Long id) {
    Optional<Product> optionalProduct = productRepository.findById(id);
    return optionalProduct.orElse(null);
  }

  public void createProduct(Product i) {
    productRepository.saveAndFlush(i);
  }

  public void updateProduct(Product product) {
    Optional<Product> optionalProduct = productRepository.findById(product.getId());
    if (optionalProduct.isPresent()) {
      Product i = optionalProduct.get();
      i.setTitle(product.getTitle());
      i.setCreatedBy(product.getCreatedBy());
      i.setLikes(product.getLikes());
      i.setUsers(product.getUsers());
      productRepository.saveAndFlush(i);
    }
  }

  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }

  private List<Product> sortListByField(List<Product> productList, String sortField) {
    List<Product> products = null;

    if (sortField.equals("id")) {
      products =
          productList.stream()
              .sorted(Comparator.comparing(Product::getId))
              .collect(Collectors.toList());
    }

    if (sortField.equals("title")) {
      products =
          productList.stream()
              .sorted(Comparator.comparing(Product::getTitle))
              .collect(Collectors.toList());
    }

    if (sortField.equals("likes")) {
      products =
          productList.stream()
              .sorted(Comparator.comparing(Product::getLikes).reversed())
              .collect(Collectors.toList());
    }
    if (sortField.equals("discount")) {
      products =
          productList.stream()
              .sorted(
                  (o1, o2) -> {
                    if (o1.getDiscount() == null && o2.getDiscount() == null) {
                      return 0;
                    }
                    if (o1.getDiscount() != null && o2.getDiscount() == null) {
                      return -1;
                    }
                    if (o1.getDiscount() == null && o2.getDiscount() != null) {
                      return 1;
                    }
                    return o2.getDiscount().compareTo(o1.getDiscount());
                  })
              .collect(Collectors.toList());
    }

    if (sortField.equals("price")) {
      products =
          productList.stream()
              .sorted(Comparator.comparing(Product::getPrice))
              .collect(Collectors.toList());
    }
    return products;
  }
}
