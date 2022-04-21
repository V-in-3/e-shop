package com.coding.product.models;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(min = 3, message = "Title must be greater than 3 characters")
  private String title;

  private String createdBy;
  private BigDecimal price;
  private Integer discount;

  private int likes;

  @Column(updatable = false)
  private Date createdAt;
  private Date updatedAt;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "orders",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id"))
  private List<User> users;

  public Product() {}

  public Product(Long id, String title, BigDecimal price) {
    this.id = id;
    this.title = title;
    this.price = price;
  }

  public Product(Long id, String title, BigDecimal price, Integer discount, int likes) {
    this.id = id;
    this.title = title;
    this.price = price;
    this.discount = discount;
    this.likes = likes;
  }

  public Product(String title, BigDecimal price, Integer discount, int likes) {
    this.title = title;
    this.price = price;
    this.discount = discount;
    this.likes = likes;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public int getLikes() {
    return likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Integer getDiscount() {
    return discount;
  }

  public void setDiscount(Integer discount) {
    this.discount = discount;
  }
}
