package com.jx.example.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.jx.example.exception.ValidateFailedException;
import com.jx.example.util.RandomValidateCodeUtil;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月7日 上午9:11:39
 *
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// 在这里进行验证码的校验
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpSession session = httpServletRequest.getSession();
		// 取出验证码
		String validateCode = (String) session.getAttribute(RandomValidateCodeUtil.RANDOMCODEKEY);
		// 取出页面的验证码
		// 输入的验证和session中的验证进行对比
		String randomcode = httpServletRequest.getParameter("randomcode");
		if (randomcode == null || validateCode == null || !randomcode.equals(validateCode)) {
			// 如果校验失败，将验证码错误失败信息，通过DEFAULT_ERROR_KEY_ATTRIBUTE_NAME=shiroLoginFailure设置到request中
			// FormAuthenticationFilter会统一将登录（subject.login（））后的异常全部转换放在这个request中，具体方法如下：etFailureAttribute(ServletRequest
			// request, AuthenticationException ae)
			httpServletRequest.setAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME,
					"kaptchaValidateFailed");// 自定义登录异常
			//throw new ValidateFailedException("验证码校验失败");
			// 拒绝访问，不再校验账号和密码
			return true;
		}
		return super.onAccessDenied(request, response);
	}

}
