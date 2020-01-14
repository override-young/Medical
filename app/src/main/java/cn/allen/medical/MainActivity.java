package cn.allen.medical;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;

import java.util.ArrayList;
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.CaptureActivity;
import allen.frame.adapter.FragmentAdapter;
import allen.frame.tools.Intents;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import allen.frame.widget.ContrlScrollViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.allen.medical.count.CountFragment;
import cn.allen.medical.data.DataHelper;
import cn.allen.medical.data.HttpCallBack;
import cn.allen.medical.data.MeRespone;
import cn.allen.medical.entry.MeMenu;
import cn.allen.medical.entry.MenuEnum;
import cn.allen.medical.mine.MineFragment;
import cn.allen.medical.todo.TodoFragment;
import cn.allen.medical.utils.OnUpdateCountListener;
import cn.allen.medical.warning.WarningFragment;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity extends AllenBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar bar;
    @BindView(R.id.center_panel)
    ContrlScrollViewPager centerPanel;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavBar;
    private TextBadgeItem dclBadge,yjBadge;

    private FragmentAdapter adapter;
    private ArrayList<Fragment> list;

    public static final int REQUEST_CAMERA_PERMISSION = 1003;

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 11) {
                String res = data.getStringExtra(Intents.Scan.RESULT);
                Logger.e("debug", "Scan:" + res);
                if (StringUtils.notEmpty(res)) {
                    if (res.startsWith("http")) {
                        startActivity(new Intent(context, ScanLoginActivity.class).putExtra("url", res));
                    } else {
                        MsgUtils.showMDMessage(context, "识别错误,请重试!");
                    }
                } else {
                    MsgUtils.showMDMessage(context, "无法识别,请重试!");
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION: {
                if (verifyPermissions(grantResults)) {
                    startActivityForResult(new Intent(context, CaptureActivity.class), 11);
                }
                break;
            }
        }
    }

    public boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
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
        centerPanel.setOffscreenPageLimit(4);
        userAthur();
    }

    @Override
    protected void addEvent() {
        bar.setOnMenuItemClickListener(listener);
        bottomNavBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                setAppModule(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    private Toolbar.OnMenuItemClickListener listener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.item_sm:
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    } else {
                        startActivityForResult(new Intent(context, CaptureActivity.class), 11);
                    }
                    break;
            }
            return false;
        }
    };

    private void setAppModule(int index) {
        MeMenu menu = roles.get(index);
        bar.setTitle(menu.getText());
        if(menu.getCode().equals(MenuEnum.mine)){
            bar.setTitle("");
            if (bar.getMenu().size() > 0) {
                bar.getMenu().getItem(0).setVisible(true);
            }
        }else{
            if (bar.getMenu().size() > 0) {
                bar.getMenu().getItem(0).setVisible(false);
            }
        }
        centerPanel.setCurrentItem(index, false);
    }

    private List<MeMenu> roles;

    private void userAthur() {
        DataHelper.init().userAuth(new HttpCallBack<List<MeMenu>>() {
            @Override
            public void onSuccess(List<MeMenu> respone) {
                roles = respone;
                int len = roles==null?0:roles.size();
                if(len>0){
                    MeMenu menu = new MeMenu();
                    menu.setCode(MenuEnum.mine);
                    menu.setText("我的");
                    roles.add(menu);
                }else{
                    roles = new ArrayList<>();
                    MeMenu menu = new MeMenu();
                    menu.setCode(MenuEnum.mine);
                    menu.setText("我的");
                    roles.add(menu);
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
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    list = new ArrayList<>();
                    bottomNavBar.clearAll();
                    dclBadge = new TextBadgeItem().setBorderWidth(2).setBackgroundColor(Color.RED).setText("").hide();
                    yjBadge = new TextBadgeItem().setBorderWidth(2).setBackgroundColor(Color.RED).setText("").hide();
                    bottomNavBar.setMode(BottomNavigationBar.MODE_FIXED);
                    bottomNavBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
                    for (MeMenu menu : roles) {
                        String code = menu.getCode();
                        if (code.equals(MenuEnum.todo)) {//医疗端待处理
                            bottomNavBar.addItem(new BottomNavigationItem(MenuEnum.getResId(code),menu.getText())
                                    .setInActiveColorResource(R.color.text_gray_color)
                                    .setActiveColorResource(R.color.btn_normal_color)
                                    .setBadgeItem(dclBadge));
                            list.add(TodoFragment.init().setList(menu.getChildList()).setUpdateCount(new OnUpdateCountListener() {
                                @Override
                                public void count(int count) {
                                    dclBadge.setText(String.valueOf(count));
                                    if(count>0){
                                        dclBadge.show();
                                    }else{
                                        dclBadge.hide();
                                    }
                                }
                            }));
                        }else if (code.equals(MenuEnum.gys_todo)) {//供应商待处理
                            bottomNavBar.addItem(new BottomNavigationItem(MenuEnum.getResId(code),menu.getText())
                                    .setInActiveColorResource(R.color.text_gray_color)
                                    .setActiveColorResource(R.color.btn_normal_color)
                                    .setBadgeItem(dclBadge));
                            list.add(TodoFragment.init().setList(menu.getChildList()).setUpdateCount(new OnUpdateCountListener() {
                                @Override
                                public void count(int count) {
                                    dclBadge.setText(String.valueOf(count));
                                    if(count>0){
                                        dclBadge.show();
                                    }else{
                                        dclBadge.hide();
                                    }
                                }
                            }));
                        } else if (code.equals(MenuEnum.count) || code.equals(MenuEnum.gys_count)) {
                            bottomNavBar.addItem(new BottomNavigationItem(MenuEnum.getResId(code),menu.getText())
                                    .setInActiveColorResource(R.color.text_gray_color)
                                    .setActiveColorResource(R.color.btn_normal_color));
                            list.add(CountFragment.init().setList(menu.getChildList()));
                        } else if (code.equals(MenuEnum.waring) || code.equals(MenuEnum.gys_waring)) {
                            bottomNavBar.addItem(new BottomNavigationItem(MenuEnum.getResId(code),menu.getText())
                                    .setInActiveColorResource(R.color.text_gray_color)
                                    .setActiveColorResource(R.color.btn_normal_color)
                                    .setBadgeItem(yjBadge));
                            list.add(WarningFragment.init().setList(menu.getChildList()).setUpdateCount(new OnUpdateCountListener() {
                                @Override
                                public void count(int count) {
                                    yjBadge.setText(String.valueOf(count));
                                    if(count>0){
                                        yjBadge.show();
                                    }else{
                                        yjBadge.hide();
                                    }
                                }
                            }));
                        } else if(code.equals(MenuEnum.mine)){
                            bottomNavBar.addItem(new BottomNavigationItem(MenuEnum.getResId(code),menu.getText())
                                    .setInActiveColorResource(R.color.text_gray_color)
                                    .setActiveColorResource(R.color.btn_normal_color));
                            list.add(MineFragment.init());
                        }
                    }
                    adapter = new FragmentAdapter(getSupportFragmentManager(), list);
                    centerPanel.setAdapter(adapter);
                    bottomNavBar.setFirstSelectedPosition(0);
                    bottomNavBar.initialise();
//                    bottomBar.setSelectedItemId(bottomBar.getMenu().getItem(0).getItemId());
//                    bar.setTitle("待处理");
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        actHelper.doClickTwiceExit(centerPanel);
    }

}
