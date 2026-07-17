package org.soroth.procuctapi.restcontrollers;

import lombok.RequiredArgsConstructor;
import org.soroth.procuctapi.dto.order.CreateOrderRequest;
import org.soroth.procuctapi.dto.order.OrderResponse;
import org.soroth.procuctapi.service.OrderService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderRestController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderResponse> getOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping
    public OrderResponse creatOrder(@RequestBody CreateOrderRequest request){
        return orderService.createOrder(request);
    }
}
