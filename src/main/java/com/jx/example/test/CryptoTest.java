package com.jx.example.test;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月4日 下午2:57:13
 *
 */
public class CryptoTest {

	public static void main(String[] args) {
		DefaultHashService hashService = new DefaultHashService(); //默认算法SHA-512  
		hashService.setHashAlgorithmName("SHA-512");  
		hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐，默认无  
		hashService.setGeneratePublicSalt(true);//是否生成公盐，默认false  
		hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个  
		hashService.setHashIterations(1); //生成Hash值的迭代次数  
		  
		HashRequest request = new HashRequest.Builder()  
		            .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes("hello"))  
		            .setSalt(ByteSource.Util.bytes("123")).setIterations(2).build();  
		String hex = hashService.computeHash(request).toHex();
		System.out.println(hex);
	}

}
