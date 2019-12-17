package cn.allen.medical.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import allen.frame.tools.EncryptUtils;

public class MedicalEncry {

    public static String encrypt(String msg){
        String first = DESUtil.encode(Constants.KEY_STORY,msg);
//        String first = EncryptUtils.encryptString(msg,Constants.KEY_STORY);
        try {
            String strstring = new String(android.util.Base64.encode(first.getBytes("UTF-8"), android.util.Base64.DEFAULT),
                    "UTF-8");
            return strstring;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

}
