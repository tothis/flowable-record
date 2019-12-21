package com.example.service;

import org.flowable.spring.security.UserDto;

import java.util.List;

/**
 * @author admin
 * @version V1.0
 * @time 2019/10/31 13:40
 * @description
 */
public interface UserService {

    List<UserDto> selectFlowUserByUserName(UserDto userDto);
}