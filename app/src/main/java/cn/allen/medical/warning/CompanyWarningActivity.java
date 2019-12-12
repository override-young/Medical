package cn.allen.medical.warning;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.widget.MaterialRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.allen.medical.R;
import cn.allen.medical.entry.CompanyWarningEntity;
import cn.allen.medical.entry.PriceDetailsEntity;
import cn.allen.medical.utils.CommonAdapter;
import cn.allen.medical.utils.ViewHolder;

public class CompanyWarningActivity extends AllenBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    MaterialRefreshLayout refreshLayout;

    private Context mContext=this;
    private CommonAdapter<CompanyWarningEntity> adapter;
    private List<CompanyWarningEntity> list=new ArrayList<>();
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:

                    break;
            }
        }
    };



    @Override
    protected boolean isStatusBarColorWhite() {
        return true;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_company_warning;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        toolbar.setTitle("企业资质警告");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {

        for (int i = 0; i < 3; i++) {
            list.add(new CompanyWarningEntity());
        }
        initAdapter();
    }

    private void initAdapter() {
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .VERTICAL, false));
        adapter=new CommonAdapter<CompanyWarningEntity>(mContext,R.layout.company_warning_item_layout) {
            @Override
            public void convert(ViewHolder holder, CompanyWarningEntity entity, int position) {

            }
        };
        recyclerview.setAdapter(adapter);
        adapter.setDatas(list);
    }

    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
