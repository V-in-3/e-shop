package com.coding.product.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    BLOCKED_USER(-1L),
    AUTHORIZED_USER(-2L),
    MANAGER(-3L),
    ADMIN(-4L);

    private final Long roleId;
}
