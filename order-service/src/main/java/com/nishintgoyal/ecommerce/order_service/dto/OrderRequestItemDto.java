package com.nishintgoyal.ecommerce.order_service.dto;

import lombok.Data;
import org.hibernate.id.IntegralDataTypeHolder;

@Data
public class OrderRequestItemDto
{
    private Long id;
    private Long productId;
    private Integer quantity;
}
