package cn.allen.medical.entry;

import java.util.HashMap;
import java.util.Map;

import cn.allen.medical.R;

public class MenuEnum {

    private static Map<String,Integer> resId;
    static {
        resId = new HashMap<>();
        resId.put("A3FA1DD6038A4A448A8910B90F795F3C", R.mipmap.menu_dcl);//待处理
        resId.put("B8035CD7AC1F4F2DBBC75BA2884E9107", R.mipmap.todo_ht);//待确认合同
        resId.put("560E4D9BC98E49329FC6CECB3F6C41B2", R.mipmap.todo_zd);//待确认账单(科室)",
        resId.put("76E271E365D84E3FA5110703C8CB109E", R.mipmap.todo_zd);//待确认账单(设备科)",
        resId.put("E6EC42528B7D40CCAB316C7FD58A87F4", R.mipmap.todo_jg);//待确认价格
        resId.put("04F714A27327471E883BB756E86A4E98", R.mipmap.menu_sjtj);//数据统计
        resId.put("63F5B26B5D6B4A40B0CFB43EFE492261", R.mipmap.count_rk);//入库数量统计
        resId.put("CC55B5CCFE7C40139FF5FF87084B24D6", R.mipmap.count_kcsl);//库存数量统计
        resId.put("7AE4CA4A6CE34D099C2045E7D5ADD95F", R.mipmap.count_ly);//领用数量统计
        resId.put("EE41BBDE53234A44B7A46B0882993331", R.mipmap.count_sy);//使用数量统计
        resId.put("FFBABBE6080D4CBBA8AA7451DE82AE83", R.mipmap.menu_yjxh);//效期预警
        resId.put("1CF606C6A1B04FF2819EBD9EAE062C0C", R.mipmap.waring_zz);//企业资质预警
        resId.put("21493937DB4243169288A9FE48963562", R.mipmap.waring_xq);//耗材库存效期预警
        resId.put("14C9606BEFFF49F8933A95DBD101B106", R.mipmap.waring_hc);//耗材资质预警
        resId.put("CCB91E4DAB6940829A8D364ABFEB2164", R.mipmap.waring_ht);//合同效期预警
    }

    public static int getResId(String code){
        int id = 0;
        if(resId.containsKey(code)){
            return resId.get(code);
        }
        return id;
    }
}
