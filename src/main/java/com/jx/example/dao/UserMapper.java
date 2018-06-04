package com.jx.example.dao;

import org.apache.ibatis.annotations.Mapper;

import com.jx.example.base.BaseMapper;
import com.jx.example.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
	public User findUserByPhoneOrEmail(String username);
}