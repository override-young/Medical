package cn.allen.medical;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
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
import butterknife.OnClick;
import cn.allen.medical.entry.PriceDetailsEntity;
import cn.allen.medical.utils.CommonAdapter;
import cn.allen.medical.utils.ViewHolder;

public class PriceDetailsActivity extends AllenBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_gys)
    AppCompatTextView tvGys;
    @BindView(R.id.tv_bgrq)
    AppCompatTextView tvBgrq;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    MaterialRefreshLayout refreshLayout;
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;

    private Context mContext=this;
    private CommonAdapter<PriceDetailsEntity> adapter;
    private List<PriceDetailsEntity> list=new ArrayList<>();
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
        return R.layout.activity_price_details;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        toolbar.setTitle("价格详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {

        for (int i = 0; i < 3; i++) {
            list.add(new PriceDetailsEntity());
        }
        initAdapter();
    }

    private void initAdapter() {
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .VERTICAL, false));
        adapter=new CommonAdapter<PriceDetailsEntity>(mContext,R.layout.price_details_item_layout) {
            @Override
            public void convert(ViewHolder holder, PriceDetailsEntity entity, int position) {

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

    @OnClick({R.id.tv_gys, R.id.tv_bgrq, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_gys:
                break;
            case R.id.tv_bgrq:
                break;
            case R.id.btn_submit:
                startActivity(new Intent(mContext,ContractDetailsActivity.class));
                break;
        }
    }
}
