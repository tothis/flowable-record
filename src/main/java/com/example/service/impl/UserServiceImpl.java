package com.example.service.impl;

import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.flowable.spring.security.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author admin
 * @version V1.0
 * @time 2019/10/31 13:40
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserDto> selectFlowUserByUserName(UserDto userDto) {
        return userMapper.selectFlowUserByUserName(userDto);
    }
}