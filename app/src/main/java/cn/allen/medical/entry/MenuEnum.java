package cn.allen.medical.entry;

import java.util.HashMap;
import java.util.Map;

import cn.allen.medical.R;

public class MenuEnum {

    private static Map<String,Integer> resId;
    public static String todo = "A3FA1DD6038A4A448A8910B90F795F3C";//待处理
    public static String todo_ht= "B8035CD7AC1F4F2DBBC75BA2884E9107";//待确认合同
    public static String todo_zd_ks = "560E4D9BC98E49329FC6CECB3F6C41B2";//待确认账单(科室)",
    public static String todo_zd_sb = "76E271E365D84E3FA5110703C8CB109E";//待确认账单(设备科)",
    public static String todo_jg = "E6EC42528B7D40CCAB316C7FD58A87F4";//待确认价格
    public static String count = "04F714A27327471E883BB756E86A4E98";//数据统计
    public static String count_rk = "63F5B26B5D6B4A40B0CFB43EFE492261";//入库数量统计
    public static String count_kcsl = "CC55B5CCFE7C40139FF5FF87084B24D6";//库存数量统计
    public static String count_ly = "7AE4CA4A6CE34D099C2045E7D5ADD95F";//领用数量统计
    public static String count_sy = "EE41BBDE53234A44B7A46B0882993331";//使用数量统计
    public static String waring = "FFBABBE6080D4CBBA8AA7451DE82AE83";//效期预警
    public static String waring_zz = "1CF606C6A1B04FF2819EBD9EAE062C0C";//企业资质预警
    public static String waring_xq = "21493937DB4243169288A9FE48963562";//耗材库存效期预警
    public static String waring_hc = "14C9606BEFFF49F8933A95DBD101B106";//耗材资质预警
    public static String waring_ht = "CCB91E4DAB6940829A8D364ABFEB2164";//合同效期预警

    static {
        resId = new HashMap<>();
        resId.put(todo, R.mipmap.menu_dcl);//待处理
        resId.put(todo_ht, R.mipmap.todo_ht);//待确认合同
        resId.put(todo_zd_ks, R.mipmap.todo_zd);//待确认账单(科室)",
        resId.put(todo_zd_sb, R.mipmap.todo_zd);//待确认账单(设备科)",
        resId.put(todo_jg, R.mipmap.todo_jg);//待确认价格
        resId.put(count, R.mipmap.menu_sjtj);//数据统计
        resId.put(count_rk, R.mipmap.count_rk);//入库数量统计
        resId.put(count_kcsl, R.mipmap.count_kcsl);//库存数量统计
        resId.put(count_ly, R.mipmap.count_ly);//领用数量统计
        resId.put(count_sy, R.mipmap.count_sy);//使用数量统计
        resId.put(waring, R.mipmap.menu_yjxh);//效期预警
        resId.put(waring_zz, R.mipmap.waring_zz);//企业资质预警
        resId.put(waring_xq, R.mipmap.waring_xq);//耗材库存效期预警
        resId.put(waring_hc, R.mipmap.waring_hc);//耗材资质预警
        resId.put(waring_ht, R.mipmap.waring_ht);//合同效期预警
    }

    public static int getResId(String code){
        int id = 0;
        if(resId.containsKey(code)){
            return resId.get(code);
        }
        return id;
    }
}
