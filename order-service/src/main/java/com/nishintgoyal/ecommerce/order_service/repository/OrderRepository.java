package com.nishintgoyal.ecommerce.order_service.repository;

import com.nishintgoyal.ecommerce.order_service.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity,Long>
{

}
