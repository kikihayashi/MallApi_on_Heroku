package com.kikihayashi.springboot_mall.service;

import com.kikihayashi.springboot_mall.model.Product;

public interface ProductService {
    Product getProductById(Integer id);
}
