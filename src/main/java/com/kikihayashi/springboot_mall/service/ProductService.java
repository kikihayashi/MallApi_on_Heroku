package com.kikihayashi.springboot_mall.service;

import com.kikihayashi.springboot_mall.dto.ProductRequest;
import com.kikihayashi.springboot_mall.model.Product;

public interface ProductService {
    Product getProductById(Integer id);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer id, ProductRequest productRequest);
    void deleteProduct(Integer id);
}
