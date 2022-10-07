package com.kikihayashi.springboot_mall.service.implement;

import com.kikihayashi.springboot_mall.dao.UserDao;
import com.kikihayashi.springboot_mall.dto.UserLoginRequest;
import com.kikihayashi.springboot_mall.dto.UserRegisterRequest;
import com.kikihayashi.springboot_mall.model.User;
import com.kikihayashi.springboot_mall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        //檢查Email是否有人註冊了
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (user != null) {
            log.warn("該email {} 已被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //建立帳號
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        //檢查Email是否存在
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        if (user == null) {
            log.warn("該Email {} 未註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (user.getPassword().equals(userLoginRequest.getPassword())) {
            return user;
        } else {
            log.warn("該Email {} 密碼不正確", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
