package com.kikihayashi.springboot_mall.dao;

import com.kikihayashi.springboot_mall.model.Product;

public interface ProductDao {

    Product getProductById(Integer id);
}
