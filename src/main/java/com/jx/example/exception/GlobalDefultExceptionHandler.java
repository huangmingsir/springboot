package com.jx.example.exception;

import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.UnknownSessionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月6日 下午6:33:40
 *
 */
@ControllerAdvice
public class GlobalDefultExceptionHandler {
	
	/*@ExceptionHandler(UnknownAccountException.class)  
    @ResponseBody  
    public ResponseEntity<String> validateFailedExceptionHandler(UnknownAccountException e) {  
		return new ResponseEntity<String>("登录异常："+e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
	
	@ExceptionHandler(IncorrectCredentialsException.class)  
    @ResponseBody  
    public ResponseEntity<String> validateFailedExceptionHandler(IncorrectCredentialsException e) {  
		return new ResponseEntity<String>("登录异常："+e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
	
	@ExceptionHandler(LockedAccountException.class)  
    @ResponseBody  
    public ResponseEntity<String> validateFailedExceptionHandler(LockedAccountException e) {  
		return new ResponseEntity<String>("登录异常："+e.getMessage(), HttpStatus.LOCKED);
    }
	
	@ExceptionHandler(ExcessiveAttemptsException.class)  
    @ResponseBody  
    public ResponseEntity<String> validateFailedExceptionHandler(ExcessiveAttemptsException e) {  
		return new ResponseEntity<String>("登录异常："+e.getMessage(), HttpStatus.LOOP_DETECTED);
    }*/
	
//	@ExceptionHandler(UnknownSessionException.class)  
//    @ResponseBody  
//    public ResponseEntity<String> validateFailedExceptionHandler(UnknownSessionException e) {  
//		return new ResponseEntity<String>("验证码异常："+e.getMessage(), HttpStatus.EXPECTATION_FAILED);
//    }
	
	@ExceptionHandler(Exception.class)  
    @ResponseBody  
    public ResponseEntity<String> defultExcepitonHandler(Exception e) {  
		return new ResponseEntity<String>("服务器异常："+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
