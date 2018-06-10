package com.util;

import java.io.IOException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DesUtil {

	private final static String DES = "DES/CBC/PKCS5Padding";
	
	private final static String DES_IV = "Raysdata";
	
    public static void main(String[] args) throws Exception {
    	String key = DesUtil.encrypt("D:\\temp\\1528558782882.pdf", "convert");
		System.out.println(key);
		System.out.println(DesUtil.decrypt(key, "455"));
    }
     
    /**
     * Description 根据键值进行加密
     * @param data 
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(), asciiToBcdBytes(key, Math.min(32, key.length()) / 2));
        String strs = Base64.getEncoder().encodeToString(bt);
        return strs;
    }
 
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws IOException,
            Exception {
        if (data == null)
            return null;
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] buf = decoder.decode(data);
        byte[] bt = decrypt(buf,asciiToBcdBytes(key, Math.min(32, key.length()) / 2));
        return new String(bt);
    }
 
    /**
     * Description 根据键值进行加密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
 
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey,new IvParameterSpec(DES_IV.getBytes()));
 
        return cipher.doFinal(data);
    }
     
     
    /**
     * Description 根据键值进行解密
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        
        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
 
        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
 
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey,new IvParameterSpec(DES_IV.getBytes()));
        
        // 用密钥初始化Cipher对象
        return cipher.doFinal(data);
    }
    /**
     * 转换byte数组
     * @param key
     * @param count
     * @return
     */
    private static byte[] asciiToBcdBytes(String key, int count){
    	byte[] hex = new byte[24];
    	byte[] inputBytes = key.getBytes();
    	for (int i = 0, j = 0; j < inputBytes.length && i < count; ++i) {
    		byte v = inputBytes[j];
    		++j;
    		if (v <= 0x39)
    			hex[i] = (byte) (v - 0x30);
    		else
    			hex[i] = (byte) (v - 0x41 + 10);

    		hex[i] <<= 4;

    		if (j >= inputBytes.length)
    			break;

    		v = inputBytes[j];
    		++j;
    		if (v <= 0x39)
    			hex[i] += (byte) (v - 0x30);
    		else
    			hex[i] += (byte) (v - 0x41 + 10);
    	}
    	for (int i = 0; i < 8; ++i)
    		hex[16 + i] = hex[i];
    	return hex;
    }
}
