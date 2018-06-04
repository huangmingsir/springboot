package com.jx.example.config.shiro;

import java.awt.Menu;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.jx.example.entity.Permission;
import com.jx.example.entity.Role;
import com.jx.example.entity.User;
import com.jx.example.enumm.DeleteFlagEnum;
import com.jx.example.service.IPermissionService;
import com.jx.example.service.IRoleService;
import com.jx.example.service.IUserService;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月4日 上午9:46:03
 *
 */
public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IPermissionService permissionService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		String username = (String) principal.getPrimaryPrincipal();
		User user = (User) SecurityUtils.getSubject().getPrincipal();

		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

		List<Role> roleList = this.roleService.findRoleByUserId(user.getId());
		Set<String> roleSet = new HashSet<>();
		Set<String> permissionSet = new HashSet<>();
		for (Role r : roleList) {
			roleSet.add(r.getRoleName());
			// 添加权限
			List<Permission> permissionList = this.permissionService.findPermissionByRoleId(r.getId());
			for (Permission p : permissionList) {
				permissionSet.add(p.getParentPermissionName());
			}
		}
		simpleAuthorizationInfo.setRoles(roleSet);
		simpleAuthorizationInfo.setStringPermissions(permissionSet);
		return simpleAuthorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName = (String) token.getPrincipal();//登录的账号（不管是邮箱或者手机）
		String password = new String((char[]) token.getCredentials());

		User user = this.userService.findUserByPhoneOrEmail(userName);
		if (user == null) {
			throw new UnknownAccountException("用户名或密码错误！");
		}
//		if (!password.equals(user.getPassword())) {
//			throw new IncorrectCredentialsException("用户名或密码错误！");
//		}
		if (DeleteFlagEnum.Y.toString().equals(user.getDeleteFlag())) {
			throw new LockedAccountException("账号已被锁定,请联系管理员！");
		}
		return new SimpleAuthenticationInfo(user, password, 
				ByteSource.Util.bytes(user.getSalt()),//salt=username+salt
				getName());
	}

}
