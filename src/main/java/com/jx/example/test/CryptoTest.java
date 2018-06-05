package com.jx.example.test;

import java.security.Key;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.BlowfishCipherService;
import org.apache.shiro.crypto.DefaultBlockCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.Sha384Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.jupiter.api.Test;

import junit.framework.Assert;

/**
 * 
 * @Description: TODO(描述类)
 * @author 黄明彪
 * @date 2018年6月4日 下午2:57:13
 *
 */
public class CryptoTest {

	public static void main(String[] args) {
		DefaultHashService hashService = new DefaultHashService(); // 默认算法SHA-512
		hashService.setHashAlgorithmName("SHA-512");
		hashService.setPrivateSalt(new SimpleByteSource("123")); // 私盐，默认无
		hashService.setGeneratePublicSalt(true);// 是否生成公盐，默认false
		hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());// 用于生成公盐。默认就这个
		hashService.setHashIterations(1); // 生成Hash值的迭代次数

		HashRequest request = new HashRequest.Builder().setAlgorithmName("MD5")
				.setSource(ByteSource.Util.bytes("hello")).setSalt(ByteSource.Util.bytes("123")).setIterations(2)
				.build();
		String hex = hashService.computeHash(request).toHex();
		System.out.println(hex);
		// Base64编码及解码，位数不固定
		String str2 = "hello11111";
		String base64Encoded2 = Base64.encodeToString(str2.getBytes());
		String str22 = Base64.decodeToString(base64Encoded2);
		System.out.println(base64Encoded2 + "----------------" + str22);
		// Hex编码及解码，位数不固定
		String str222 = "hello";
		String base64Encoded22 = Hex.encodeToString(str222.getBytes());
		String str2222 = new String(Hex.decode(base64Encoded22.getBytes()));
		System.out.println(base64Encoded22 + "----------------" + str2222);
		// Md5Hash理论上不可解码，固定32位
		String str = "hello11111";
		String salt = "123";
		int hashIterations = 2;
		String md5 = new Md5Hash(str, salt, hashIterations).toString();
		System.out.println(md5);
		// Md5Hash不可解码，固定64位
		String sha1 = new Sha256Hash(str, salt, hashIterations).toString();
		System.out.println(sha1);

		SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
//		randomNumberGenerator.setSeed("123".getBytes());//生成123的固定32位编码
		String hex2 = randomNumberGenerator.nextBytes().toHex();
		System.out.println(hex2);
	}
	
	 @Test
	    public void testBase64() {
	        String str = "hello";
	        String base64Encoded = Base64.encodeToString(str.getBytes());
	        String str2 = Base64.decodeToString(base64Encoded);
	        Assert.assertEquals(str, str2);

	    }

	    @Test
	    public void testHex() {
	        String str = "hello";
	        String base64Encoded = Hex.encodeToString(str.getBytes());
	        String str2 = new String(Hex.decode(base64Encoded.getBytes()));
	        Assert.assertEquals(str, str2);

	    }

	    @Test
	    public void testCodecSupport() {
	        String str = "hello";
	        byte[] bytes = CodecSupport.toBytes(str, "utf-8");
	        String str2 = CodecSupport.toString(bytes, "utf-8");
	        Assert.assertEquals(str, str2);
	    }


	    @Test
	    public void testRandom() {
	        //生成随机数
	        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	        randomNumberGenerator.setSeed("123".getBytes());
	        System.out.println(randomNumberGenerator.nextBytes().toHex());
	    }



	    @Test
	    public void testMd5() {
	        String str = "hello";
	        String salt = "123";
	        String md5 = new Md5Hash(str, salt).toString();//还可以转换为 toBase64()/toHex()
	        System.out.println(md5);


	    }

	    @Test
	    public void testSha1() {
	        String str = "hello";
	        String salt = "123";
	        String sha1 = new Sha1Hash(str, salt).toString();
	        System.out.println(sha1);

	    }

	    @Test
	       public void testSha256() {
	        String str = "hello";
	        String salt = "123";
	        String sha1 = new Sha256Hash(str, salt).toString();
	        System.out.println(sha1);

	    }

	    @Test
	    public void testSha384() {
	        String str = "hello";
	        String salt = "123";
	        String sha1 = new Sha384Hash(str, salt).toString();
	        System.out.println(sha1);

	    }

	    @Test
	    public void testSha512() {
	        String str = "hello";
	        String salt = "123";
	        String sha1 = new Sha512Hash(str, salt).toString();
	        System.out.println(sha1);

	    }

	    @Test
	    public void testSimpleHash() {
	        String str = "hello";
	        String salt = "123";
	        //MessageDigest
	        String simpleHash = new SimpleHash("SHA-1", str, salt).toString();
	        System.out.println(simpleHash);

	    }



	    @Test
	    public void testHashService() {
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


	    @Test
	    public void testAesCipherService() {
	        AesCipherService aesCipherService = new AesCipherService();
	        aesCipherService.setKeySize(128);//设置key长度

	        //生成key
	        Key key = aesCipherService.generateNewKey();

	        String text = "hello";

	        //加密
	        String encrptText = aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
	        System.out.println(encrptText);
	        //解密
	        String text2 = new String(aesCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());
	        System.out.println(text2);
	        Assert.assertEquals(text, text2);
	    }

	    @Test
	    public void testBlowfishCipherService() {
	        BlowfishCipherService blowfishCipherService = new BlowfishCipherService();
	        blowfishCipherService.setKeySize(128);

	        //生成key
	        Key key = blowfishCipherService.generateNewKey();

	        String text = "hello";

	        //加密
	        String encrptText = blowfishCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
	        //解密
	        String text2 = new String(blowfishCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());

	        Assert.assertEquals(text, text2);
	    }

	    @Test
	    public void testDefaultBlockCipherService() throws Exception {


	        //对称加密，使用Java的JCA（javax.crypto.Cipher）加密API，常见的如 'AES', 'Blowfish'
	        DefaultBlockCipherService cipherService = new DefaultBlockCipherService("AES");
	        cipherService.setKeySize(128);

	        //生成key
	        Key key = cipherService.generateNewKey();

	        String text = "hello";

	        //加密
	        String encrptText = cipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
	        //解密
	        String text2 = new String(cipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());

	        Assert.assertEquals(text, text2);
	    }


}
