package cn.allen.medical;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Html;
import android.view.View;

import allen.frame.AllenBaseActivity;
import allen.frame.tools.Logger;
import allen.frame.tools.TimeMeter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AllenBaseActivity {

    @BindView(R.id.login_accout)
    AppCompatEditText loginAccout;
    @BindView(R.id.login_psw)
    AppCompatEditText loginPsw;
    @BindView(R.id.login_forget)
    AppCompatTextView loginForget;
    @BindView(R.id.login_get_box)
    AppCompatCheckBox loginGetBox;
    @BindView(R.id.zh_login_layout)
    LinearLayoutCompat zhLoginLayout;
    @BindView(R.id.login_phone)
    AppCompatEditText loginPhone;
    @BindView(R.id.login_yzm)
    AppCompatEditText loginYzm;
    @BindView(R.id.login_get_yzm)
    AppCompatTextView loginGetYzm;
    @BindView(R.id.yzm_login_layout)
    LinearLayoutCompat yzmLoginLayout;
    @BindView(R.id.login_bt)
    AppCompatButton loginBt;
    @BindView(R.id.login_change)
    AppCompatTextView loginChange;

    private boolean isPhoneLogin = false;
    private TimeMeter meter;

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
        ButterKnife.bind(this);
        meter = new TimeMeter();
        meter.setMaxTime(60);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        change();
    }

    @Override
    protected void addEvent() {
        meter.setTimerLisener(new TimeMeter.OnTimerLisener() {
            @Override
            public void onStart() {
                loginGetYzm.setText(Html.fromHtml("<font color=\"red\">60</font>"+getString(R.string.login_ing_yzm)));
            }

            @Override
            public void onInTime(long inTime) {
                loginGetYzm.setText(Html.fromHtml("<font color=\"red\">"+inTime+"</font>"+getString(R.string.login_ing_yzm)));
            }

            @Override
            public void onEnd() {
                loginGetYzm.setText(getString(R.string.login_again_yzm));
            }
        });
    }

    @OnClick({R.id.login_forget, R.id.login_get_yzm, R.id.login_bt, R.id.login_change})
    public void onViewClicked(View view) {
        if(actHelper.isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.login_forget:

                break;
            case R.id.login_get_yzm:
                Logger.e("debug","getyzm++");
                meter.start();
                break;
            case R.id.login_bt:
                startActivity(new Intent(context,MainActivity.class));
                break;
            case R.id.login_change:
                isPhoneLogin = !isPhoneLogin;
                change();
                break;
        }
    }

    private void change(){
        if(isPhoneLogin){
            zhLoginLayout.setVisibility(View.GONE);
            yzmLoginLayout.setVisibility(View.VISIBLE);
            loginChange.setText(getText(R.string.login_account_type));
            loginChange.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.ic_account,0,0,0);
        }else{
            zhLoginLayout.setVisibility(View.VISIBLE);
            yzmLoginLayout.setVisibility(View.GONE);
            loginChange.setText(getText(R.string.login_phone_type));
            loginChange.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.ic_phone,0,0,0);
        }
    }
}
