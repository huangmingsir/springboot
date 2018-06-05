package com.jx.example.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月5日 上午10:26:05
 *
 */
public class EncryptUtil {
	/**
	 * HashService加密
	 * @param algorithmName
	 * @param salt
	 * @param iterations
	 * @param encryptStr
	 * @return
	 */
	public static String hashService(String algorithmName,String salt,int hashIterations,String encryptStr) {
		DefaultHashService hashService = new DefaultHashService(); //默认算法SHA-512  
		hashService.setHashAlgorithmName("SHA-512");
		hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐，默认无  
		hashService.setGeneratePublicSalt(true);//是否生成公盐，默认false  
		hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个  
		hashService.setHashIterations(1); //生成Hash值的迭代次数  
		  
		HashRequest request = new HashRequest.Builder()  
		            .setAlgorithmName(algorithmName)
		            .setSource(ByteSource.Util.bytes(encryptStr))  
		            .setSalt(ByteSource.Util.bytes(salt))
		            .setIterations(hashIterations)
		            .build();  
		String hex = hashService.computeHash(request).toHex();
		return hex;
	}
	
	/**
	 * SimpleHash加密
	 * @param algorithmName
	 * @param salt
	 * @param hashIterations
	 * @param encryptStr
	 * @return
	 */
	public String simpleHash(String algorithmName,String salt,int hashIterations,String encryptStr) {
		 return new SimpleHash(algorithmName, encryptStr, salt, hashIterations).toHex();  
	}
	
	/**
	 * 散列算法:Md5Hash理论上不可解码，32位
	 * @param object
	 * @param salt
	 * @param hashIterations
	 * @return
	 */
	public String md5Hash(Object object, Object salt, int hashIterations) {
		return new Md5Hash(object, salt, hashIterations).toString();
	}
	
	/**
	 * 散列算法:Sha256Hash不可解码，64位
	 * @param object
	 * @param salt
	 * @param hashIterations
	 * @return
	 */
	public String sha256Hash(Object object, Object salt, int hashIterations) {
		return new Sha256Hash(object, salt, hashIterations).toString();
	}
	
	
}
