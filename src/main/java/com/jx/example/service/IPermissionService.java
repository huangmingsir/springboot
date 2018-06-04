package com.jx.example.service;

import java.util.List;

import com.jx.example.base.IBaseService;
import com.jx.example.entity.Permission;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月4日 上午9:47:53
 *
 */
public interface IPermissionService extends IBaseService<Permission> {
	public List<Permission> findPermissionByRoleId(int roleId);
}
