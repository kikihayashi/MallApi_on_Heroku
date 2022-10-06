package com.kikihayashi.springboot_mall.dao.implement;

import com.kikihayashi.springboot_mall.constant.ProductCategory;
import com.kikihayashi.springboot_mall.dao.ProductDao;
import com.kikihayashi.springboot_mall.dto.ProductQueryParams;
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
    public List<Product> getProducts(ProductQueryParams params) {
        String sqlCommand = "SELECT product_id,product_name, category, image_url, price, " +
                "stock, description, created_date, last_modified_date " +
                "FROM product WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        sqlCommand = addFilteringSql(params, map, sqlCommand);

        //加上排序條件(註：ORDER BY 不能用map.put方法傳遞參數，得到的結果不會是預期)
        sqlCommand += " ORDER BY " + params.getOrderBy() + " " + params.getSort();

        //加上分頁條件(註：LIMIT、OFFSET 可以用map.put方法傳遞參數)
        sqlCommand += " LIMIT :limit OFFSET :offset";
        map.put("limit", params.getLimit());
        map.put("offset", params.getOffset());

        List<Product> productList = namedParameterJdbcTemplate.query(sqlCommand, map, new ProductRowMapper());

        return productList;
    }

    @Override
    public Integer countProduct(ProductQueryParams params) {
        String sqlCommand = "SELECT COUNT(*) FROM product WHERE 1=1";

        Map<String, Object> map = new HashMap<>();
        sqlCommand = addFilteringSql(params, map, sqlCommand);
        //queryForObject專門查找COUNT(*)用
        Integer count = namedParameterJdbcTemplate.queryForObject(sqlCommand, map, Integer.class);

        return count;
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

        namedParameterJdbcTemplate.update(sqlCommand, new MapSqlParameterSource(map), keyHolder);

        int id = keyHolder.getKey().intValue();

        return id;
    }

    @Override
    public void updateProduct(Integer id, ProductRequest productRequest) {
        String sql = "UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl, " +
                "price=:price, stock=:stock, description=:description, last_modified_date=:lastModifiedDate " +
                "WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", id);
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().name());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());
        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProduct(Integer id) {
        String sql = "DELETE FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", id);

        namedParameterJdbcTemplate.update(sql, map);
    }

    private String addFilteringSql(ProductQueryParams params, Map<String, Object> map, String sqlCommand) {
        if (params.getCategory() != null) {
            sqlCommand += " AND category = :category";
            map.put("category", params.getCategory().name());
        }
        if (params.getSearch() != null) {
            sqlCommand += " AND product_name LIKE :search";
            map.put("search", "%" + params.getSearch() + "%");//百分比不可以放在sqlCommand中，一定要在map裡
        }
        return sqlCommand;
    }


}
