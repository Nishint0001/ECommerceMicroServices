package com.nishintgoyal.ecommerce.inventory_service.service;

import com.nishintgoyal.ecommerce.inventory_service.dto.OrderRequestDto;
import com.nishintgoyal.ecommerce.inventory_service.dto.OrderRequestItemDto;
import com.nishintgoyal.ecommerce.inventory_service.dto.ProductDto;
import com.nishintgoyal.ecommerce.inventory_service.entities.ProductEntity;
import com.nishintgoyal.ecommerce.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Transactional
    public Double reduceStocks(OrderRequestDto orderRequestDto)
    {
        log.info("Reducing the stock");
        double totalPrice=0.0;

        for(OrderRequestItemDto ele : orderRequestDto.getItems())
        {
            Long pId= ele.getProductId();
            Integer quant= ele.getQuantity();

            ProductEntity product=productRepository.findById(pId).orElseThrow(()->new RuntimeException("Product not existt with id :"+pId));

            if(product.getStock()<quant)
            {
                throw new RuntimeException("Only this much stocks is available "+product.getStock()+"Sorry");
            }

            product.setStock(product.getStock()-quant);
            productRepository.save(product);
            totalPrice +=quant*product.getPrice();

        }

        return totalPrice;
    }
}
