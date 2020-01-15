package cn.allen.medical;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import allen.frame.AllenBaseActivity;

public class GuldActivity extends AllenBaseActivity {
    @Override
    protected boolean isStatusBarColorWhite() {
        return true;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_guild;
    }

    @Override
    protected void initBar() {

    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        handler.postDelayed(runnable,2000);
    }

    @Override
    protected void addEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(context,LoginActivity.class));
            finish();
        }
    };
    private Handler handler = new Handler(){};
}
