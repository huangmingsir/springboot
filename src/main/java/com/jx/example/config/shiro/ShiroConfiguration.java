package com.jx.example.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ShiroConfiguration {

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager());
		
		return shiroFilterFactoryBean;
	}
	
	@Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;

    }
	
	@Bean
    public ShiroRealm shiroRealm() {
		ShiroRealm shiroRealm = new ShiroRealm();
        //告诉realm,使用credentialsMatcher加密算法类来验证密文
		shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		shiroRealm.setCachingEnabled(false);
        return shiroRealm;
    }
	
	@Bean  
    public HashedCredentialsMatcher hashedCredentialsMatcher() {  
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();  
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;  
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));  
        return hashedCredentialsMatcher;  
    } 

}
