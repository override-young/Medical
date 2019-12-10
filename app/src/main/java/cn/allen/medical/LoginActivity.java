package cn.allen.medical;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import allen.frame.AllenBaseActivity;
import allen.frame.tools.StatusBarUtil;

public class LoginActivity extends AllenBaseActivity {

    @Override
    protected boolean isStatusBarColorWhite() {
        return true;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initBar() {
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void addEvent() {

    }
}
