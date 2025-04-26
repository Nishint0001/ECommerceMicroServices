package com.nishintgoyal.ecommerce.order_service.controller;

import com.nishintgoyal.ecommerce.order_service.client.InventoryOpenFeignClient;
import com.nishintgoyal.ecommerce.order_service.dto.OrderRequestDto;
import com.nishintgoyal.ecommerce.order_service.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class OrderController
{
    private final OrderService orderService;
    private final InventoryOpenFeignClient inventoryOpenFeignClient;

    @GetMapping("/helloOrders")
    public String helloOrders()
    {
        return "Hello from Orders Service";
    }


    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders(HttpServletRequest httpServletRequest)
    {
        log.info("Feteching all orders via controller");

        List<OrderRequestDto> orders=orderService.gtAllOrders();

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable Long id)
    {
        log.info("Feteching order with Id: {} via controllr",id);

        OrderRequestDto orderRequestDto=orderService.getOrderById(id);

        return ResponseEntity.ok(orderRequestDto);
    }

    @PostMapping("/crete-order")
    public ResponseEntity<OrderRequestDto> createOrder(@RequestBody OrderRequestDto orderRequestDto)
    {
        OrderRequestDto orderRequestDto1=orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok(orderRequestDto1);
    }
}
