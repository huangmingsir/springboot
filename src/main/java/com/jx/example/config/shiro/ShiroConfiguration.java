package com.jx.example.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.jx.example.config.GlobalConfig;
import com.jx.example.filter.CustomFormAuthenticationFilter;

@Configuration
public class ShiroConfiguration {

	/**
	 * 用于定期的验证会话是否已过期，如果过期将停止会话；使用是需要引入shiro-quartz依赖
	 * 
	 * @return
	 */
	@Bean
	public ExecutorServiceSessionValidationScheduler scheduler() {
		ExecutorServiceSessionValidationScheduler scheduler = new ExecutorServiceSessionValidationScheduler();
		scheduler.setInterval(3600000);
		scheduler.setSessionManager(sessionManager());
		return scheduler;
	}

	@Bean
	public SimpleCookie simpleCookie() {
		SimpleCookie simpleCookie = new SimpleCookie();
		// subject.getSession()获取到的将是此名字的SESSION
		simpleCookie.setName("MYSESSIONID");// 不能取名叫sid，否则会报 org.apache.shiro.session.UnknownSessionException: There is
											// no session with id
		simpleCookie.setMaxAge(1800000);// 设置Cookie的过期时间，秒为单位，默认-1表示关闭浏览器时过期Cookie；
		// 如果设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；此特性需要实现了Servlet
		// 2.5 MR6及以上版本的规范的Servlet容器支持；
		simpleCookie.setHttpOnly(true);
		return simpleCookie;
	}

	/**
	 * 在Servlet容器中，默认使用JSESSIONID
	 * Cookie维护会话，且会话默认是跟容器绑定的；在某些情况下可能需要使用自己的会话机制，此时我们可以使用DefaultWebSessionManager来维护会话
	 * 
	 * @return
	 */
	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
		defaultWebSessionManager.setGlobalSessionTimeout(1800000);// 设置session全局过期时间30分钟
		defaultWebSessionManager.setSessionIdCookie(simpleCookie());
		defaultWebSessionManager.setSessionIdCookieEnabled(true);
		defaultWebSessionManager.setDeleteInvalidSessions(true);// 删除过期的session
		defaultWebSessionManager.setSessionDAO(enterpriseCacheSessionDAO());//不设置默认使用AbstractSessionManager,可以自定义sessionDao实现session的增删改查或持久化
		defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
		// defaultWebSessionManager.setSessionValidationScheduler(scheduler());
		return defaultWebSessionManager;
	}

	@Bean
    public EnterpriseCacheSessionDAO enterpriseCacheSessionDAO(){
	    EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
	    enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
	    return enterpriseCacheSessionDAO;
    }

	/**
	 * 用于密码重试和会话缓存
	 * 
	 * @return
	 */
	@Bean
	public EhCacheManager ehCacheManager() {
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
		return cacheManager;
	}

	@Bean(name = "securityManager") // 必须
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(shiroRealm());
		securityManager.setCacheManager(ehCacheManager());
		securityManager.setSessionManager(sessionManager());
		return securityManager;
	}

	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public ShiroRealm shiroRealm() {
		ShiroRealm shiroRealm = new ShiroRealm();
		// 告诉realm,使用credentialsMatcher加密算法类来验证密文
		shiroRealm.setCredentialsMatcher(myCredentialsMatcher());
		shiroRealm.setCachingEnabled(false);
		return shiroRealm;
	}

	@Bean
	public MyCredentialsMatcher myCredentialsMatcher() {
		// MyCredentialsMatcher myCredentialsMatcher = new MyCredentialsMatcher();
		MyCredentialsMatcher myCredentialsMatcher = new MyCredentialsMatcher(ehCacheManager());
		myCredentialsMatcher.setHashAlgorithm(GlobalConfig.HASHALGORITHM);
		myCredentialsMatcher.setHashIterations(GlobalConfig.HASHITERATIONS);
		return myCredentialsMatcher;
	}

	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");// 散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(2);// 散列的次数，比如散列两次，相当于md5(md5(""));
		hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
		return hashedCredentialsMatcher;
	}

	/**
	 * 
	 * LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，
	 * 
	 * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。(必须)
	 * 
	 * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
	 * 
	 */

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 开启shiro aop注解支持,AuthorizationAttributeSourceAdvisor，shiro里实现的Advisor类，
	 * 内部使用AopAllianceAnnotationsAuthorizingMethodInterceptor来拦截用以下注解的方法。
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager());
		return advisor;
	}

	/**
	 * DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。
	 */
	@Bean
	@ConditionalOnMissingBean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
		defaultAAP.setProxyTargetClass(true);
		return defaultAAP;
	}

	/**
	 * ShiroFilterFactoryBean，是个factorybean，为了生成ShiroFilter。
	 * 它主要保持了三项数据，securityManager，filters，filterChainDefinitionManager。
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager());
		Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
		filters.put("authc", new CustomFormAuthenticationFilter());
		// LogoutFilter logoutFilter = new LogoutFilter();
		// logoutFilter.setRedirectUrl("/login");
		// filters.put("logout",null);
		shiroFilterFactoryBean.setFilters(filters);
		Map<String, String> filterChainDefinitionManager = new LinkedHashMap<String, String>();
		filterChainDefinitionManager.put("/loginUser", "anon");
		filterChainDefinitionManager.put("/logout", "logout");
		filterChainDefinitionManager.put("/user/**", "authc,roles[ROLE_USER]");
		filterChainDefinitionManager.put("/events/**", "authc,roles[ROLE_ADMIN]");
		filterChainDefinitionManager.put("/getVerify", "anon");
		filterChainDefinitionManager.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionManager);
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面，如果不存在就会发生第一次登录经过CustomFormAuthenticationFilter后，再次进入并丢失httpServletRequest导致登录失败
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/");
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
		return shiroFilterFactoryBean;
	}

}
