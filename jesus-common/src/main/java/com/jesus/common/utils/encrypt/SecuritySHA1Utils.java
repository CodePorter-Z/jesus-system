package com.jesus.common.utils.encrypt;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class SecuritySHA1Utils {

    /**
     * SHA 加密
     * @param inStr
     * @return
     * @throws Exception
     */
    public static String shaEncode(String inStr) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
 
        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * SHA-1 加密
     * @param credentials 需要加密的字符串
     * @param salt  盐值
     * @return 加密后的字符串
     */
    public static String SHA1(String credentials,String salt){
        return encryptType("SHA-1",credentials,salt,1024);
    }

    /**
     * 用于生成随机盐值
     * @return 20位 随机字符串
     */
    public static String getRandomSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[15];
        random.nextBytes(bytes);
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 借助 shiro SimpleHash类 实现加密
     * @param algorithmName  加密的方式
     * @param credentials    要加密的字符串
     * @param salt           加密盐值
     * @param hashIterations 加密次数
     * @return
     */
    private static String encryptType(String algorithmName,Object credentials,Object salt,int hashIterations){
        return new SimpleHash(algorithmName, credentials,
                salt, hashIterations).toString();
    }

    public static void main(String args[]) throws Exception {
//        String str = "123456";
//        System.out.println(getRandomSalt());
//        System.out.println("原始：" + str);
//        System.out.println("SHA后：" + shaEncode(str));
//        System.out.println("SHA后：" + SHA1(str));
    }
}
 