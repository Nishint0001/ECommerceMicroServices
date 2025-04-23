package com.nishintgoyal.ecommerce.inventory_service.repository;

import com.nishintgoyal.ecommerce.inventory_service.controllers.ProductController;
import com.nishintgoyal.ecommerce.inventory_service.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity,Long>
{

}
