package com.kikihayashi.springboot_mall.service;

import com.kikihayashi.springboot_mall.dto.UserLoginRequest;
import com.kikihayashi.springboot_mall.dto.UserRegisterRequest;
import com.kikihayashi.springboot_mall.model.User;

public interface UserService {
    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer id);

    User login(UserLoginRequest userLoginRequest);
}
