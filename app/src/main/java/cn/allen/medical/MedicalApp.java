package cn.allen.medical;

import android.app.Application;

import allen.frame.AllenManager;
import allen.frame.tools.Logger;
import cn.allen.medical.utils.Constants;
import cn.jpush.android.api.JPushInterface;

public class MedicalApp extends Application {
    private static final String TAG = "JIGUANG-Example";

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init().setHttp(Constants.IsDebug).setDebug(Constants.IsDebug);
        JPushInterface.setDebugMode(Constants.IsDebug); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
        AllenManager.init(this);
    }
}
