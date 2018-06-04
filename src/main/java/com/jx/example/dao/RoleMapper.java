package com.jx.example.dao;

import java.util.List;

import com.jx.example.base.BaseMapper;
import com.jx.example.entity.Role;

public interface RoleMapper extends BaseMapper<Role> {
	public List<Role> findRoleByUserId(int userId);
}