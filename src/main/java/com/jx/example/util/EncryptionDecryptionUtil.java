package com.jx.example.util;

import java.security.Key;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月5日 下午6:32:31
 *
 */
public class EncryptionDecryptionUtil {

	/**
	 * Base64编码，位数根据传入code变化
	 * 
	 * @param code
	 * @return
	 */
	public String base64Encode(String code) {
		return Base64.encodeToString(code.getBytes());
	}

	/**
	 * Base64解码
	 * 
	 * @param code
	 * @return
	 */
	public String base64Decode(String decode) {
		return Base64.decodeToString(decode.getBytes());
	}

	/**
	 * Hex 16进制编码，位数根据传入code变化
	 * 
	 * @param code
	 * @return
	 */
	public String hexEncode(String code) {
		return Hex.encodeToString(code.getBytes());
	}

	/**
	 * Hex 16进制解码
	 * 
	 * @param code
	 * @return
	 */
	public String hexDecode(String decode) {
		return new String(Hex.decode(decode.getBytes()));
	}

	/**
	 * AES对称式算法编码，32位数
	 * 
	 * @param code
	 * @return
	 */
	public String aesEncode(String code) {
		AesCipherService aesCipherService = new AesCipherService();
		aesCipherService.setKeySize(128); // 设置key长度
		Key key = aesCipherService.generateNewKey();
		return aesCipherService.encrypt(code.getBytes(), key.getEncoded()).toHex();
	}

	/**
	 * AES对称式算法解码
	 * 
	 * @param code
	 * @return
	 */
	public String aesDecode(String decode) {
		AesCipherService aesCipherService = new AesCipherService();
		aesCipherService.setKeySize(128); // 设置key长度
		Key key = aesCipherService.generateNewKey();
		return new String(aesCipherService.decrypt(Hex.decode(decode), key.getEncoded()).getBytes());
	}
	
	

}
