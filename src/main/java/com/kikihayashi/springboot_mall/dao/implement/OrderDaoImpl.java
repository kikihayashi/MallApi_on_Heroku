package com.kikihayashi.springboot_mall.dao.implement;

import com.kikihayashi.springboot_mall.dao.OrderDao;
import com.kikihayashi.springboot_mall.dto.OrderQueryParams;
import com.kikihayashi.springboot_mall.dto.ProductQueryParams;
import com.kikihayashi.springboot_mall.model.Order;
import com.kikihayashi.springboot_mall.model.OrderItem;
import com.kikihayashi.springboot_mall.rowmapper.OrderItemRowMapper;
import com.kikihayashi.springboot_mall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.remoting.jaxws.SimpleHttpServerJaxWsServiceExporter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(Integer userId, int totalAmount) {
        String sqlCommand = "INSERT INTO order_info (user_id, total_amount, created_date, last_modified_date) " +
                "VALUES(:userId, :totalAmount, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalAmount", totalAmount);

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyholder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sqlCommand, new MapSqlParameterSource(map), keyholder, new String[]{"order_id"});

        int orderId = keyholder.getKey().intValue();

        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        String sqlCommand = "INSERT INTO order_item(order_id, product_id, quantity, amount) " +
                "VALUES(:orderId, :productId, :quantity, :amount)";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];

        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);

            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderId", orderId);
            parameterSources[i].addValue("productId", orderItem.getProductId());
            parameterSources[i].addValue("quantity", orderItem.getQuantity());
            parameterSources[i].addValue("amount", orderItem.getAmount());
        }
        namedParameterJdbcTemplate.batchUpdate(sqlCommand, parameterSources);
    }

    @Override
    public Order getOrderById(Integer orderId) {

        String sqlCommand = "SELECT order_id,user_id,total_amount,created_date,last_modified_date " +
                "FROM order_info WHERE order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<Order> orderList = namedParameterJdbcTemplate.query(sqlCommand, map, new OrderRowMapper());

        return (orderList.size() > 0) ? orderList.get(0) : null;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {

        String sqlCommand = "SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount," +
                "p.product_name, p.product_name, p.image_url " +
                "FROM order_item AS oi " +
                "LEFT JOIN product AS p ON oi.product_id = p.product_id " +
                "WHERE oi.order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<OrderItem> orderItemList = namedParameterJdbcTemplate.query(sqlCommand, map, new OrderItemRowMapper());

        return orderItemList;
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        String sqlCommand = "SELECT COUNT(*) FROM order_info WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        //查詢條件
        sqlCommand = addFilteringSql(orderQueryParams, map, sqlCommand);

        Integer total = namedParameterJdbcTemplate.queryForObject(sqlCommand, map, Integer.class);

        return total;
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        String sqlCommand = "SELECT order_id, user_id, total_amount ,created_date, last_modified_date " +
                "FROM order_info WHERE 1=1";

        Map<String, Object> map = new HashMap<>();

        //查詢條件
        sqlCommand = addFilteringSql(orderQueryParams, map, sqlCommand);

        //排序
        sqlCommand += " ORDER BY created_date DESC";

        //分頁
        sqlCommand += " LIMIT :limit OFFSET :offset";
        map.put("limit", orderQueryParams.getLimit());
        map.put("offset", orderQueryParams.getOffset());

        List<Order> orderList = namedParameterJdbcTemplate.query(sqlCommand, map, new OrderRowMapper());

        return orderList;
    }

    private String addFilteringSql(OrderQueryParams params, Map<String, Object> map, String sqlCommand) {
        if (params.getUserId() != null) {
            sqlCommand += " AND user_id = :userId";
            map.put("userId", params.getUserId());
        }
        return sqlCommand;
    }
}
