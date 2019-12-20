package cn.allen.medical.data;

public class API {
    public static String login = "/api/mobile/Authorize/SysLoginIn";
    public static String exit = "/api/mobile/User/LoginOut";
    public static String userInfo = "/api/mobile/User/GetUserProfile";
    public static String updatePswByPsw = "/api/mobile/User/UpdateUserPassword";
    public static String updatePswBySms = "/api/mobile/User/MobileUpdatePassword";
    public static String userAuth = "/api/mobile/User/GetUserAuth";
    public static String smsAuth = "/api/mobile/Common/SendVerificationCode";

    public static final String todoCount = "/api/mobile/HospitalApp/GetWaitConfirmCount";
    public static final String waringCount = "/api/mobile/HospitalApp/GetExpireWarningCount";

    //待处理合同列表
    public static String todoContract = "/api/mobile/HospitalApp/GetWaitConfirmContractPageList";
    //合同详情
    public static String contractDetails = "/api/mobile/HospitalApp/GetWaitConfirmContractPageList";

}
