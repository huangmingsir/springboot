package com.jx.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.jx.example.base.BaseServiceImpl;
import com.jx.example.dao.PermissionMapper;
import com.jx.example.entity.Permission;
import com.jx.example.service.IPermissionService;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月4日 上午9:51:46
 *
 */
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements IPermissionService {
	
	@Autowired
	private PermissionMapper permissionMapper;

	public void setPermissionMapperMapper() {
		super.setBaseMapper(permissionMapper);
	}

}
