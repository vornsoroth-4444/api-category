package org.soroth.procuctapi.service;

import org.soroth.procuctapi.dto.order.CreateOrderRequest;
import org.soroth.procuctapi.dto.order.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
    List<OrderResponse> getAllOrders();
    List<OrderResponse>getAllOrderByCustomer(Integer customerId);

}
