package com.iksun.finance.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {
    public static String encrypt(String password) {
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(password.getBytes());
            byte byteData[] = sh.digest();

            StringBuffer sb = new StringBuffer();
            for(int i = 0 ; i < byteData.length ; i++){
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }
}
