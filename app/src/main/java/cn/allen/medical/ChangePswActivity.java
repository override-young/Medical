package cn.allen.medical;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;

import allen.frame.AllenBaseActivity;
import allen.frame.tools.TimeMeter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePswActivity extends AllenBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar bar;
    @BindView(R.id.user_phone)
    AppCompatEditText userPhone;
    @BindView(R.id.user_yzm)
    AppCompatEditText userYzm;
    @BindView(R.id.user_get_yzm)
    AppCompatTextView userGetYzm;
    @BindView(R.id.phone_psw_layout)
    LinearLayoutCompat phonePswLayout;
    @BindView(R.id.user_old_psw)
    AppCompatEditText userOldPsw;
    @BindView(R.id.old_psw_layout)
    LinearLayoutCompat oldPswLayout;
    @BindView(R.id.user_new_psw)
    AppCompatEditText userNewPsw;
    @BindView(R.id.user_confirm_psw)
    AppCompatEditText userConfirmPsw;
    @BindView(R.id.ok_bt)
    AppCompatButton okBt;
    @BindView(R.id.psw_change)
    AppCompatTextView pswChange;
    private TimeMeter meter;

    private boolean isPhone = false;

    @Override
    protected boolean isStatusBarColorWhite() {
        return true;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_change_psw;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        meter = new TimeMeter();
        meter.setMaxTime(60);
        actHelper.setToolbarTitleCenter(bar, "修改密码");
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        change();
    }

    @Override
    protected void addEvent() {
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        meter.setTimerLisener(new TimeMeter.OnTimerLisener() {
            @Override
            public void onStart() {
                userGetYzm.setText(Html.fromHtml("<font color=\"red\">60</font>"+getString(R.string.login_ing_yzm)));
            }

            @Override
            public void onInTime(long inTime) {
                userGetYzm.setText(Html.fromHtml("<font color=\"red\">"+inTime+"</font>"+getString(R.string.login_ing_yzm)));
            }

            @Override
            public void onEnd() {
                userGetYzm.setText(getString(R.string.login_again_yzm));
            }
        });
    }

    @OnClick({R.id.user_get_yzm, R.id.ok_bt, R.id.psw_change})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_get_yzm:
                meter.start();
                break;
            case R.id.ok_bt:
                break;
            case R.id.psw_change:
                isPhone = !isPhone;
                change();
                break;
        }
    }

    private void change(){
        if(isPhone){
            phonePswLayout.setVisibility(View.VISIBLE);
            oldPswLayout.setVisibility(View.GONE);
            pswChange.setText(getText(R.string.change_bypsw_type));
            pswChange.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.change_psw,0,0,0);
        }else{
            phonePswLayout.setVisibility(View.GONE);
            oldPswLayout.setVisibility(View.VISIBLE);
            pswChange.setText(getText(R.string.change_byyzm_type));
            pswChange.setCompoundDrawablesRelativeWithIntrinsicBounds(R.mipmap.change_phone,0,0,0);
        }
    }

}
