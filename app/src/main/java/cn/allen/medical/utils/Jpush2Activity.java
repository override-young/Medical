package cn.allen.medical.utils;

import android.content.Context;
import android.content.Intent;

import cn.allen.medical.entry.MeMenu;

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
        switch (code){
            case "":
//                context.startActivity(new Intent(context,xx).putExtra("Menu",menu));
                break;
        }
    }

}
