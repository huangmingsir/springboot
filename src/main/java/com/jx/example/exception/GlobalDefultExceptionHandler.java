package com.jx.example.exception;

import javax.servlet.http.HttpServletRequest;

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
	
	@ExceptionHandler(Exception.class)  
    @ResponseBody  
    public ResponseEntity<String> defultExcepitonHandler(HttpServletRequest request,Exception e) {  
		return new ResponseEntity<String>("服务器异常："+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
