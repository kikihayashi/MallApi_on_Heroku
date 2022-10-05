package com.kikihayashi.springboot_mall.dao.implement;

import com.kikihayashi.springboot_mall.dao.ProductDao;
import com.kikihayashi.springboot_mall.dto.ProductRequest;
import com.kikihayashi.springboot_mall.model.Product;
import com.kikihayashi.springboot_mall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer id) {
        String sqlCommand = "SELECT product_id,product_name, category, image_url, price, " +
                "stock, description, created_date, last_modified_date " +
                "FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", id);

        List<Product> productList = namedParameterJdbcTemplate.query(sqlCommand, map, new ProductRowMapper());

        return (productList.size() > 0) ? productList.get(0) : null;
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        String sqlCommand = "INSERT INTO product(product_name, category, image_url, price, " +
                "stock, description, created_date, last_modified_date) " +
                "VALUES (:productName, :category, :imageUrl, :price,:stock, " +
                ":description, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().name());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sqlCommand,new MapSqlParameterSource(map), keyHolder);

        int id = keyHolder.getKey().intValue();

        return id;
    }
}
