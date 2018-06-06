package com.jx.example.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jx.example.entity.User;
import com.jx.example.service.IUserService;

@RestController
public class LoginController {

	@Autowired
	private IUserService userService;

	@RequestMapping("/login")
	public ResponseEntity<User> login() {
		User user = new User();
		user.setNickName("张三丰");
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping("/loginUser")
	public String loginUser(String username, String password, HttpSession session) {
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(usernamePasswordToken); // 完成登录
			User user = (User) subject.getPrincipal();
			session.setAttribute("user", user);
			return "index";
		} catch (Exception e) {
			return "login";// 返回登录页面
		}

	}

	@RequestMapping("/logOut")
	public String logOut(HttpSession session) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		// session.removeAttribute("user");
		return "login";
	}

	@PostMapping("/register")
	public ResponseEntity<Boolean> registerUser(@RequestBody User user) {
		return new ResponseEntity<Boolean>(userService.save(user), HttpStatus.OK);
	}

}
