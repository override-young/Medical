package cn.allen.medical;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import allen.frame.AllenBaseActivity;
import allen.frame.tools.TimeMeter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePswActivity extends AllenBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar bar;
    private TimeMeter meter;

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
        meter = TimeMeter.getInstance().setMaxTime(60);
        bar.setTitle("修改密码");
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actHelper.setToolbarTitleCenter(bar,"修改密码");
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {

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

            }

            @Override
            public void onInTime(long inTime) {

            }

            @Override
            public void onEnd() {

            }
        });
    }
}
