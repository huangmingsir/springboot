package com.jx.example.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.support.json.JSONParser;
import com.jx.example.entity.User;
import com.jx.example.log.SystemLogAop;
import com.jx.example.service.IUserService;

@RestController
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private IUserService userService;

	/**
	 * 这个方法必须是POST，因为在FormAuthenticationFilter会进行(request instanceof
	 * HttpServletRequest) &&
	 * WebUtils.toHttp(request).getMethod().equalsIgnoreCase(POST_METHOD);方法验证，非POST方法将不会进入subject.login
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity<String> login(HttpServletRequest request) {
		// FormAuthenticationFilter会统一将登录（subject.login（））后的异常全部转换后放在这个request
		// Attribute中
		String exception = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if (exception != null) {
			if ("kaptchaValidateFailed".equals(exception)) {
				logger.info(request.getAttribute("username") + ":登录验证码错误");
				return new ResponseEntity<String>("验证码错误", HttpStatus.PRECONDITION_FAILED);
			} else if ("org.apache.shiro.authc.IncorrectCredentialsException".equals(exception)) {
				return new ResponseEntity<String>("用户名或密码错误", HttpStatus.UNAUTHORIZED);
			} else if ("org.apache.shiro.authc.UnknownAccountException".equals(exception)) {
				return new ResponseEntity<String>("用户名或密码错误", HttpStatus.UNAUTHORIZED);
			} else if ("org.apache.shiro.authc.LockedAccountException".equals(exception)) {
				return new ResponseEntity<String>("账号已被锁定", HttpStatus.LOCKED);
			} else if ("org.apache.shiro.authc.ExcessiveAttemptsException".equals(exception)) {
				return new ResponseEntity<String>("登录失败次数过多", HttpStatus.LOOP_DETECTED);
			} else {
				return new ResponseEntity<String>("登录失败", HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<String>("登录成功", HttpStatus.OK);
	}

	@RequestMapping("/loginUser")
	public ResponseEntity<Object> loginUser(String username, String password, String randomcode) {
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(usernamePasswordToken); // 完成登录

		} catch (IncorrectCredentialsException e) {
			return new ResponseEntity<Object>("用户名或密码错误", HttpStatus.UNAUTHORIZED);
		} catch (ExcessiveAttemptsException e) {
			return new ResponseEntity<Object>("登录失败次数过多", HttpStatus.LOOP_DETECTED);
		}
		User user = (User) subject.getPrincipal();
		return new ResponseEntity<Object>(user, HttpStatus.OK);

	}

	@RequestMapping("/logOut")
	public ResponseEntity<Object> logOut(HttpSession session) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		// session.removeAttribute("user");
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<Boolean> registerUser(@RequestBody User user) {
		return new ResponseEntity<Boolean>(userService.save(user), HttpStatus.OK);
	}

}
