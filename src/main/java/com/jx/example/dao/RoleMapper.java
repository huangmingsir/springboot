package com.jx.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jx.example.base.BaseMapper;
import com.jx.example.entity.Role;
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
	public List<Role> findRoleByUserId(int userId);
}