package com.kikihayashi.springboot_mall.dao;


import com.kikihayashi.springboot_mall.dto.OrderQueryParams;
import com.kikihayashi.springboot_mall.model.Order;
import com.kikihayashi.springboot_mall.model.OrderItem;

import java.util.List;

public interface OrderDao {
    Integer createOrder(Integer userId, int totalAmount);
    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

    Order getOrderById(Integer orderId);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);
}
