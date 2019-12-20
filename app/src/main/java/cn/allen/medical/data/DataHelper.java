package cn.allen.medical.data;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import cn.allen.medical.entry.BillDetailsEntity;
import cn.allen.medical.entry.BillDifferentEntity;
import cn.allen.medical.entry.CompanyWarningEntity;
import cn.allen.medical.entry.ConsumableQualityEntity;
import cn.allen.medical.entry.ConsumableStoreEntity;
import cn.allen.medical.entry.ContractDetailsEntity;
import cn.allen.medical.entry.ContractWarnintEntity;
import cn.allen.medical.entry.DoDifferenceEntity;
import cn.allen.medical.entry.PriceDetailsEntity;
import cn.allen.medical.entry.SelectSumChartEntity;
import cn.allen.medical.entry.ToDoBillEntity;
import cn.allen.medical.entry.MeMenu;
import cn.allen.medical.entry.ToDoContractEntity;
import allen.frame.tools.EncryptUtils;
import cn.allen.medical.entry.ToDoPriceEntity;
import cn.allen.medical.entry.User;
import cn.allen.medical.utils.MedicalEncry;

public class DataHelper {
    private static DataHelper helper;
    HttpRequest request;
    HttpBody body;

    private DataHelper() {
        request = new HttpRequest();
        body = new HttpBody();
    }

    public static DataHelper init() {
        if (helper == null) {
            helper = new DataHelper();
        }
        return helper;
    }

    public void refush() {
        request = new HttpRequest();
    }

