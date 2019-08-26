package com.jesus.common.utils.encrypt.aes;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


@Slf4j
public class AES {

    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        Cipher cipher = getInstance(sKey,"encrypt");
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {

            Cipher cipher = getInstance(sKey,"decrypt");
            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    private static Cipher getInstance(String sKey,String type)  throws Exception {
        // 判断Key是否正确
        if (StringUtils.isEmpty(sKey)) {
            log.error("ket is not null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            log.error("key Length should not be less than 16");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        if("encrypt".equals(type)){
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        }else if("decrypt".equals(type)){
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        }
        return cipher;
    }


    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String cKey = "1234567890123456";
        // 需要加密的字串
        String cSrc = "3";
        System.out.println(cSrc);
        // 加密
        String enString = AES.Encrypt(cSrc, cKey);
        System.out.println("加密后的字串是：" + enString);

        // 解密
        String DeString = AES.Decrypt(enString, cKey);
        System.out.println("解密后的字串是：" + DeString);

    }
}
