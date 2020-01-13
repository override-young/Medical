package cn.allen.medical;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Html;
import android.view.View;
import android.widget.CompoundButton;

import allen.frame.AllenBaseActivity;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import allen.frame.tools.TimeMeter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.allen.medical.data.DataHelper;
import cn.allen.medical.data.HttpCallBack;
import cn.allen.medical.data.MeRespone;
import cn.allen.medical.entry.User;
import cn.allen.medical.utils.Constants;

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
    private boolean isTokenErro = false;

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
        Logger.init().setHttp(Constants.IsDebug).setDebug(Constants.IsDebug);
        meter = new TimeMeter();
        meter.setMaxTime(60);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        loginForget.setVisibility(View.GONE);
        isTokenErro = getIntent().getBooleanExtra(Constants.Login_Token_Erro,false);
        change();
        boolean isRemember = actHelper.getSharedPreferences().getBoolean(Constants.Remember_Psw,false);
        loginGetBox.setChecked(isRemember);
        if(isRemember){
            loginAccout.setText(actHelper.getSharedPreferences().getString(Constants.User_Account,""));
            loginPsw.setText(actHelper.getSharedPreferences().getString(Constants.User_Psw,""));
        }
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
                isCanGetYzm = true;
                loginGetYzm.setText(getString(R.string.login_again_yzm));
            }
        });
        loginGetBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                actHelper.getSharedPreferences().edit().putBoolean(Constants.Remember_Psw,isChecked).commit();
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
                getYzm();
                break;
            case R.id.login_bt:
                if(checkIsOk()){
                    login();
                }
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
    private void login(){
        showProgressDialog("");
        DataHelper.init().login(isPhoneLogin?2:1, zh, psw, yzm, new HttpCallBack<User>() {
            @Override
            public void onSuccess(User muser) {
                Logger.e("debug",muser.toString());
                user = muser;
                SharedPreferences shared = actHelper.getSharedPreferences();
                SharedPreferences.Editor editor = shared.edit();
                editor.putString(Constants.User_ID,user.getUserId());
                editor.putString(Constants.User_Token,user.getToken());
                if(!isPhoneLogin){
                    boolean isRemember = actHelper.getSharedPreferences().getBoolean(Constants.Remember_Psw,false);
                    if(isRemember){
                        editor.putString(Constants.User_Account,zh);
                        editor.putString(Constants.User_Psw,psw);
                    }
                }
                editor.commit();
            }
            @Override
            public void onTodo(MeRespone respone){
                Message msg = new Message();
                msg.what = 0;
                msg.obj = respone.getMessage();
                handler.sendMessage(msg);
            }

            @Override
            public void tokenErro(MeRespone respone) {

            }

            @Override
            public void onFailed(MeRespone respone) {
                Logger.e("debug",respone.toString());
                Message msg = new Message();
                msg.what = -1;
                msg.obj = respone.getMessage();
                handler.sendMessage(msg);
            }
        });
    }

    private boolean isCanGetYzm = true;
    private void getYzm(){
        if(!isCanGetYzm){
            MsgUtils.showMDMessage(context,"请60秒后再获取验证码!");
            return;
        }
        phone = loginPhone.getText().toString().trim();
        if(StringUtils.empty(phone)){
            MsgUtils.showMDMessage(context,"请输入手机号!");
            return;
        }
        showProgressDialog("");
        DataHelper.init().smsAuther(phone,"SystemLogin",new HttpCallBack() {
            @Override
            public void onSuccess(Object respone) {

            }

            @Override
            public void onTodo(MeRespone respone) {
                isCanGetYzm = false;
                isGetYzm = true;
                meter.start();
                Message msg = new Message();
                msg.what = 1;
                msg.obj = respone.getMessage();
                handler.sendMessage(msg);
            }

            @Override
            public void tokenErro(MeRespone respone) {

            }

            @Override
            public void onFailed(MeRespone respone) {
                isCanGetYzm = true;
                Message msg = new Message();
                msg.what = -1;
                msg.obj = respone.getMessage();
                handler.sendMessage(msg);
            }
        });
    }

    private User user;
    private String zh,psw,phone,yzm;
    private boolean isGetYzm = false;
    private boolean checkIsOk(){
        zh = loginAccout.getText().toString().trim();
        psw = loginPsw.getText().toString().trim();
        phone = loginPhone.getText().toString().trim();
        yzm = loginYzm.getText().toString().trim();
        if(isPhoneLogin){
            if(StringUtils.empty(phone)){
                MsgUtils.showMDMessage(context,"请输入手机号!");
                return false;
            }
            if(!isGetYzm){
                MsgUtils.showMDMessage(context,"请先获取验证码!");
                return false;
            }
            if(StringUtils.empty(yzm)){
                MsgUtils.showMDMessage(context,"请输入验证码!");
                return false;
            }
        }else{
            if(StringUtils.empty(zh)){
                MsgUtils.showMDMessage(context,"请输入账号!");
                return false;
            }
            if(StringUtils.empty(psw)){
                MsgUtils.showMDMessage(context,"请输入密码!");
                return false;
            }
        }
        return true;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case -1:
                    dismissProgressDialog();
                    MsgUtils.showMDMessage(context, (String) msg.obj);
                    break;
                case 0:
                    dismissProgressDialog();
                    DataHelper.init().refush();
                    if(isTokenErro){
                        setResult(RESULT_OK,getIntent());
                        finish();
                    }else{
                        startActivity(new Intent(context,MainActivity.class));
                    }
                    break;
                case 1:
                    dismissProgressDialog();

                    break;
            }
        }
    };

}
