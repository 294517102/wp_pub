/**
 * 
 */
package com.tjsj.wp.tools;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
/**
 * @author andrew-silence
 *
 */
public class RSAUtils {
	 /**
     * 算法名称
     */
    private static final String ALGORITHOM = "RSA";
    /**
     * 密钥大小
     */
    private static final int KEY_SIZE = 1024;
    /**
     * 默认的安全服务提供者
     */
    private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();
    private static KeyPairGenerator keyPairGen = null;
    private static KeyFactory keyFactory = null;
    /**
     * 缓存的密钥对。
     */
    private static KeyPair oneKeyPair = null;

    //密文种子, 当想更换RSA钥匙的时候,只需要修改密文种子,即可更换
    private static final String radamKey = "nari";//你自己随便写上数字或者英文即可

    //类加载后进行初始化数据
    static {
        try {
            keyPairGen = KeyPairGenerator.getInstance(ALGORITHOM,
                    DEFAULT_PROVIDER);
            keyFactory = KeyFactory.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 根据指定的密文种子,生成并返回RSA密钥对。
     */
    private static synchronized KeyPair generateKeyPair() {
        try {
            keyPairGen.initialize(KEY_SIZE,
                    new SecureRandom(radamKey.getBytes()));
            oneKeyPair = keyPairGen.generateKeyPair();
            return oneKeyPair;
        } catch (InvalidParameterException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 返回初始化时默认的公钥。
     */
    public static RSAPublicKey getDefaultPublicKey() {
        KeyPair keyPair = generateKeyPair();
        if (keyPair != null) {
            return (RSAPublicKey) keyPair.getPublic();
        }
        return null;
    }

    /**
     * 使用指定的私钥解密数据。
     *
     * @param privateKey 给定的私钥。
     * @param data       要解密的数据。
     * @return 原数据。
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] data) throws Exception {
        Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        ci.init(Cipher.DECRYPT_MODE, privateKey);
        return ci.doFinal(data);
    }

    /**
     * 使用默认的私钥解密给定的字符串。
     *
     * @param encryptText 密文。
     * @return 原文字符串。
     */
    public static String decryptString(String encryptText) {
        if (StringUtils.isBlank(encryptText)) {
            return null;
        }
        KeyPair keyPair = generateKeyPair();
        try {
            byte[] en_data = Hex.decode(encryptText);
            byte[] data = decrypt((RSAPrivateKey) keyPair.getPrivate(), en_data);
            return new String(data);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 使用秘钥 - 对js端传递过来密文进行解密
     *
     * @param encryptText 密文。
     * @return {@code encryptText} 的原文字符串。
     */
    public static String decryptStringByJs(String encryptText) {
        String text = decryptString(encryptText);
        if (text == null) {
            return null;
        }
        String reverse = StringUtils.reverse(text);
        String decode = null;
        try {
            //这里需要进行编码转换.注:在前端js对明文加密前需要先进行转码-可自行百度"编码转换"
            decode = URLDecoder.decode(reverse, "UTF-8");
            System.out.println("解密后文字：" + decode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decode;
    }

    //java端 - 使用公钥进行加密
    public static byte[] encrypt(String plaintext) throws Exception {
        // 获取公钥及参数e,n
        RSAPublicKey publicKey = RSAUtils.getDefaultPublicKey();
        //获取公钥指数 e
        BigInteger e = publicKey.getPublicExponent();
        //获取公钥系数 n
        BigInteger n = publicKey.getModulus();
        //先将明文进行编码
        String encode = URLEncoder.encode(plaintext);
        // 获取明文字节数组 m
        BigInteger m = new BigInteger(encode.getBytes());
        // 进行明文加密 c
        BigInteger c = m.modPow(e, n);
        //返回密文字节数组
        return c.toByteArray();
    }

    //java端 - 使用私钥进行解密
    public static String decrypt(byte[] cipherText) throws Exception {
        // 读取私钥
        KeyPair keyPair = generateKeyPair();
        RSAPrivateKey prk = (RSAPrivateKey) keyPair.getPrivate();
        // 获取私钥参数-指数/系数
        BigInteger d = prk.getPrivateExponent();
        BigInteger n = prk.getModulus();
        // 读取密文
        BigInteger c = new BigInteger(cipherText);
        // 进行解密
        BigInteger m = c.modPow(d, n);
        // 解密结果-字节数组
        byte[] mt = m.toByteArray();
        //转成String,此时是乱码
        String en = new String(mt);
        //再进行编码
        String result = URLDecoder.decode(en, "UTF-8");
        //最后返回解密后得到的明文
        return result;
    }


    public static void main(String[] args) {
        /*解密js端传递过来的密文*/
        //获取公钥对象--注意:前端那边需要用到公钥系数和指数
        RSAPublicKey publicKey = RSAUtils.getDefaultPublicKey();
        
        //公钥-系数(n)
        System.out.println("public key modulus:" + new String(Hex.encode(publicKey.getModulus().toByteArray())));
        //公钥-指数(e1)
        System.out.println("public key exponent:" + new String(Hex.encode(publicKey.getPublicExponent().toByteArray())));
        //JS加密后的字符串
        String param = "00e95d69beed602f41a0ccc7 00b8f7a3a9e3bbd8292cc411";
        //解密后的字符串
        String param1 = RSAUtils.decryptStringByJs(param);

        System.out.println(param1);

    }

}
