package com.kikihayashi.springboot_mall.dao;


import com.kikihayashi.springboot_mall.dto.CreateOrderRequest;
import com.kikihayashi.springboot_mall.model.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;

public interface OrderDao {
    Integer createOrder(Integer userId, int totalAmount);
    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
