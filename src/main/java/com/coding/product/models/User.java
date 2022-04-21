package com.coding.product.models;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(min = 3, message = "Need at least 3 characters")
  private String name;

  @Email(message = "Email invalid")
  private String email;

  @Size(min = 8, message = "Need at least 8 characters")
  private String password;

  private UserRole userRole;

  @Transient private String passwordConfirmation;

  @Column(updatable = false)
  private Date createdAt;

  private Date updatedAt;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "orders",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "product_id"))
  private List<Product> products;

  public User() {}

  public User(Long id, String name, String email, String password, UserRole userRole, String passwordConfirmation) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.userRole = userRole;
    this.passwordConfirmation = passwordConfirmation;
  }

  public User(String email, String password, String passwordConfirmation) {
    this.email = email;
    this.password = password;
    this.passwordConfirmation = passwordConfirmation;
  }

  public List<Product> getProducts() {
    return products;
  }

  public void setProducts(List<Product> products) {
    this.products = products;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPasswordConfirmation() {
    return passwordConfirmation;
  }

  public void setPasswordConfirmation(String passwordConfirmation) {
    this.passwordConfirmation = passwordConfirmation;
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

  @PrePersist
  protected void onCreate() {
    this.createdAt = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = new Date();
  }

  public UserRole getUserRole() {
    return userRole;
  }

  public void setUserRole(UserRole userRole) {
    this.userRole = userRole;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;
    User user = (User) o;
    return getId().equals(user.getId())
        && getName().equals(user.getName())
        && getEmail().equals(user.getEmail())
        && getPassword().equals(user.getPassword())
        && getPasswordConfirmation().equals(user.getPasswordConfirmation());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(),
        getName(),
        getEmail(),
        getPassword(),
        getPasswordConfirmation());
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", userRole=" + userRole +
            '}';
  }
}
