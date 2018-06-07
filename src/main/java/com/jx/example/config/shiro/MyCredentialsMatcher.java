package com.jx.example.config.shiro;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import com.jx.example.util.EncryptUtil;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月5日 上午9:21:37
 *
 */
public class MyCredentialsMatcher extends SimpleCredentialsMatcher {

	private String hashAlgorithm;
	private int hashIterations;
	private boolean hashSalted;
	private Cache<String, AtomicInteger> passwordRetryCache;

	public MyCredentialsMatcher() {
	}

	public MyCredentialsMatcher(CacheManager cacheManager) {
		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		String username = (String) token.getPrincipal();
		// 限制密码重试次数；在ehcache中通过timeToIdleSeconds设置超次数后的等待时间。retry count + 1
		AtomicInteger retryCount = passwordRetryCache.get(username);
		if (retryCount == null) {
			retryCount = new AtomicInteger(0);
			passwordRetryCache.put(username, retryCount);
		}
		if (retryCount.incrementAndGet() > 5) {
			// if retry count > 5 throw
			throw new ExcessiveAttemptsException("账户登录错误过多，请稍后重试");
		}
		// token里有用户输入的用户名和密码；info里有realm里SimpleAuthenticationInfo传的用户和密码
		UsernamePasswordToken autoken = (UsernamePasswordToken) token;
		SimpleAuthenticationInfo sinfo = (SimpleAuthenticationInfo) info;
		String credentialsSalt = new String(sinfo.getCredentialsSalt().getBytes());
		String inputCredential = EncryptUtil.hashService(hashAlgorithm, credentialsSalt, hashIterations,
				String.valueOf(autoken.getPassword()));
		String accountCredentials = String.valueOf(getCredentials(info));// 数据库保存的密码
		boolean match = equals(inputCredential, accountCredentials);
		if (match) {
			passwordRetryCache.remove(username);
		}
		return match;
	}

	public String getHashAlgorithm() {
		return hashAlgorithm;
	}

	public void setHashAlgorithm(String hashAlgorithm) {
		this.hashAlgorithm = hashAlgorithm;
	}

	public int getHashIterations() {
		return hashIterations;
	}

	public void setHashIterations(int hashIterations) {
		this.hashIterations = hashIterations;
	}

	public boolean isHashSalted() {
		return hashSalted;
	}

	public void setHashSalted(boolean hashSalted) {
		this.hashSalted = hashSalted;
	}

}
