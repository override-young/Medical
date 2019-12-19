package cn.allen.medical;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.adapter.FragmentAdapter;
import allen.frame.tools.Logger;
import allen.frame.widget.BadgeView;
import allen.frame.widget.ContrlScrollViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.allen.medical.count.CountFragment;
import cn.allen.medical.data.DataHelper;
import cn.allen.medical.data.HttpCallBack;
import cn.allen.medical.data.MeRespone;
import cn.allen.medical.entry.MeMenu;
import cn.allen.medical.entry.MenuEnum;
import cn.allen.medical.entry.Role;
import cn.allen.medical.mine.MineFragment;
import cn.allen.medical.todo.TodoFragment;
import cn.allen.medical.utils.Constants;
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

//        dclBadge = new BadgeView(this);
//        dclBadge.setTargetView(menuView.getChildAt(0));
//        dclBadge.setBadgeCount(2);
//        dclBadge.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
//        dclBadge.setHideOnNull(true);
//        yjBadge = new BadgeView(this);
//        yjBadge.setTargetView(menuView.getChildAt(2));
//        yjBadge.setBadgeCount(5);
//        yjBadge.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
//        yjBadge.setHideOnNull(true);
        userAthur();
    }

    @Override
    protected void addEvent() {
        bottomBar.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
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
            case MenuEnum.Todo_ItemId:
                index = 0;
                bar.setTitle("待处理");
                if(bar.getMenu().size()>0){
                    bar.getMenu().getItem(0).setVisible(false);
                }
                break;
            case MenuEnum.Count_ItemId:
                index = 1;
                bar.setTitle("数据统计");
                if(bar.getMenu().size()>0){
                    bar.getMenu().getItem(0).setVisible(false);
                }
                break;
            case MenuEnum.Waring_ItemId:
                index = 2;
                bar.setTitle("效期预警");
                if(bar.getMenu().size()>0){
                    bar.getMenu().getItem(0).setVisible(false);
                }
                break;
            case MenuEnum.Mine_ItemId:
                index = 3;
                bar.setTitle("");
                if(bar.getMenu().size()>0){
                    bar.getMenu().getItem(0).setVisible(true);
                }
                break;
        }
        centerPanel.setCurrentItem(index,false);
    }

    private List<MeMenu> roles;
    private void userAthur(){
        DataHelper.init().userAuth(new HttpCallBack<List<MeMenu>>() {
            @Override
            public void onSuccess(List<MeMenu> respone) {
                roles = respone;
                for(MeMenu r:roles){
                    Logger.e("debug",r.toString());
                }
            }

            @Override
            public void onTodo(MeRespone respone) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void tokenErro(MeRespone respone) {

            }

            @Override
            public void onFailed(MeRespone respone) {

            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    list = new ArrayList<>();
                    int i = 0;
                    for(MeMenu menu:roles){
                        String code = menu.getCode();
                        if(code.equals(MenuEnum.todo)){
                            Menu m = bottomBar.getMenu();
                            m.add(0,MenuEnum.Todo_ItemId,0,menu.getText());
                            MenuItem item = m.findItem(MenuEnum.Todo_ItemId);
                            item.setIcon(MenuEnum.getResId(code));//设置菜单图片
                            list.add(TodoFragment.init().setList(menu.getChildList()));
                        }else if(code.equals(MenuEnum.count)){
                            Menu m = bottomBar.getMenu();
                            m.add(0,MenuEnum.Count_ItemId,1,menu.getText());
                            MenuItem item = m.findItem(MenuEnum.Count_ItemId);
                            item.setIcon(MenuEnum.getResId(code));//设置菜单图片
                            list.add(CountFragment.init().setList(menu.getChildList()));
                        }else if(code.equals(MenuEnum.waring)){
                            Menu m = bottomBar.getMenu();
                            m.add(0,MenuEnum.Waring_ItemId,2,menu.getText());
                            MenuItem item = m.findItem(MenuEnum.Waring_ItemId);
                            item.setIcon(MenuEnum.getResId(code));//设置菜单图片
                            list.add(WarningFragment.init().setList(menu.getChildList()));
                        }
                    }
                    Menu m = bottomBar.getMenu();
                    m.add(0,MenuEnum.Mine_ItemId,3,"我的");
                    MenuItem item = m.findItem(MenuEnum.Mine_ItemId);
                    item.setIcon(R.mipmap.menu_wd);//设置菜单图片
                    list.add(MineFragment.init());
                    BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomBar.getChildAt(0);
                    for(MeMenu menu:roles){
                        String code = menu.getCode();
                        if(code.equals(MenuEnum.todo)){
                            dclBadge = new BadgeView(context);
                            dclBadge.setTargetView(menuView.getChildAt(i++));
                            dclBadge.setBadgeCount(2);
                            dclBadge.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
                            dclBadge.setHideOnNull(true);
                        }else if(code.equals(MenuEnum.count)){
                            i++;
                        }else if(code.equals(MenuEnum.waring)){
                            yjBadge = new BadgeView(context);
                            yjBadge.setTargetView(menuView.getChildAt(i++));
                            yjBadge.setBadgeCount(5);
                            yjBadge.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
                            yjBadge.setHideOnNull(true);
                        }
                    }
                    adapter = new FragmentAdapter(getSupportFragmentManager(),list);
                    centerPanel.setAdapter(adapter);
                    bottomBar.setSelectedItemId(bottomBar.getMenu().getItem(0).getItemId());
                    bar.setTitle("待处理");
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        actHelper.doClickTwiceExit(centerPanel);
    }
}