    /**
     * 登录
     *
     * @param type
     * @param user
     * @param psw
     * @param smsCode
     * @param callBack
     */
    public void login(int type, String user, String psw, String smsCode, HttpCallBack<User>
            callBack) {
        Object[] arrays = new Object[]{"loginType", type, "user", user, "password", MedicalEncry
                .encrypt(psw), "smsVerificationCode", smsCode};
        request.okhttppost(API.login, arrays, callBack);
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
     * 获取待处理合同列表
     *
     * @param page
     * @param callBack
     */
    public void getTodoContract(int page, HttpCallBack<ToDoContractEntity> callBack) {
        Object[] arrays = new Object[]{"PageIndex", page};
        request.okhttpget(API.todoContract, arrays, callBack);
    }

    /**
     * 获取合同详细
     *
     * @param id
     * @param callBack
     */
    public void getContractDetails(String id, HttpCallBack<ContractDetailsEntity> callBack) {
        Object[] arrays = new Object[]{"id", id};
        request.okhttpget(API.contractDetails, arrays, callBack);
    }

    /**
     * 待确认账单（科室）
     * @param page
     * @param callBack
     */
    public void getTodoBillByDep(int page, HttpCallBack<ToDoBillEntity> callBack) {
        Object[] arrays = new Object[]{"PageIndex", page};
        request.okhttpget(API.todoBillByDep, arrays, callBack);
    }

    /**
     * 待确认账单（设备科）
     * @param page
     * @param callBack
     */
    public void getTodoBillByDevice(int page, HttpCallBack<ToDoBillEntity> callBack) {
        Object[] arrays = new Object[]{"PageIndex", page};
        request.okhttpget(API.todoBillByDevice, arrays, callBack);
    }

    /**
     * 待确认价格
     * @param page
     * @param callBack
     */
    public void getTodoPrice(int page, HttpCallBack<ToDoPriceEntity> callBack) {
        Object[] arrays = new Object[]{"PageIndex", page};
        request.okhttpget(API.todoPrice, arrays, callBack);
    }

    /**
     *价格详情
     * @param id
     * @param callBack
     */
    public void getPriceDetails(String id, HttpCallBack<PriceDetailsEntity> callBack) {
        Object[] arrays = new Object[]{"id", id};
        request.okhttpget(API.priceDetails, arrays, callBack);
    }

    /**
     *账单详情-获取对账单明细
     * @param id
     * @param callBack
     */
    public void getBillDetails(String id,int page,String pname, HttpCallBack<BillDetailsEntity> callBack) {
        Object[] arrays = new Object[]{"OrderId", id,"PageIndex",page,"PName",pname};
        request.okhttpget(API.billDetails, arrays, callBack);
    }

    /**
     *账单详情-获取详情信息
     * @param id
     * @param callBack
     */
    public void getBillDifferentDetails(String id, HttpCallBack<BillDifferentEntity> callBack) {
        Object[] arrays = new Object[]{"Id", id};
        request.okhttpget(API.billDifferentDetails, arrays, callBack);
    }

    /**
     * 处理差异-筛选医院耗材列表
     * @param searchName
     * @param callBack
     */
    public void getDifferentList(String searchName, HttpCallBack<DoDifferenceEntity> callBack) {
        Object[] arrays = new Object[]{"KeyWords", searchName};
        request.okhttpget(API.differentDetails, arrays, callBack);
    }

    /**
     * 处理差异
     * @param orderid 对账单ID
     * @param differences [
     *     {
     *       "spid": "string",  供应商耗材ID
     *       "quantity": 0, 差异数量（非0）
     *       "remarks": "string" 备注
     *     }
     * @param callBack
     */
    public void dillDifferentList(String orderid,String differences, HttpCallBack callBack) {
        JSONArray array=null;
        try {
            array = new JSONArray(differences);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Object[] arrays = new Object[]{"orderId", orderid,"differences",array};
        request.okhttppost(API.dillDifferent, arrays, callBack);
    }

    /**
     * 入库统计
     *
     * @param year
     * @param callBack
     */
    public void getSelectSumChart(String year, HttpCallBack<List<SelectSumChartEntity>> callBack) {
        Object[] arrays = new Object[]{"year", year};
        request.okhttpget(API.selectSumChart, arrays, callBack);
    }

    /**
     * 企业资质预警
     * @param page
     * @param callBack
     */
    public void getCompanyWarning(int page, HttpCallBack<CompanyWarningEntity> callBack) {
        Object[] arrays = new Object[]{"PageIndex", page};
        request.okhttpget(API.companyWarning, arrays, callBack);
    }

    /**
     * 耗材库存效期预警
     * @param page
     * @param callBack
     */
    public void getConsumableStoreWarning(int page, HttpCallBack<ConsumableStoreEntity> callBack) {
        Object[] arrays = new Object[]{"PageIndex", page};
        request.okhttpget(API.hckcWarning, arrays, callBack);
    }

    /**
     * 耗材资质预警
     * @param page
     * @param callBack
     */
    public void getConsumableQualityWarning(int page, HttpCallBack<ConsumableQualityEntity> callBack) {
        Object[] arrays = new Object[]{"PageIndex", page};
        request.okhttpget(API.hczzWarning, arrays, callBack);
    }

    /**
     * 合同效期预警
     * @param page
     * @param callBack
     */
    public void getContractWarning(int page, HttpCallBack<ContractWarnintEntity> callBack) {
        Object[] arrays = new Object[]{"PageIndex", page};
        request.okhttpget(API.contractWarning, arrays, callBack);
    }
    /**
     * 获取用户信息
     *
     * @param callBack
     */
    public void uerInfo(HttpCallBack<User> callBack) {
        Object[] arrays = new Object[]{};
        request.okhttpget(API.userInfo, arrays, callBack);
    }

    /**
     * 关于我们
     *
     * @param callBack
     */
    public void aboutUs(HttpCallBack<User> callBack) {
        Object[] arrays = new Object[]{};
        request.okhttpget(API.aboutUs, arrays, callBack);
    }

    /**
     * 修改密码
     * @param oldPsw
     * @param newPsw
     * @param conPsw
     * @param callBack
     */
    public void updatePsw(String oldPsw,String newPsw,String conPsw,HttpCallBack callBack){
        Object[] arrays = new Object[] {"oldPassword",MedicalEncry.encrypt(oldPsw),
                "loginPassword",MedicalEncry.encrypt(newPsw),
                "confirmLoginPassword",MedicalEncry.encrypt(conPsw)};
        request.okhttppost(API.updatePsw,arrays,callBack);
    }
}
