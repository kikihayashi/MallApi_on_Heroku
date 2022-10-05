package com.kikihayashi.springboot_mall.service.implement;

import com.kikihayashi.springboot_mall.constant.ProductCategory;
import com.kikihayashi.springboot_mall.dao.ProductDao;
import com.kikihayashi.springboot_mall.dto.ProductRequest;
import com.kikihayashi.springboot_mall.model.Product;
import com.kikihayashi.springboot_mall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProducts(ProductCategory category, String search) {
        return productDao.getProducts(category, search);
    }

    @Override
    public Product getProductById(Integer id) {
        return productDao.getProductById(id);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer id, ProductRequest productRequest) {
        productDao.updateProduct(id, productRequest);
    }

    @Override
    public void deleteProduct(Integer id) {
        productDao.deleteProduct(id);
    }
}
