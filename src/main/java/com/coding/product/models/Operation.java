package com.coding.product.models;

import lombok.Data;

@Data
public class Operation {
    private long id;
    private String name;

    public Operation(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
