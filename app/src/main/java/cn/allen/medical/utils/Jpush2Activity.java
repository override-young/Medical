package cn.allen.medical.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;

import allen.frame.tools.Logger;
import cn.allen.medical.count.CountLyActivity;
import cn.allen.medical.count.KucunCountActivity;
import cn.allen.medical.count.SelectSumChartActivity;
import cn.allen.medical.count.SysltjActivity;
import cn.allen.medical.entry.MeMenu;
import cn.allen.medical.entry.MenuEnum;
import cn.allen.medical.todo.GysDlcActivity;
import cn.allen.medical.todo.ToDoBillActivity;
import cn.allen.medical.todo.ToDoContractActivity;
import cn.allen.medical.todo.ToDoPriceActivity;
import cn.allen.medical.warning.CompanyWarningActivity;
import cn.allen.medical.warning.ConsumableQualityWarningActivity;
import cn.allen.medical.warning.ConsumableStoreWarningActivity;
import cn.allen.medical.warning.ContractWarningActivity;
import cn.allen.medical.warning.GysCompanyWarningActivity;
import cn.allen.medical.warning.GysFirmWarningActivity;
import cn.allen.medical.warning.GysProxyWarningActivity;

public class Jpush2Activity {

    private Context context;

    public Jpush2Activity(Context context) {
        this.context = context;
    }

    public void push2Activity(MeMenu menu){
        if(menu==null){
            return;
        }
        String code = menu.getCode();
        Logger.e("jpush",code);
        switch (code){
            case MenuEnum.todo_ht://待确认合同
                context.startActivity(new Intent(context,ToDoContractActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
            case MenuEnum.todo_zd_ks://待确认账单（科室）
                context.startActivity(new Intent(context,ToDoBillActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
            case MenuEnum.todo_zd_sb://待确认账单（设备室）
                context.startActivity(new Intent(context,ToDoBillActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
            case MenuEnum.todo_jg://待确认价格
                context.startActivity(new Intent(context,ToDoPriceActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
            case MenuEnum.waring_zz:
                context.startActivity(new Intent(context,CompanyWarningActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
            case MenuEnum.waring_xq:
                context.startActivity(new Intent(context,ConsumableStoreWarningActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
            case MenuEnum.waring_hc:
                context.startActivity(new Intent(context,ConsumableQualityWarningActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
            case MenuEnum.waring_ht:
                context.startActivity(new Intent(context,ContractWarningActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;

            case MenuEnum.gys_waring_zz://供应商企业资质预警
                context.startActivity(new Intent(context,GysCompanyWarningActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
            case MenuEnum.gys_waring_cs://供应商厂商资质预警
                context.startActivity(new Intent(context,GysFirmWarningActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
            case MenuEnum.gys_waring_hc://供应商耗材资质预警
                context.startActivity(new Intent(context,ConsumableQualityWarningActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
            case MenuEnum.gys_waring_ht://供应商合同效期预警
                context.startActivity(new Intent(context,ContractWarningActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
            case MenuEnum.gys_waring_dl://代理授权书预警
                context.startActivity(new Intent(context,GysProxyWarningActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
            case MenuEnum.count_rk:
                context.startActivity(new Intent(context,SelectSumChartActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
            case MenuEnum.count_kcsl:
                context.startActivity(new Intent(context,KucunCountActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
            case MenuEnum.count_ly:
                context.startActivity(new Intent(context,CountLyActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
            case MenuEnum.count_sy:
                context.startActivity(new Intent(context,SysltjActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
            case MenuEnum.gys_todo:
                context.startActivity(new Intent(context,GysDlcActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
            case MenuEnum.gys_count_fh:
                context.startActivity(new Intent(context,SelectSumChartActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
            case MenuEnum.gys_count_kcsl:
                context.startActivity(new Intent(context,KucunCountActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
            case MenuEnum.gys_count_ly:
                context.startActivity(new Intent(context,CountLyActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP ).putExtra("Menu",menu));
                break;
        }
    }

}
