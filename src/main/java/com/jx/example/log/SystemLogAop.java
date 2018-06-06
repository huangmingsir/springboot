package com.jx.example.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年5月31日 上午9:39:05
 *
 */
@Component
@Aspect
public class SystemLogAop {

	private static final Logger logger = LoggerFactory.getLogger(SystemLogAop.class);

//	@Autowired
//	private IOperationLogService operationLogService;

	@Around(("execution(* com.fisco.evidence.controller..*.*(..)) && @annotation(systemLogAnnotation)"))
	public Object doAfterAdvice(ProceedingJoinPoint joinPoint, SystemLogAnnotation systemLogAnnotation) {
		logger.info("==============================用户操作日志-环绕通知开始执行......==============================");
		long beginControllerTime = System.currentTimeMillis();
		String module = systemLogAnnotation.module();
		String remark = systemLogAnnotation.remark();
		Object obj = new Object();
		try {
			obj = joinPoint.proceed();
			long endControllerTime = System.currentTimeMillis();
			long responseTime = endControllerTime - beginControllerTime;
			addSystemLog(module, remark, responseTime);

		} catch (Throwable e) {
			logger.error("用户操作日志-环绕通知获取请求结果失败：" + e.getMessage());
		}

		logger.info("==============================用户操作日志-环绕通知结束执行......==============================");
		return obj;
	}

	@Async("taskExecutor")
	public void addSystemLog(String module, String remark, long responseTime) {
		/*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String url = request.getRequestURL().toString();
		User user = ApplicationUtil.getCurrentUser();
		OperationLog operationLog = new OperationLog();
		operationLog.setLoginIp(request.getRemoteHost());
		operationLog.setModule(module);
		operationLog.setOperationUrl(url);
		operationLog.setRemark(remark);
		operationLog.setResponseTime(responseTime);
		operationLog.setCreateTime(new Date());
		if (user != null) {
			operationLog.setUserId(user.getUserId());
		}
		operationLogService.asyncsave(operationLog);*/
	}

}