package cn.allen.medical;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import allen.frame.AllenBaseActivity;
import allen.frame.tools.Logger;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.allen.medical.data.DataHelper;
import cn.allen.medical.data.HttpCallBack;
import cn.allen.medical.data.HttpRequest;
import cn.allen.medical.data.MeRespone;
import cn.allen.medical.utils.Constants;

public class ScanLoginActivity extends AllenBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar bar;
    @BindView(R.id.scan_ok)
    AppCompatButton scanOk;
    @BindView(R.id.scan_no)
    AppCompatButton scanNo;
    private String url;

    @Override
    protected boolean isStatusBarColorWhite() {
        return true;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_scan_login;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        actHelper.setToolbarTitleCenter(bar,"扫码登录");
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        url = getIntent().getStringExtra("url");
        Logger.e("debug",url);
    }

    @Override
    protected void addEvent() {
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void scanLogin(){
        DataHelper.init().scanLogin(url, new HttpCallBack() {
            @Override
            public void onSuccess(Object respone) {

            }

            @Override
            public void onTodo(MeRespone respone) {
                Logger.e("debug","scan:"+respone.toString());
            }

            @Override
            public void tokenErro(MeRespone respone) {
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onFailed(MeRespone respone) {
                Logger.e("debug","scan:"+respone.toString());
            }
        });
    }

    @OnClick({R.id.scan_ok, R.id.scan_no})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.scan_ok:
                scanLogin();
                break;
            case R.id.scan_no:
                finish();
                break;
        }
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    break;
                case 1:
                    startActivityForResult(new Intent(context,LoginActivity.class).putExtra(Constants.Login_Token_Erro,true),11);
                    break;
            }
        }
    };
}
