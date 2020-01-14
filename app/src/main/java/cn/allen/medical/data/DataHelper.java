package cn.allen.medical.data;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import allen.frame.entry.Type;
import cn.allen.medical.entry.BillDetailsEntity;
import cn.allen.medical.entry.BillDifferentEntity;
import cn.allen.medical.entry.CompanyWarningEntity;
import cn.allen.medical.entry.ConsumableQualityEntity;
import cn.allen.medical.entry.ConsumableStoreEntity;
import cn.allen.medical.entry.ContractDetailsEntity;
import cn.allen.medical.entry.ContractWarnintEntity;
import cn.allen.medical.entry.DoDifferenceEntity;
import cn.allen.medical.entry.KucunCountEntity;
import cn.allen.medical.entry.PriceDetailsEntity;
import cn.allen.medical.entry.SelectSumChartEntity;
import cn.allen.medical.entry.SysltjEntity;
import cn.allen.medical.entry.ToDoBillEntity;
import cn.allen.medical.entry.MeMenu;
import cn.allen.medical.entry.ToDoContractEntity;
import cn.allen.medical.entry.TodoCount;
import cn.allen.medical.entry.ToDoPriceEntity;
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
     * @param isYy 是否医院端
     * @param callBack
     */
    public void waringCount(boolean isYy,HttpCallBack<WaringCount> callBack){
        Object[] arrays = new Object[] { };
        request.okhttpget(isYy?API.waringCount:API.gyswaringCount,arrays,callBack);
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
     * 合同审核
     * @param id
     * @param isAgree
     * @param remark
     * @param callBack
     */
    public void getContractExamine(String id,boolean isAgree, String remark,HttpCallBack callBack) {
        Object[] arrays = new Object[]{"id", id,"examineResult",isAgree,"remarks",remark};
        request.okhttppost(API.contractExamine, arrays, callBack);
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
     *审核价格
     * @param id
     * @param isAgree
     * @param remark
     * @param callBack
     */
    public void getPriceExamine(String id,boolean isAgree, String remark,HttpCallBack callBack) {
        Object[] arrays = new Object[]{"id", id,"isAgree",isAgree,"remarks",remark};
        request.okhttppost(API.priceExamine, arrays, callBack);
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
     * 账单确认（科室）
     * @param id
     * @param remark
     * @param callBack
     */
    public void getBillExamineKs(String id, String remark,HttpCallBack callBack) {
        Object[] arrays = new Object[]{"orderId", id,"remarks",remark};
        request.okhttppost(API.billExamineKs, arrays, callBack);
    }

    /**
     * 账单确认（设备科）
     * @param id
     * @param remark
     * @param callBack
     */
    public void getBillExamineSbk(String id, String remark,HttpCallBack callBack) {
        Object[] arrays = new Object[]{"orderId", id,"remarks",remark};
        request.okhttppost(API.billExamineSbk, arrays, callBack);
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
     * 库存数量统计
     * @param page
     * @param callBack
     */
    public void getKucunCount(int page, HttpCallBack<KucunCountEntity> callBack) {
        Object[] arrays = new Object[]{"PageIndex", page};
        request.okhttpget(API.kucunCount, arrays, callBack);
    }

    /**
     * 领用数量统计
     * @param page
     * @param keshiID 科室ID
     * @param startDate
     * @param endDate
     */
    public void getLiyongCount(int page,String keshiID,String startDate,String endDate , HttpCallBack<SysltjEntity> callBack) {
        Object[] arrays = new Object[]{"PageIndex", page,"DeptId",keshiID,"StartDate",startDate,"EndDate",endDate};
        request.okhttpget(API.liyongCount, arrays, callBack);
    }

    /**
     * 使用数量统计
     * @param page
     * @param keshiID 科室ID
     * @param startDate
     * @param endDate
     * @param callBack
     */
    public void getUseCount(int page,String keshiID,String startDate,String endDate , HttpCallBack<SysltjEntity> callBack) {
        Object[] arrays = new Object[]{"PageIndex", page,"DeptId",keshiID,"StartDate",startDate,"EndDate",endDate};
        request.okhttpget(API.useCount, arrays, callBack);
    }

    /**
     * 查询科室下拉列表
     * @param callBack
     */
    public void getKeShi(HttpCallBack<List<Type>> callBack) {
        Object[] arrays = new Object[]{};
        request.okhttpget(API.KeShiList, arrays, callBack);
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
