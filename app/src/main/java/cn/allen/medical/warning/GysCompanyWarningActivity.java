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
import cn.allen.medical.entry.GysCompanyWarningEntity;
import cn.allen.medical.entry.MeMenu;
import cn.allen.medical.utils.CommonAdapter;
import cn.allen.medical.utils.ViewHolder;

public class GysCompanyWarningActivity extends AllenBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    MaterialRefreshLayout refreshLayout;

    private Context mContext = this;
    private CommonAdapter<GysCompanyWarningEntity.CertListBean> adapter;
    private List<GysCompanyWarningEntity.CertListBean> list = new ArrayList<>();
    private List<GysCompanyWarningEntity.CertListBean> sublist = new ArrayList<>();
    private boolean isRefresh = true;
    private int page = 0, pageSize = 20;
    private MeMenu meMenu;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
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
                    actHelper.setCanLoadMore(refreshLayout, pageSize, list);
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
        return R.layout.activity_company_warning;
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
        showProgressDialog("");
        loadData();
    }


    private void initAdapter() {
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .VERTICAL, false));
        adapter = new CommonAdapter<GysCompanyWarningEntity.CertListBean>(mContext, R.layout
                .company_warning_details_item_layout) {
            @Override
            public void convert(ViewHolder holder, GysCompanyWarningEntity.CertListBean entity, int
                    position) {
                holder.setText(R.id.tv_permit_name,entity.getCertName());
                holder.setText(R.id.tv_due_time, entity.getExpireDate().replaceAll(" 00:00:00",""));
                Date currentDate = DateUtils.stringToDate(entity.getCurrentDate(), "yyyy-MM-dd " +
                        "HH:mm:ss");
                if (StringUtils.empty(entity.getExpireDate())) {
                    holder.setText(R.id.tv_remaining_time, "--");
                } else {
                    Date ExpiredDate = DateUtils.stringToDate(entity
                            .getExpireDate(), "yyyy-MM-dd HH:mm:ss");
                    long bussinessCount = DateUtils.getDataDistance(currentDate,
                            ExpiredDate);
                    holder.setText(R.id.tv_remaining_time, bussinessCount + "");
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
        DataHelper.init().getGysCompanyWarning(new HttpCallBack<GysCompanyWarningEntity>() {
            @Override
            public void onSuccess(GysCompanyWarningEntity respone) {
                sublist = respone.getCertList();
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
