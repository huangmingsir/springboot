package com.jx.example.dao;

import com.jx.example.base.BaseMapper;
import com.jx.example.entity.User;

public interface UserMapper extends BaseMapper<User> {
	public User findUserByPhoneOrEmail(String username);
}