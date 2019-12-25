package cn.allen.medical.data;

import java.util.List;

import cn.allen.medical.entry.MeMenu;
import cn.allen.medical.entry.ToDoContractEntity;
import cn.allen.medical.entry.TodoCount;
import cn.allen.medical.entry.User;
import cn.allen.medical.entry.WaringCount;
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
     * 获取短信验证码
     * @param callBack
     */
    public void smsAuther(String mobile, String verificationCodeType, HttpCallBack callBack){
        Object[] arrays = new Object[] { "mobile",mobile,"verificationCodeType",verificationCodeType };
        request.okhttpget(API.smsAuth,arrays,callBack);
    }

    /**
     * 扫码登录
     * @param url
     * @param callBack
     */
    public void scanLogin(String url, HttpCallBack callBack){
        request.scanPost(url,callBack);
    }

    /**
     * 退出登录
     * @param callBack
     */
    public void exit(HttpCallBack callBack){
        Object[] arrays = new Object[] { };
        request.okhttpget(API.exit,arrays,callBack);
    }

    /**
     * 获取用户权限列表
     * @param callBack
     */
    public void userAuth(HttpCallBack<List<MeMenu>> callBack){
        Object[] arrays = new Object[] { };
        request.okhttpget(API.userAuth,arrays,callBack);
    }

    /**
     * 获取待处理数量
     * @param callBack
     */
    public void todoCount(HttpCallBack<TodoCount> callBack){
        Object[] arrays = new Object[] { };
        request.okhttpget(API.todoCount,arrays,callBack);
    }

    /**
     * 获取预警数量
     * @param callBack
     */
    public void waringCount(HttpCallBack<WaringCount> callBack){
        Object[] arrays = new Object[] { };
        request.okhttpget(API.waringCount,arrays,callBack);
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

    /**
     * 修改密码（原密码）
     * @param oldPsw
     * @param newPsw
     * @param conPsw
     * @param callBack
     */
    public void updatePswByPsw(String oldPsw,String newPsw,String conPsw,HttpCallBack callBack){
        Object[] arrays = new Object[] {"oldPassword",MedicalEncry.encrypt(oldPsw),
                "loginPassword",MedicalEncry.encrypt(newPsw),
                "confirmLoginPassword",MedicalEncry.encrypt(conPsw)};
        request.okhttppost(API.updatePswByPsw,arrays,callBack);
    }
    /**
     * 修改密码（原密码）
     * @param verificationCode
     * @param newPsw
     * @param conPsw
     * @param callBack
     */
    public void updatePswBySms(String verificationCode,String newPsw,String conPsw,HttpCallBack callBack){
        Object[] arrays = new Object[] {"verificationCode",verificationCode,
                "loginPassword",MedicalEncry.encrypt(newPsw),
                "confirmLoginPassword",MedicalEncry.encrypt(conPsw)};
        request.okhttppost(API.updatePswBySms,arrays,callBack);
    }
}
