package com.nishintgoyal.ecommerce.order_service.service;

import com.nishintgoyal.ecommerce.order_service.client.InventoryOpenFeignClient;
import com.nishintgoyal.ecommerce.order_service.dto.OrderRequestDto;
import com.nishintgoyal.ecommerce.order_service.entities.OrderEntity;
import com.nishintgoyal.ecommerce.order_service.entities.OrderItem;
import com.nishintgoyal.ecommerce.order_service.entities.OrderStatus;
import com.nishintgoyal.ecommerce.order_service.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService
{
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final InventoryOpenFeignClient inventoryOpenFeignClient;

    public List<OrderRequestDto> gtAllOrders()
    {
        log.info("Fetching all orders");

        List<OrderEntity> orderEntities=orderRepository.findAll();

       return  orderEntities.stream().map(record->modelMapper.map(record, OrderRequestDto.class)).toList();
    }

    public OrderRequestDto getOrderById(Long id)
    {
        log.info("Feteching order with ID: {}");
        OrderEntity orderEntity=orderRepository.findById(id).orElseThrow(()->new RuntimeException("Order not found"));

        return modelMapper.map(orderEntity, OrderRequestDto.class);
    }

  //  @Retry(name="inventoryRetry",fallbackMethod = "createOrderFallback")
   // @RateLimiter(name="inventoryRateLimiter",fallbackMethod = "createOrderFallback")

    @CircuitBreaker(name="inventoryCircuit",fallbackMethod ="createOrderFallback")
    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto)
    {
        log.info("calling the createorder");
        Double tprice=inventoryOpenFeignClient.reduceStocks(orderRequestDto);

        OrderEntity orderEntity=modelMapper.map(orderRequestDto, OrderEntity.class);

        for(OrderItem orderItem: orderEntity.getItems())
        {
            orderItem.setOrder(orderEntity);

        }

        orderEntity.setTotalPrice(tprice);
        orderEntity.setOrderStatus(OrderStatus.CONFIRMED);

       OrderEntity savedOrder=orderRepository.save(orderEntity);

       return modelMapper.map(savedOrder, OrderRequestDto.class);

    }

    public OrderRequestDto createOrderFallback(OrderRequestDto orderRequestDto,Throwable throwable)
    {
        log.error("Fallback occurred due to : {}",throwable.getMessage());

        return new OrderRequestDto();
    }
}
