package cn.allen.medical.data;

import cn.allen.medical.entry.ToDoContractEntity;
import allen.frame.tools.EncryptUtils;
import cn.allen.medical.entry.User;
import cn.allen.medical.utils.Constants;
import cn.allen.medical.utils.MedicalEncry;

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

    public void refush(){
        request = new HttpRequest();
    }

    /**
     * 登录
     * @param type
     * @param user
     * @param psw
     * @param smsCode
     * @param callBack
     */
    public void login(int type, String user, String psw, String smsCode, HttpCallBack<User> callBack){
        Object[] arrays = new Object[] { "loginType",type,"user",user,"password",MedicalEncry.encrypt(psw),"smsVerificationCode",smsCode};
        request.okhttppost(API.login,arrays,callBack);
    }

    /**
     * 获取待处理合同列表
     * @param page
     * @param callBack
     */
    public void getTodoContract(int page, HttpCallBack<ToDoContractEntity> callBack){
        Object[] arrays = new Object[] {"PageIndex",page};
        request.okhttpget(API.todoContract,arrays,callBack);
    }

    /**
     * 获取合同详细
     * @param id
     * @param callBack
     */
    public void getContractDetails(String id, HttpCallBack<ToDoContractEntity> callBack){
        Object[] arrays = new Object[] {"id",id};
        request.okhttppost(API.contractDetails,arrays,callBack);
    }

    /**
     * 获取用户信息
     * @param callBack
     */
    public void uerInfo(HttpCallBack<User> callBack){
        Object[] arrays = new Object[] {};
        request.okhttpget(API.userInfo,arrays,callBack);
    }
}
