package com.jx.example.dao;

import java.util.List;

import com.jx.example.base.BaseMapper;
import com.jx.example.entity.Permission;

public interface PermissionMapper extends BaseMapper<Permission> {
	public List<Permission> findPermissionByRoleId(int roleId);
}