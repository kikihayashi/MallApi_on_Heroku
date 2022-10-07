package com.kikihayashi.springboot_mall.service.implement;

import com.kikihayashi.springboot_mall.dao.UserDao;
import com.kikihayashi.springboot_mall.dto.UserRegisterRequest;
import com.kikihayashi.springboot_mall.model.User;
import com.kikihayashi.springboot_mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }
}
