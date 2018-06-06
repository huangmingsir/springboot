package com.jx.example.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jx.example.base.BaseServiceImpl;
import com.jx.example.config.GlobalConfig;
import com.jx.example.dao.UserMapper;
import com.jx.example.entity.User;
import com.jx.example.enumm.DeleteFlagEnum;
import com.jx.example.service.IUserService;
import com.jx.example.util.EncryptUtil;
import com.jx.example.util.RandomCodeUtil;

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

	@Override
	public boolean save(User user) {
		user.setDeleteFlag(DeleteFlagEnum.N.toString());
		user.setCreateTime(new Date());
		String salt = RandomCodeUtil.secureRandomNumberGenerator();
		user.setSalt(salt);
		user.setPassword(EncryptUtil.hashService(GlobalConfig.HASHALGORITHM, salt, GlobalConfig.HASHITERATIONS,
				user.getPassword()));
		int insert = userMapper.insert(user);
		if(insert > 0) {
			return true;
		}
		return false;
	}

}
