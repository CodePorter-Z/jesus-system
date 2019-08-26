package com.code.proter.common.utils.encrypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class EncryptUtil {

    // 密钥是16位长度的byte[]进行Base64转换后得到的字符串
//    public static String key = "LmMGStGtOpF4xNyvYt54EQ==";
    public static String key = "GgZDeS/dKMJgSjo2oVBERQ==";

    /**
     * <li>
     * 方法名称:encrypt</li> <li>
     * 加密方法
     * @param xmlStr
     *            需要加密的消息字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String xmlStr) {
        byte[] encrypt = null;

        try {
            // 取需要加密内容的utf-8编码。
            encrypt = xmlStr.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 取MD5Hash码，并组合加密数组
        byte[] md5Hash = null;
        try {
            md5Hash = MD5Util.MD5Hash(encrypt, 0, encrypt.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 组合消息体
        byte[] totalByte = MD5Util.addMD5(md5Hash, encrypt);

        byte[] temp = getDecryptOrEncryptByte(totalByte,"encrypt");

        // 使用Base64加密后返回
        return new BASE64Encoder().encode(temp);
    }

    /**
     * <li>
     * 方法名称:encrypt</li> <li>
     * 功能描述:
     *
     * <pre>
     * 解密方法
     * </pre>
     *
     * </li>
     *
     * @param xmlStr
     *            需要解密的消息字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    public static String decrypt(String xmlStr) throws Exception {
        // base64解码
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] encBuf = null;
        try {
            encBuf = decoder.decodeBuffer(xmlStr);
        } catch (IOException e) {
            e.printStackTrace();
        }

       byte[] temp = getDecryptOrEncryptByte(encBuf,"decrypt");

        // 进行解密后的md5Hash校验
        byte[] md5Hash = null;
        try {
            md5Hash = MD5Util.MD5Hash(temp, 16, temp.length - 16);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 进行解密校检
        for (int i = 0; i < md5Hash.length; i++) {
            if (md5Hash[i] != temp[i]) {
                // System.out.println(md5Hash[i] + "MD5校验错误。" + temp[i]);
                throw new Exception("MD5校验错误。");
            }
        }

        // 返回解密后的数组，其中前16位MD5Hash码要除去。
        return new String(temp, 16, temp.length - 16, "utf-8");
    }
    
    private static byte[] getDecryptOrEncryptByte(byte[] encBuf,String type){

        // 取密钥和偏转向量
        byte[] key = new byte[8];
        byte[] iv = new byte[8];
        getKeyIV(EncryptUtil.key, key, iv);

        SecretKeySpec desKey = new SecretKeySpec(key, "DES");
        IvParameterSpec ivParam = new IvParameterSpec(iv);

        // 使用DES算法解密
        byte[] temp = null;
        try {
            if (type.toLowerCase().equals("decrypt")) {

                temp = DESUtil.DES_CBC_Decrypt(encBuf, desKey, ivParam);

            } else if (type.toLowerCase().equals("encrypt")) {
                temp = DESUtil.DES_CBC_Encrypt(encBuf, desKey, ivParam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return temp;
    }

    /**
     * <li> 
     * 方法名称:getKeyIV</li> <li> 
     * 功能描述: 
     *
     * <pre> 
     *
     * </pre> 
     * </li> 
     *
     * @param encryptKey
     * @param key
     * @param iv
     */
    public static void getKeyIV(String encryptKey, byte[] key, byte[] iv) {
        // 密钥Base64解密  
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] buf = null;
        try {
            buf = decoder.decodeBuffer(encryptKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 前8位为key  
        int i;
        for (i = 0; i < key.length; i++) {
            key[i] = buf[i];
        }
        // 后8位为iv向量  
        for (i = 0; i < iv.length; i++) {
            iv[i] = buf[i + 8];
        }
    }

    public static void main(String[] args) {
        System.out.println(encrypt("123456"));
    }
}
