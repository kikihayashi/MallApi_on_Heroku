package com.kikihayashi.springboot_mall.service;

import com.kikihayashi.springboot_mall.dto.CreateOrderRequest;
import org.springframework.stereotype.Component;


public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
