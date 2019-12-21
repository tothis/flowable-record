package com.example.mapper;

import org.flowable.spring.security.UserDto;

import java.util.List;

/**
 * @author admin
 * @version V1.0
 * @time 2019/10/31 13:44
 * @description
 */
public interface UserMapper {

    List<UserDto> selectFlowUserByUserName(UserDto userDto);

    int insertFlowUser(UserDto userDto);
}