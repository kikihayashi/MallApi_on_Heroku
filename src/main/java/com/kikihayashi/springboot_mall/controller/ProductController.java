package com.kikihayashi.springboot_mall.controller;

import com.kikihayashi.springboot_mall.model.Product;
import com.kikihayashi.springboot_mall.service.ProductService;
import org.apache.catalina.util.Introspection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
        Product product =  productService.getProductById(id);

        if(product!=null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
