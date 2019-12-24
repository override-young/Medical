package cn.allen.medical.data;

public class API {
    public static String login = "/api/mobile/Authorize/SysLoginIn";
    public static String exit = "/api/mobile/User/LoginOut";
    public static String userInfo = "/api/mobile/User/GetUserProfile";
    public static String aboutUs = "/api/mobile/Common/GetAboutInfo";
    public static String updatePsw = "/api/mobile/User/UpdateUserPassword";
    public static String updatePswByPsw = "/api/mobile/User/UpdateUserPassword";
    public static String updatePswBySms = "/api/mobile/User/MobileUpdatePassword";
    public static String userAuth = "/api/mobile/User/GetUserAuth";
    public static String smsAuth = "/api/mobile/Common/SendVerificationCode";

    public static final String todoCount = "/api/mobile/HospitalApp/GetWaitConfirmCount";
    public static final String waringCount = "/api/mobile/HospitalApp/GetExpireWarningCount";

    //待处理合同列表
    public static String todoContract = "/api/mobile/HospitalApp/GetWaitConfirmContractPageList";
    //待确认账单（科室）
    public static String todoBillByDep = "/api/mobile/HospitalApp/GetFinanceBillOrderByDeptPageList";
    //待确认账单（设备科）
    public static String todoBillByDevice = "/api/mobile/HospitalApp/GetFinanceBillOrderByDevicePageList";
    //待确认价格
    public static String todoPrice = "/api/mobile/HospitalApp/GetPriceExaminePageList";

    //合同详情
    public static String contractDetails = "/api/mobile/HospitalApp/GetContractDetails";
    //价格详情
    public static String priceDetails = "/api/mobile/HospitalApp/GetPriceEntity";
    //账单详情-获取详情信息
    public static String billDifferentDetails = "/api/mobile/HospitalApp/GetFinanceBillOrderEntity";
    /**
     * 处理差异-筛选医院耗材列表
     */
    public static String differentDetails = "/api/mobile/HospitalApp/SupplierProductPageListSearch";
    /**
     * 处理差异
     */
    public static String dillDifferent = "/api/mobile/HospitalApp/FinanceBillOrderHandleDifference";
    //账单详情-获取对账单明细
    public static String billDetails = "/api/mobile/HospitalApp/GetFinanceBillOrderDetailsPageList";
    //入库统计
    public static String selectSumChart = "/api/mobile/HospitalApp/InDepotStatistics";
    //企业资质预警
    public static String companyWarning = "/api/mobile/HospitalApp/GetCertWarnPageList";
    //耗材库存效期预警
    public static String hckcWarning = "/api/mobile/HospitalApp/GetProductWarnPageList";
    //耗材资质预警
    public static String hczzWarning = "/api/mobile/HospitalApp/GetProductCertWarnPageList";
    //合同效期预警
    public static String contractWarning = "/api/mobile/HospitalApp/GetContractWarnPageList";

}
