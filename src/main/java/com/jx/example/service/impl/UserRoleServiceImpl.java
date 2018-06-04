package com.jx.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jx.example.base.BaseServiceImpl;
import com.jx.example.dao.UserRoleMapper;
import com.jx.example.entity.UserRole;
import com.jx.example.service.IUserRoleService;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月4日 上午9:51:46
 *
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements IUserRoleService {
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	public void setUserRoleMapper() {
        super.setBaseMapper(userRoleMapper);
    }

}
