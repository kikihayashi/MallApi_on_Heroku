package com.kikihayashi.springboot_mall.dao.implement;

import com.kikihayashi.springboot_mall.dao.ProductDao;
import com.kikihayashi.springboot_mall.model.Product;
import com.kikihayashi.springboot_mall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

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
                "FROM product WHERE product_id = :product_id";

        Map<String, Object> map = new HashMap<>();
        map.put("product_id", id);

        List<Product> productList = namedParameterJdbcTemplate.query(sqlCommand, map, new ProductRowMapper());

        return (productList.size() > 0) ? productList.get(0) : null;
    }
}
