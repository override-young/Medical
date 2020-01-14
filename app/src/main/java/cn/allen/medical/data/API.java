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

    public static String todoCount = "/api/mobile/HospitalApp/GetWaitConfirmCount";
    public static String gystodoCount = "/api/mobile/SupplierApp/GetWaitConfirmCount";
    public static String waringCount = "/api/mobile/HospitalApp/GetExpireWarningCount";
    public static String gyswaringCount = "/api/mobile/SupplierApp/GetExpireWarningCount";

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
    //合同审核
    public static String contractExamine = "/api/mobile/HospitalApp/ContractExamine";

    //价格详情
    public static String priceDetails = "/api/mobile/HospitalApp/GetPriceEntity";
    //价格审核
    public static String priceExamine = "/api/mobile/HospitalApp/PriceConfirm";
    //账单详情-获取详情信息
    public static String billDifferentDetails = "/api/mobile/HospitalApp/GetFinanceBillOrderEntity";
    //账单确认（科室）
    public static String billExamineKs = "/api/mobile/HospitalApp/FinanceBillOrderDeptConfirm";
    //账单确认（设备科）
    public static String billExamineSbk = "/api/mobile/HospitalApp/FinanceBillOrderDeviceConfirm";
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
    //库存数量统计
    public static String kucunCount = "/api/mobile/HospitalApp/GetStockStatisticPageList";
    //领用数量统计
    public static String liyongCount = "/api/mobile/HospitalApp/GetReceiveStatisticPageList";
    //查询科室下拉列表
    public static String KeShiList = "/api/mobile/HospitalApp/GetDeptList";
    //使用数量统计
    public static String useCount = "/api/mobile/HospitalApp/GetUsedStatisticPageList";
    //企业资质预警
    public static String companyWarning = "/api/mobile/HospitalApp/GetCertWarnPageList";
    //耗材库存效期预警
    public static String hckcWarning = "/api/mobile/HospitalApp/GetProductWarnPageList";
    //耗材资质预警
    public static String hczzWarning = "/api/mobile/HospitalApp/GetProductCertWarnPageList";
    //合同效期预警
    public static String contractWarning = "/api/mobile/HospitalApp/GetContractWarnPageList";

    //供应商端
    /**
     * 查询当前机构的所有合作机构列表
     */
    public static String hospitalList = "/api/mobile/SupplierApp/GetCooperationOrgList";
    /**
     * 发货数量统计
     */
    public static String gysSelectSumChart = "/api/mobile/SupplierApp/DeliveryStatisticsByMonth";
    /**
     * 库存数量统计
     */
    public static String gysKuncun = "/api/mobile/SupplierApp/GetHospitalProductStock";
    /**
     * 领用数量统计
     */
    public static String gysLiyong = "/api/mobile/SupplierApp/GetReceiveStatisticPageList";
    /**
     * 供应商企业资质预警
     */
    public static String gysCompanyWarning = "/api/mobile/SupplierApp/GetSelfCertWarnList";
    /**
     * 供应商厂商资质预警
     */
    public static String gysFirmWarning = "/api/mobile/SupplierApp/GetCertWarnPageList";
    /**
     *  供应商耗材资质预警
     */
    public static String gysHczzWarning = "/api/mobile/SupplierApp/GetProductCertWarnPageList";
    /**
     * 供应商合同效期预警
     */
    public static String gysContractWarning = "/api/mobile/SupplierApp/GetContractWarnPageList";
    /**
     * 代理授权书预警
     */
    public static String gysProxyWarning = "/api/mobile/SupplierApp/GetSupplierProductCertWarnPageList";
    /**
     * 供应商待确认订单列表
     */
    public static String todoGys = "/api/mobile/SupplierApp/GetPurchaseOrdersPageList";
    /**
     * 供应商待处理订单详情
     */
    public static String gysToDoDetails = "/api/mobile/SupplierApp/GetPurchaseOrdersDetails";
    /**
     * 供应商确认采购订单
     */
    public static String gysToDoExamine = "/api/mobile/SupplierApp/ConfirmPurchaseOrders";
}
