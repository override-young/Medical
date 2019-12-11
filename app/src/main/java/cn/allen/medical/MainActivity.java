package cn.allen.medical;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import java.util.ArrayList;

import allen.frame.AllenBaseActivity;
import allen.frame.entry.Type;
import allen.frame.widget.BadgeView;
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

    private BadgeView dclBadge,yjBadge;

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
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomBar.getChildAt(0);
        dclBadge = new BadgeView(this);
        yjBadge = new BadgeView(this);
        dclBadge.setTargetView(menuView.getChildAt(0));
        dclBadge.setBadgeCount(2);
        dclBadge.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        dclBadge.setHideOnNull(true);
        yjBadge.setTargetView(menuView.getChildAt(2));
        yjBadge.setBadgeCount(5);
        yjBadge.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        yjBadge.setHideOnNull(true);
    }

    @Override
    protected void addEvent() {
        bottomBar.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomBar.setSelectedItemId(R.id.item_dcl);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            setAppModule(menuItem.getItemId());
            return true;
        }

    };
    private void setAppModule(int module) {
        switch (module) {
            case R.id.item_dcl:
                bar.setTitle("待处理");
                break;
            case R.id.item_data:
                bar.setTitle("数据统计");
                break;
            case R.id.item_xqyj:
                bar.setTitle("效期预警");
                break;
            case R.id.item_mine:
                bar.setTitle("");
                break;
        }
    }
}
