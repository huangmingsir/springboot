package com.jx.example.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月5日 下午6:14:08
 *
 */
public class RandomCodeUtil {
	
	/**
	 * 生成随机32位编码
	 * @param object
	 * @param salt
	 * @param hashIterations
	 * @return
	 */
	public String secureRandomNumberGenerator() {
		SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
//		randomNumberGenerator.setSeed("123".getBytes());//生成123的固定32位编码
		return randomNumberGenerator.nextBytes().toHex();
	}
}
