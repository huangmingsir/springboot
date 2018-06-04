package com.jx.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jx.example.base.BaseServiceImpl;
import com.jx.example.dao.RoleMapper;
import com.jx.example.entity.Role;
import com.jx.example.service.IRoleService;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月4日 上午9:51:46
 *
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService {
	
	@Autowired
	private RoleMapper roleMapper;
	
	public void setRoleMapper() {
        super.setBaseMapper(roleMapper);
    }

	@Override
	public List<Role> findRoleByUserId(int userId) {
		return roleMapper.findRoleByUserId(userId);
	}

}
