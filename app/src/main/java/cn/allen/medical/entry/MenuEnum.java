package cn.allen.medical.entry;

import java.util.HashMap;
import java.util.Map;

import cn.allen.medical.R;

public class MenuEnum {

    private static Map<String,Integer> resId;
    public final static String todo = "A3FA1DD6038A4A448A8910B90F795F3C";//待处理
    public final static String todo_ht= "B8035CD7AC1F4F2DBBC75BA2884E9107";//待确认合同
    public final static String todo_zd_ks = "560E4D9BC98E49329FC6CECB3F6C41B2";//待确认账单(科室)",
    public final static String todo_zd_sb = "76E271E365D84E3FA5110703C8CB109E";//待确认账单(设备科)",
    public final static String todo_jg = "E6EC42528B7D40CCAB316C7FD58A87F4";//待确认价格
    public final static String count = "04F714A27327471E883BB756E86A4E98";//数据统计
    public final static String count_rk = "63F5B26B5D6B4A40B0CFB43EFE492261";//入库数量统计
    public final static String count_kcsl = "CC55B5CCFE7C40139FF5FF87084B24D6";//库存数量统计
    public final static String count_ly = "7AE4CA4A6CE34D099C2045E7D5ADD95F";//领用数量统计
    public final static String count_sy = "EE41BBDE53234A44B7A46B0882993331";//使用数量统计
    public final static String waring = "FFBABBE6080D4CBBA8AA7451DE82AE83";//效期预警
    public final static String waring_zz = "1CF606C6A1B04FF2819EBD9EAE062C0C";//企业资质预警
    public final static String waring_xq = "21493937DB4243169288A9FE48963562";//耗材库存效期预警
    public final static String waring_hc = "14C9606BEFFF49F8933A95DBD101B106";//耗材资质预警
    public final static String waring_ht = "CCB91E4DAB6940829A8D364ABFEB2164";//合同效期预警
    /*供应商端*/
    public final static String gys_todo = "797CCA060A2B438EAAF5ADA4B60C7E50";//待处理
    public final static String gys_count = "3CC220313FCC42B0AF9E177551393B74";//数据统计
    public final static String gys_count_fh = "822DB41459584700BA803DD72A812C41";//发货数量统计
    public final static String gys_count_kcsl = "23FB25D7714E4AE5B218F4DC6F36FF7B";//库存数量统计
    public final static String gys_count_ly = "AD439E91962D4F5E98A2DF99945B626F";//领用数量统计
    public final static String gys_waring = "5B8E8CA12856427593705C268B979BD8";//效期预警
    public final static String gys_waring_zz = "B46143A488C44D0196022D9217BC5DAD";//企业资质预警
    public final static String gys_waring_cs = "D2ED25461D5543F4B7287EA3586EAF32";//厂商资质预警
    public final static String gys_waring_hc = "65906BBFA6B34F798E4E9AC77BDD2E36";//耗材资质预警
    public final static String gys_waring_dl = "31F33FA530B34A7A94B1EB6D6AD2C4B0";//代理授权书预警
    public final static String gys_waring_ht = "E40CAEAD35254037808495A34C74B833";//合同效期预警

    public final static String mine = "6546510351315365465";//我的

    public static final int Todo_ItemId = 12340;
    public static final int Count_ItemId = 12341;
    public static final int Waring_ItemId = 12342;
    public static final int Mine_ItemId = 12343;

    static {
        resId = new HashMap<>();
        resId.put(todo, R.drawable.menu_dcl);//待处理
        resId.put(gys_todo, R.drawable.menu_dcl);//待处理
        resId.put(todo_ht, R.mipmap.todo_ht);//待确认合同
        resId.put(todo_zd_ks, R.mipmap.todo_zd);//待确认账单(科室)",
        resId.put(todo_zd_sb, R.mipmap.todo_zd);//待确认账单(设备科)",
        resId.put(todo_jg, R.mipmap.todo_jg);//待确认价格
        resId.put(count, R.drawable.menu_sjtj);//数据统计
        resId.put(gys_count, R.drawable.menu_sjtj);//数据统计
        resId.put(count_rk, R.mipmap.count_rk);//入库数量统计
        resId.put(count_kcsl, R.mipmap.count_kcsl);//库存数量统计
        resId.put(gys_count_kcsl, R.mipmap.count_kcsl);//库存数量统计
        resId.put(count_ly, R.mipmap.count_ly);//领用数量统计
        resId.put(gys_count_ly, R.mipmap.count_ly);//领用数量统计
        resId.put(count_sy, R.mipmap.count_sy);//使用数量统计
        resId.put(gys_count_fh, R.mipmap.count_fh);//发货数量统计
        resId.put(waring, R.drawable.menu_yjxh);//效期预警
        resId.put(gys_waring, R.drawable.menu_yjxh);//效期预警
        resId.put(waring_zz, R.mipmap.waring_zz);//企业资质预警
        resId.put(gys_waring_zz, R.mipmap.waring_zz);//企业资质预警
        resId.put(waring_xq, R.mipmap.waring_xq);//耗材库存效期预警
        resId.put(waring_hc, R.mipmap.waring_hc);//耗材资质预警
        resId.put(gys_waring_hc, R.mipmap.waring_hc);//耗材资质预警
        resId.put(waring_ht, R.mipmap.waring_ht);//合同效期预警
        resId.put(gys_waring_ht, R.mipmap.waring_ht);//合同效期预警
        resId.put(gys_waring_dl, R.mipmap.waring_dl);//代理授权书预警
        resId.put(gys_waring_cs, R.mipmap.waring_cs);//厂商资质预警
        resId.put(mine, R.mipmap.menu_wd);//我的
    }

    public static int getResId(String code){
        int id = 0;
        if(resId.containsKey(code)){
            return resId.get(code);
        }
        return id;
    }
}
