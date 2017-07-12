package com.hellopass;


import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public class AESUtil {
	private static final int AESBLOCKSIZE = 16;
	
	private String salt = "";
	
	public void setSalt(String salt)
	{
		this.salt = salt;
	}
	/**
	 * 加密函数
	 * @param src
	 * @return
	 */
	public String encrypt(String src) {
		if(src == null)
		{
			return "";
		}
		
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, makeKey());
			
			//IV
			AlgorithmParameters params = cipher.getParameters();
			byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
			
			//密文
			byte[] cipherbyte = cipher.doFinal(src.getBytes());
			
			//IV+密文
			byte[] ciphertext = new byte[AESBLOCKSIZE + cipherbyte.length];
			System.arraycopy(iv, 0, ciphertext, 0, AESBLOCKSIZE);
			System.arraycopy(cipherbyte, 0, ciphertext, AESBLOCKSIZE, cipherbyte.length);
			
			return new Base64().encodeAsString(ciphertext);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 解密函数
	 * @param src
	 * @return
	 */
	public String decrypt(String src) {
		if(StringUtils.isEmpty(src))
		{
			return "";
		}
		
		String decrypted = "";
		try {
			//密文
			byte[] ciphertext= Base64.decodeBase64(src);
			byte[] cipherbyte = new byte[ciphertext.length - AESBLOCKSIZE];
			System.arraycopy(ciphertext, AESBLOCKSIZE, cipherbyte, 0, ciphertext.length - AESBLOCKSIZE);
			
			//解密
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, makeKey(), makeIv(ciphertext));
			decrypted = new String(cipher.doFinal(cipherbyte));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return decrypted;
	}
	
	static AlgorithmParameterSpec makeIv(byte[] ciphertext) {
		byte[] iv = new byte[AESBLOCKSIZE];
		System.arraycopy(ciphertext, 0, iv, 0, AESBLOCKSIZE);
		return new IvParameterSpec(iv);
	}
	
	private Key makeKey() {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] key = md.digest(salt.getBytes("UTF-8"));
			return new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
//	public static void main(String[] args)
//	{
//		AESUtil aesUtil = new AESUtil();
//		aesUtil.setSalt("abcde");
//		
//		String cipher = aesUtil.encrypt("hello123");
//		System.out.println(cipher);
//		
//		String plain = aesUtil.decrypt(cipher);
//		System.out.println(plain);
//	}

}
