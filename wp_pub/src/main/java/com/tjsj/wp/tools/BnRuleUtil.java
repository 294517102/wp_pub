/**
 * 
 */
package com.tjsj.wp.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.CollectionUtils;

import com.tjsj.base.constant.Const;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @Desc
 * @author gongdzh
 *
 * @Date 2019年6月6日 下午7:23:32
 */
public class BnRuleUtil {

	private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";


	// 采用时间+随时数的方式
	public static String orderNo() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateStr = sdf.format(new Date());
		Random random = new Random();
		// 三位随机数
		for (int i = 0; i < 3; i++) {
			dateStr += random.nextInt(10);
		}
		return dateStr;
	}



	/*
	 * 偏移秒数 Parameters:date 日期offset 偏移秒数，正数向未来偏移，负数向历史偏移Returns:偏移后的日期
	 */
	public static Date offsetSecond(int second) {
		return new Date(DateUtil.offsetSecond(new Date(), second).getTime());
	}

	/**
	 * @Desc 判断是否是强密码（必填字母数字及特殊字符，且以字母开头，8位以上）
	 * @param password
	 * @return
	 */
	public static boolean isStrongPassword(String password) {
		if (StrUtil.isBlank(password)) {
			return false;
		}
		String REGEX_PASSWORD_STRONG = "^(?![0-9]+$)(?![^0-9]+$)(?![a-zA-Z]+$)(?![^a-zA-Z]+$)(?![a-zA-Z0-9]+$)[a-zA-Z0-9\\S]{8,16}$";
		if (password.matches(REGEX_PASSWORD_STRONG)) {
			return true;
		}
		return false;
	}

	public static String encrypt(String content, String key) {
		try {
			byte[] raw = key.getBytes(); // 获得密码的字节数组
			SecretKeySpec skey = new SecretKeySpec(raw, "AES"); // 根据密码生成AES密钥
			Cipher cipher = Cipher.getInstance(ALGORITHMSTR); // 根据指定算法ALGORITHM自成密码器
			cipher.init(Cipher.ENCRYPT_MODE, skey); // 初始化密码器，第一个参数为加密(ENCRYPT_MODE)或者解密(DECRYPT_MODE)操作，第二个参数为生成的AES密钥
			byte[] byte_content = content.getBytes("utf-8"); // 获取加密内容的字节数组(设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
			byte[] encode_content = cipher.doFinal(byte_content); // 密码器加密数据
			return Base64.encodeBase64String(encode_content); // 将加密后的数据转换为字符串返回
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decrypt(String encryptStr, String decryptKey) {
		try {
			byte[] raw = decryptKey.getBytes(); // 获得密码的字节数组
			SecretKeySpec skey = new SecretKeySpec(raw, "AES"); // 根据密码生成AES密钥
			Cipher cipher = Cipher.getInstance(ALGORITHMSTR); // 根据指定算法ALGORITHM自成密码器
			cipher.init(Cipher.DECRYPT_MODE, skey); // 初始化密码器，第一个参数为加密(ENCRYPT_MODE)或者解密(DECRYPT_MODE)操作，第二个参数为生成的AES密钥
			byte[] encode_content = Base64.decodeBase64(encryptStr); // 把密文字符串转回密文字节数组
			byte[] byte_content = cipher.doFinal(encode_content); // 密码器解密数据
			return new String(byte_content, "utf-8"); // 将解密后的数据转换为字符串返回
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}



}
