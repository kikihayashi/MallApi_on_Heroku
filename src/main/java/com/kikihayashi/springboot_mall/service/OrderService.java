package com.kikihayashi.springboot_mall.service;

import com.kikihayashi.springboot_mall.dto.CreateOrderRequest;
import com.kikihayashi.springboot_mall.model.Order;


public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
