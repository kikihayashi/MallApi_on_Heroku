package com.kikihayashi.springboot_mall.service;

import com.kikihayashi.springboot_mall.dto.CreateOrderRequest;
import com.kikihayashi.springboot_mall.dto.OrderQueryParams;
import com.kikihayashi.springboot_mall.model.Order;

import java.util.List;


public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrder(OrderQueryParams orderQueryParams);
}
