package com.jesus.common.utils.encrypt.aes;

public class AESUtil {

    //加密私钥
    private static final String KEY = "zhoubing19960101";

    /**
     * 加密
     * @param pass 明文
     * @return
     */
    public static String Encrypt(String pass) {
        try {
            return AES.Encrypt(pass, KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "empty";
    }

    /**
     * 解密
     * @param pass 密文
     * @return
     */
    public static String Decrypt(String pass) {
        try {
            return AES.Decrypt(pass, KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "empty";
    }

    public static void main(String[] args) {
        String password = Encrypt("123456");

        System.out.println("加密：" + password);
        String password2 = Decrypt(password);
        System.out.println("解密：" + password2);


    }
}
