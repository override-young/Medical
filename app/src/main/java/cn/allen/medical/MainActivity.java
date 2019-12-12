package cn.allen.medical;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import allen.frame.AllenBaseActivity;
import allen.frame.adapter.FragmentAdapter;
import allen.frame.widget.BadgeView;
import allen.frame.widget.ContrlScrollViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.allen.medical.count.CountFragment;
import cn.allen.medical.mine.MineFragment;
import cn.allen.medical.todo.TodoFragment;
import cn.allen.medical.warning.WarningFragment;

public class MainActivity extends AllenBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar bar;
    @BindView(R.id.center_panel)
    ContrlScrollViewPager centerPanel;
    @BindView(R.id.bottom_bar)
    BottomNavigationView bottomBar;

    private BadgeView dclBadge,yjBadge;
    private int index = 0;

    private FragmentAdapter adapter;
    private ArrayList<Fragment> list;

    @Override
    protected boolean isStatusBarColorWhite() {
        return true;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sm, menu);
        bar.getMenu().getItem(0).setVisible(false);
        return true;
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
        list = new ArrayList<>();
        list.add(TodoFragment.init());
        list.add(CountFragment.init());
        list.add(WarningFragment.init());
        list.add(MineFragment.init());
        adapter = new FragmentAdapter(getSupportFragmentManager(),list);
        centerPanel.setAdapter(adapter);
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
                index = 0;
                bar.setTitle("待处理");
                if(bar.getMenu().size()>0){
                    bar.getMenu().getItem(0).setVisible(false);
                }
                break;
            case R.id.item_data:
                index = 1;
                bar.setTitle("数据统计");
                if(bar.getMenu().size()>0){
                    bar.getMenu().getItem(0).setVisible(false);
                }
                break;
            case R.id.item_xqyj:
                index = 2;
                bar.setTitle("效期预警");
                if(bar.getMenu().size()>0){
                    bar.getMenu().getItem(0).setVisible(false);
                }
                break;
            case R.id.item_mine:
                index = 3;
                bar.setTitle("");
                if(bar.getMenu().size()>0){
                    bar.getMenu().getItem(0).setVisible(true);
                }
                break;
        }
        centerPanel.setCurrentItem(index,false);
    }

    @Override
    public void onBackPressed() {
        actHelper.doClickTwiceExit(centerPanel);
    }
}
