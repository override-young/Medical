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
import java.util.Date;
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.tools.DateUtils;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import allen.frame.tools.StringUtils;
import allen.frame.widget.MaterialRefreshLayout;
import allen.frame.widget.MaterialRefreshListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.allen.medical.R;
import cn.allen.medical.data.DataHelper;
import cn.allen.medical.data.HttpCallBack;
import cn.allen.medical.data.MeRespone;
import cn.allen.medical.entry.CompanyWarningEntity;
import cn.allen.medical.entry.ConsumableStoreEntity;
import cn.allen.medical.entry.ContractWarnintEntity;
import cn.allen.medical.entry.KucunCountEntity;
import cn.allen.medical.utils.CommonAdapter;
import cn.allen.medical.utils.ViewHolder;

public class ConsumableStoreWarningActivity extends AllenBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    MaterialRefreshLayout refreshLayout;


    private Context mContext=this;
    private CommonAdapter<ConsumableStoreEntity.ItemsBean> adapter;
    private List<ConsumableStoreEntity.ItemsBean> list=new ArrayList<>();
    private List<ConsumableStoreEntity.ItemsBean> sublist=new ArrayList<>();
    private boolean isRefresh=false;
    private int page=0,pageSize=20;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    if (isRefresh) {
                        list = sublist;
                        refreshLayout.finishRefresh();
                    } else {
                        if (page == 1) {
                            list = sublist;
                        } else {
                            list.addAll(sublist);
                        }
                        refreshLayout.finishRefreshLoadMore();
                    }
                    adapter.setDatas(list);
                    actHelper.setCanLoadMore(refreshLayout,pageSize,list);
                    break;
                case 1:
                    dismissProgressDialog();
                    refreshLayout.finishRefreshing();
                    break;
                case -1:
                    dismissProgressDialog();
                    MsgUtils.showMDMessage(context, (String) msg.obj);
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
        return R.layout.activity_consumable_store;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        toolbar.setTitle("耗材库存效期警告");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        initAdapter();
        showProgressDialog("");
        loadData();
    }

    private void initAdapter() {
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .VERTICAL, false));
        adapter=new CommonAdapter<ConsumableStoreEntity.ItemsBean>(mContext,R.layout.haocai_cunxiaoqi_warning_item_layout) {
            @Override
            public void convert(ViewHolder holder, ConsumableStoreEntity.ItemsBean entity, int position) {
                holder.setText(R.id.tv_name,entity.getProductName());
                holder.setText(R.id.tv_pch,entity.getBatchNo());
                holder.setText(R.id.tv_guige,entity.getPSpec());
                holder.setText(R.id.tv_scrq,entity.getBornDate());
                holder.setText(R.id.tv_danwei,entity.getPUnit());
                holder.setText(R.id.tv_gqrq,entity.getExpireDate());
                Date currentDate = DateUtils.stringToDate(entity.getCurrentDate(), "yyyy-MM-dd HH:mm:ss");
                if (StringUtils.empty(entity.getExpireDate())){
                    holder.setText(R.id.tv_count, "--");
                }else {
                    Date expiredDate = DateUtils.stringToDate(entity.getExpireDate(), "yyyy-MM-dd HH:mm:ss");
                    long count = DateUtils.getDataDistance(currentDate, expiredDate);
                    holder.setText(R.id.tv_count, count + "");
                }
            }
        };
        recyclerview.setAdapter(adapter);
    }


    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshLayout.setMaterialRefreshListener(refreshListener);
    }

    private MaterialRefreshListener refreshListener = new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            isRefresh = true;
            page = 0;
            loadData();
        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            isRefresh = false;
            loadData();
        }
    };

    private void loadData() {

        DataHelper.init().getConsumableStoreWarning(page++, new HttpCallBack<ConsumableStoreEntity>() {
            @Override
            public void onSuccess(ConsumableStoreEntity respone) {
                sublist = respone.getItems();
                pageSize=respone.getPageSize();
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onTodo(MeRespone respone) {
                Message msg = new Message();
                msg.what = 1;
                msg.obj = respone.getMessage();
                handler.sendMessage(msg);

            }

            @Override
            public void tokenErro(MeRespone respone) {

            }

            @Override
            public void onFailed(MeRespone respone) {
                Logger.e("debug", respone.toString());
                Message msg = new Message();
                msg.what = -1;
                msg.obj = respone.getMessage();
                handler.sendMessage(msg);
            }
        });

    }

}
