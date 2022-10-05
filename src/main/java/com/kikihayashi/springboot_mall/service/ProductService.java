package com.kikihayashi.springboot_mall.service;

import com.kikihayashi.springboot_mall.constant.ProductCategory;
import com.kikihayashi.springboot_mall.dto.ProductRequest;
import com.kikihayashi.springboot_mall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductCategory category, String search);
    Product getProductById(Integer id);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer id, ProductRequest productRequest);
    void deleteProduct(Integer id);


}
