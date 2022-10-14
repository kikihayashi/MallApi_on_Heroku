package com.kikihayashi.springboot_mall.dao.implement;

import com.kikihayashi.springboot_mall.dao.UserDao;
import com.kikihayashi.springboot_mall.dto.UserLoginRequest;
import com.kikihayashi.springboot_mall.dto.UserRegisterRequest;
import com.kikihayashi.springboot_mall.model.User;
import com.kikihayashi.springboot_mall.rowmapper.UserRowMapper;
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
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sqlCommand = "INSERT INTO \"user\" (email, password, created_date, last_modified_date) " +
                "VALUES (:email, :password, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("email", userRegisterRequest.getEmail());
        map.put("password", userRegisterRequest.getPassword());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sqlCommand, new MapSqlParameterSource(map), keyHolder, new String[]{"user_id"});

        int id = keyHolder.getKey().intValue();
        return id;
    }

    @Override
    public User getUserById(Integer id) {
        String sqlCommand = "SELECT user_id,email,password,created_date,last_modified_date " +
                "FROM user WHERE user_id = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", id);

        return queryUser(sqlCommand, map);
    }

    @Override
    public User getUserByEmail(String email) {
        String sqlCommand = "SELECT user_id,email,password,created_date,last_modified_date " +
                "FROM user WHERE email = :email";

        Map<String, Object> map = new HashMap<>();
        map.put("email", email);

        return queryUser(sqlCommand, map);
    }

    private User queryUser(String sqlCommand, Map<String, Object> map) {
        try {
            List<User> userList = namedParameterJdbcTemplate.query(sqlCommand, map, new UserRowMapper());
            if (userList.size() > 0) {
                return userList.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
