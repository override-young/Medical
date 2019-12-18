package cn.allen.medical.data;

public class API {
    public static String login = "/api/mobile/Authorize/SysLoginIn";
    public static String exit = "/api/mobile/User/LoginOut";
    public static String userInfo = "/api/mobile/User/GetUserProfile";
    public static String updatePsw = "/api/mobile/User/UpdateUserPassword";
    //待处理合同列表
    public static String todoContract = "/api/mobile/HospitalApp/GetWaitConfirmContractPageList";
    //合同详情
    public static String contractDetails = "/api/mobile/HospitalApp/GetWaitConfirmContractPageList";

}
