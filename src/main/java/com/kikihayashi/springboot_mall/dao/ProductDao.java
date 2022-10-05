package com.kikihayashi.springboot_mall.dao;

import com.kikihayashi.springboot_mall.dto.ProductQueryParams;
import com.kikihayashi.springboot_mall.dto.ProductRequest;
import com.kikihayashi.springboot_mall.model.Product;

import java.util.List;

public interface ProductDao {

    Product getProductById(Integer id);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer id, ProductRequest productRequest);
    void deleteProduct(Integer id);
    List<Product> getProducts(ProductQueryParams params);
}
