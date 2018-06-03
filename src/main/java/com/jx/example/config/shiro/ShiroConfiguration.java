package com.jx.example.config.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ShiroConfiguration {

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		
		return shiroFilterFactoryBean;
	}
	
	@Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;

    }

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {

		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		shiroFilterFactoryBean.setSecurityManager(securityManager());

		Map<String, Filter> filters = new LinkedHashMap<String, Filter>();

		LogoutFilter logoutFilter = new LogoutFilter();

		logoutFilter.setRedirectUrl("/login");

		// filters.put("logout",null);

		shiroFilterFactoryBean.setFilters(filters);

		Map<String, String> filterChainDefinitionManager = new LinkedHashMap<String, String>();

		filterChainDefinitionManager.put("/logout", "logout");

		filterChainDefinitionManager.put("/user/**", "authc,roles[ROLE_USER]");

		filterChainDefinitionManager.put("/events/**", "authc,roles[ROLE_ADMIN]");

		// filterChainDefinitionManager.put("/user/edit/**",
		// "authc,perms[user:edit]");// 这里为了测试，固定写死的值，也可以从数据库或其他配置中读取

		filterChainDefinitionManager.put("/**", "anon");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionManager);

		shiroFilterFactoryBean.setSuccessUrl("/");

		shiroFilterFactoryBean.setUnauthorizedUrl("/403");

		return shiroFilterFactoryBean;

	}
}
