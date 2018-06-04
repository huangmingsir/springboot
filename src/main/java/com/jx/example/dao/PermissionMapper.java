package com.jx.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jx.example.base.BaseMapper;
import com.jx.example.entity.Permission;
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
	public List<Permission> findPermissionByRoleId(int roleId);
}