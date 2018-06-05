package com.jx.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jx.example.base.BaseServiceImpl;
import com.jx.example.dao.UserMapper;
import com.jx.example.entity.User;
import com.jx.example.service.IUserService;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月4日 上午9:51:46
 *
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public void setUserMapper() {
        super.setBaseMapper(userMapper);
    }

	@Override
	public User findUserByPhoneOrEmail(String username) {
		return userMapper.findUserByPhoneOrEmail(username);
	}

}