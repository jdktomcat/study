package com.jdktomcat.demo.dead.lock.restart.transaction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdktomcat.demo.dead.lock.restart.transaction.mapper.UserMapper;
import com.jdktomcat.demo.dead.lock.restart.transaction.model.User;
import com.jdktomcat.demo.dead.lock.restart.transaction.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> findAllUser() {
        return userMapper.findAllUser();
    }
}
