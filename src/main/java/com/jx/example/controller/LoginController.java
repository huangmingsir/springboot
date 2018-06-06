package com.jx.example.controller;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
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
	public ResponseEntity<String> loginUser(String username, String password, HttpSession session) {
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(usernamePasswordToken); // 完成登录
		} catch (IncorrectCredentialsException e) {
			return new ResponseEntity<String>("用户名或密码错误", HttpStatus.UNAUTHORIZED);
		} catch (ExcessiveAttemptsException e) {
			return new ResponseEntity<String>("登录失败次数过多", HttpStatus.LOOP_DETECTED);
		}
		User user = (User) subject.getPrincipal();
		session.setAttribute("user", user);
		Session session2 = subject.getSession();
//		System.out.println(session2.getId());
		return new ResponseEntity<String>("登录成功", HttpStatus.OK);

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
