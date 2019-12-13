package cn.allen.medical.count;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import allen.frame.ActivityHelper;
import allen.frame.AllenBaseActivity;
import allen.frame.widget.MaterialRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.allen.medical.R;
import cn.allen.medical.adapter.TjAdapter;
import cn.allen.medical.entry.SysltjEntity;

public class CountLyActivity extends AllenBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar bar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.mater)
    MaterialRefreshLayout mater;
    @BindView(R.id.ly_ks)
    AppCompatTextView lyKs;
    @BindView(R.id.ly_date)
    AppCompatTextView lyDate;
    private TjAdapter adapter;
    private List<SysltjEntity> list;

    @Override
    protected boolean isStatusBarColorWhite() {
        return true;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.count_ly_layout;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        actHelper.setToolbarTitleCenter(bar, "领用数量统计");
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START, "");
        adapter = new TjAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        loadData();
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

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                list = new ArrayList<>();
                list.add(new SysltjEntity());
                list.add(new SysltjEntity());
                list.add(new SysltjEntity());
                list.add(new SysltjEntity());
                list.add(new SysltjEntity());
                list.add(new SysltjEntity());
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES, "");
                    adapter.setData(list);
                    break;
            }
        }
    };

    @OnClick({R.id.ly_ks, R.id.ly_date})
    public void onViewClicked(View view) {
        if(actHelper.isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.ly_ks:
                break;
            case R.id.ly_date:
                break;
        }
    }
}
