package com.jx.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jx.example.base.BaseServiceImpl;
import com.jx.example.dao.RolePermissionMapper;
import com.jx.example.entity.RolePermission;
import com.jx.example.service.IRolePermissionService;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月4日 上午9:51:46
 *
 */
@Service
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermission> implements IRolePermissionService {
	
	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	public void setRolePermissionMapperMapper() {
        super.setBaseMapper(rolePermissionMapper);
    }

}
