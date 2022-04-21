package com.coding.product.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
  NOT_ADDED(-1L),
  ADDED(-2L),
  PROCESSED(-3L),
  COMPLETED(-4L),
  CANCELED(-5L);

  private final Long orderId;
}
