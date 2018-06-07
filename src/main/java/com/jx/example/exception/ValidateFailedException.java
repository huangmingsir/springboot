package com.jx.example.exception;
/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月7日 下午1:49:38
 *
 */
public class ValidateFailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ValidateFailedException(String message) {
		super(message);
	}
	
}
