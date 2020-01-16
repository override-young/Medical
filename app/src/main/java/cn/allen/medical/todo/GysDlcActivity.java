package cn.allen.medical.todo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import allen.frame.AllenBaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.allen.medical.R;
import cn.allen.medical.entry.MeMenu;

public class GysDlcActivity extends AllenBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar bar;
    @BindView(R.id.container)
    FrameLayout container;
    private MeMenu menu;

    @Override
    protected boolean isStatusBarColorWhite() {
        return true;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_apply;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        menu = (MeMenu) getIntent().getSerializableExtra("Menu");
        actHelper.setToolbarTitleCenter(bar,menu.getText());
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, GTodoFragment.init())
                    .commit();
        }

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

}
