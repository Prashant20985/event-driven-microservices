package com.practice.ProductService.command.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRestModel {
    private String name;
    private BigDecimal price;
    private  Integer quantity;
}
