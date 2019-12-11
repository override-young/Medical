package cn.allen.medical;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;

import allen.frame.AllenBaseActivity;
import allen.frame.widget.ContrlScrollViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AllenBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar bar;
    @BindView(R.id.center_panel)
    ContrlScrollViewPager centerPanel;
    @BindView(R.id.bottom_bar)
    BottomNavigationView bottomBar;

    @Override
    protected boolean isStatusBarColorWhite() {
        return true;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void addEvent() {

    }
}
