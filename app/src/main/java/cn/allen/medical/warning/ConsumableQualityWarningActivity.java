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

import allen.frame.ActivityHelper;
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
import cn.allen.medical.entry.ConsumableQualityEntity;
import cn.allen.medical.entry.ContractWarnintEntity;
import cn.allen.medical.entry.MeMenu;
import cn.allen.medical.entry.MenuEnum;
import cn.allen.medical.utils.CommonAdapter;
import cn.allen.medical.utils.ViewHolder;

public class ConsumableQualityWarningActivity extends AllenBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    MaterialRefreshLayout refreshLayout;

    private Context mContext=this;
    private CommonAdapter<ConsumableQualityEntity.ItemsBean> adapter;
    private List<ConsumableQualityEntity.ItemsBean> list=new ArrayList<>();
    private List<ConsumableQualityEntity.ItemsBean> sublist=new ArrayList<>();
    private boolean isRefresh=false;
    private int page=0,pageSize=20;
    private MeMenu meMenu;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    dismissProgressDialog();
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES,"");
                    refreshLayout.finishRefreshing();
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
                    if (list.isEmpty()){
                        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL,getResources().getString(R.string.no_data),R.mipmap.no_data);
                    }
                    adapter.setDatas(list);
                    actHelper.setCanLoadMore(refreshLayout,pageSize,sublist);
                    break;
                case 1:

                    break;
                case -1:
                    dismissProgressDialog();
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL,getResources().getString(R.string.no_internet),R.mipmap.no_internet);
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
        return R.layout.activity_consumable_quality;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        meMenu= (MeMenu) getIntent().getSerializableExtra("Menu");
        actHelper.setToolbarTitleCenter(toolbar, meMenu.getText());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {

        initAdapter();
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
        if (meMenu.getCode().equals(MenuEnum.waring_hc)) {
            loadData();
        }else {
            loadGysData();
        }
    }

    private void initAdapter() {
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .VERTICAL, false));
        adapter=new CommonAdapter<ConsumableQualityEntity.ItemsBean>(mContext,R.layout.haocai_zizhi_warning_item_layout) {
            @Override
            public void convert(ViewHolder holder, ConsumableQualityEntity.ItemsBean entity, int position) {
                holder.setText(R.id.tv_name,entity.getProductName());
                holder.setText(R.id.tv_danwei,entity.getProductUnit());
                holder.setText(R.id.tv_guige,entity.getProductSpec());
                holder.setText(R.id.tv_dqsj,entity.getRecordCardDate().replaceAll(" 00:00:00",""));
                Date currentDate=DateUtils.stringToDate(entity.getCurrentDate(),"yyyy-MM-dd HH:mm:ss");
                if (StringUtils.empty(entity.getRecordCardDate())) {
                    holder.setText(R.id.tv_count, "--");
                }else {
                    Date expiredDate = DateUtils.stringToDate(entity.getRecordCardDate(), "yyyy-MM-dd HH:mm:ss");
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
            if (meMenu.getCode().equals(MenuEnum.waring_hc)) {
                loadData();
            }else {
                loadGysData();
            }
        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            isRefresh = false;
            if (meMenu.getCode().equals(MenuEnum.waring_hc)) {
                loadData();
            }else {
                loadGysData();
            }
        }
    };

    private void loadData() {
        DataHelper.init().getConsumableQualityWarning(page++, new HttpCallBack<ConsumableQualityEntity>() {
            @Override
            public void onSuccess(ConsumableQualityEntity respone) {
                sublist = respone.getItems();
                pageSize=respone.getPageSize();
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onTodo(MeRespone respone) {

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
    private void loadGysData() {
        DataHelper.init().getGysConsumableQualityWarning(page++, new HttpCallBack<ConsumableQualityEntity>() {
            @Override
            public void onSuccess(ConsumableQualityEntity respone) {
                sublist = respone.getItems();
                pageSize=respone.getPageSize();
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onTodo(MeRespone respone) {

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
