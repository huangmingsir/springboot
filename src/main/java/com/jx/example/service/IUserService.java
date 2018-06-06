package com.jx.example.service;

import com.jx.example.base.IBaseService;
import com.jx.example.entity.User;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月4日 上午9:47:53
 *
 */
public interface IUserService extends IBaseService<User> {
	public boolean save(User user);

	public User findUserByPhoneOrEmail(String username);
}
