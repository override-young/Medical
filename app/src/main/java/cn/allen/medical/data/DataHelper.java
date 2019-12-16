package cn.allen.medical.data;

import cn.allen.medical.entry.User;

public class DataHelper {
    private static DataHelper helper;
    HttpRequest request;
    HttpBody body;
    private DataHelper() {
        request = new HttpRequest();
        body = new HttpBody();
    }

    public static DataHelper init(){
        if(helper==null){
            helper = new DataHelper();
        }
        return helper;
    }

    public void login(int type, String user, String psw, String smsCode, HttpCallBack<User> callBack){
        Object[] arrays = new Object[] { "loginType",type,"user",user,"password",psw,"smsVerificationCode",smsCode};
        request.okhttppost(API.login,body.okHttpPost(arrays),callBack);
    }
}
