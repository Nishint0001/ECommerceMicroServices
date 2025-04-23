package com.nishintgoyal.ecommerce.inventory_service.service;

import com.nishintgoyal.ecommerce.inventory_service.dto.ProductDto;
import com.nishintgoyal.ecommerce.inventory_service.entities.ProductEntity;
import com.nishintgoyal.ecommerce.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService
{
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> getAllInventory()
    {
        log.info("Fetching all inventory items");
        List<ProductEntity> inventoryList=productRepository.findAll();

        List<ProductDto> productDtoList=inventoryList.stream()
                .map(product ->modelMapper.map(product, ProductDto.class))
                .toList();

        return productDtoList;
    }

    public ProductDto getProductById(Long id)
    {
        log.info("Feteching Product with ID:{}",id);
        Optional<ProductEntity> inventory=productRepository.findById(id);
        return inventory.map(item->modelMapper.map(item,ProductDto.class))
                .orElseThrow(()->new RuntimeException("Inventory not found"));

    }




}
