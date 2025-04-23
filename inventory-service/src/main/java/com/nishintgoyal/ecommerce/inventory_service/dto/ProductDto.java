package com.nishintgoyal.ecommerce.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto
{
    private String title;

    private Double price;

    private Integer stock;
}
