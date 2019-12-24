package cn.allen.medical.todo;

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
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import allen.frame.widget.MaterialRefreshLayout;
import allen.frame.widget.MaterialRefreshListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.allen.medical.R;
import cn.allen.medical.data.DataHelper;
import cn.allen.medical.data.HttpCallBack;
import cn.allen.medical.data.MeRespone;
import cn.allen.medical.entry.BillDetailsEntity;
import cn.allen.medical.entry.BillDifferentEntity;
import cn.allen.medical.entry.MenuEnum;
import cn.allen.medical.utils.CommonAdapter;
import cn.allen.medical.utils.ViewHolder;

public class BillDetailsActivity extends AllenBaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_number)
    AppCompatTextView tvNumber;
    @BindView(R.id.tv_date)
    AppCompatTextView tvDate;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    MaterialRefreshLayout refreshLayout;
    @BindView(R.id.btn_pass)
    AppCompatButton btnPass;
    @BindView(R.id.cy_recyclerview)
    RecyclerView cyRecyclerview;
    @BindView(R.id.btn_do_different)
    AppCompatButton btnDoDifferent;

    private Context mContext = this;
    private CommonAdapter<BillDetailsEntity.ItemsBean> adapter;
    private CommonAdapter<BillDifferentEntity.DiffRecordsBean> differentAdapter;
    private List<BillDetailsEntity.ItemsBean> list = new ArrayList<>();
    private List<BillDetailsEntity.ItemsBean> sublist = new ArrayList<>();
    private List<BillDifferentEntity.DiffRecordsBean> difflist = new ArrayList<>();
    private boolean isRefresh = false;
    private int page = 0, pageSize = 20;
    private String id,code;
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
                    break;
                case 2:
                    BillDifferentEntity differentEntity = (BillDifferentEntity) msg.obj;
                    tvNumber.setText(differentEntity.getCode());
                    tvDate.setText(differentEntity.getStartTime().replaceAll(" 00:00:00", "") +
                            "\n" + differentEntity.getEndTime().replaceAll(" 00:00:00", ""));

                    difflist = differentEntity.getDiffRecords();
                    differentAdapter.setDatas(difflist);
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
        return R.layout.activity_bill_details;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("ID");
        code = getIntent().getStringExtra("CODE");
        if (code.equals(MenuEnum.todo_zd_ks)) {
            btnDoDifferent.setVisibility(View.VISIBLE);
            actHelper.setToolbarTitleCenter(toolbar, "账单详情(科室)");
        } else if (code.equals(MenuEnum.todo_zd_sb)){
            btnDoDifferent.setVisibility(View.GONE);
            actHelper.setToolbarTitleCenter(toolbar, "账单详情(设备科)");
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        initAdapter();
        showProgressDialog("");
        loadData();
        loadDifference();
    }


    private void initAdapter() {
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .VERTICAL, false));
        adapter = new CommonAdapter<BillDetailsEntity.ItemsBean>(mContext, R.layout
                .bill_details_item_layout) {
            @Override
            public void convert(ViewHolder holder, BillDetailsEntity.ItemsBean entity, int
                    position) {
                holder.setText(R.id.tv_name, entity.getPName());
                holder.setText(R.id.tv_danwei, entity.getPUnit());
                holder.setText(R.id.tv_guige, entity.getPSpec());
                holder.setText(R.id.tv_count, entity.getQuantity() + "");
            }
        };
        recyclerview.setAdapter(adapter);

        cyRecyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .VERTICAL, false));
        differentAdapter = new CommonAdapter<BillDifferentEntity.DiffRecordsBean>(mContext, R.layout
                .bill_difference_item_layout) {
            @Override
            public void convert(ViewHolder holder, BillDifferentEntity.DiffRecordsBean entity, int
                    position) {
                holder.setText(R.id.tv_name, entity.getPName());
                holder.setText(R.id.tv_danwei, entity.getPUnit());
                holder.setText(R.id.tv_guige, entity.getPSpec());
                holder.setText(R.id.tv_difference_count, entity.getQuantity() + "");
            }
        };
        cyRecyclerview.setAdapter(differentAdapter);
    }


    @Override
    protected void addEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter.setOnItemClickListener(onItemClickListener);
        refreshLayout.setMaterialRefreshListener(refreshListener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode==RESULT_OK){
            loadDifference();
        }
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

    private CommonAdapter.OnItemClickListener onItemClickListener = new CommonAdapter
            .OnItemClickListener() {


        @Override
        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
            return false;
        }
    };

    @OnClick({R.id.btn_pass, R.id.btn_do_different})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pass:
                break;
            case R.id.btn_do_different:
                Intent intent = new Intent(mContext, DoDifferenceActivity.class);
                intent.putExtra("ID",id);
                startActivityForResult(intent,100);
                break;
        }
    }


    private void loadDifference() {
        showProgressDialog("");
        DataHelper.init().getBillDifferentDetails(id, new HttpCallBack<BillDifferentEntity>() {
            @Override
            public void onSuccess(BillDifferentEntity respone) {
                Message msg = new Message();
                msg.what = 2;
                msg.obj = respone;
                handler.sendMessage(msg);
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

    private void loadData() {
        DataHelper.init().getBillDetails(id, page++, "", new HttpCallBack<BillDetailsEntity>() {
            @Override
            public void onSuccess(BillDetailsEntity respone) {
                pageSize = respone.getPageSize();
                sublist = respone.getItems();
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
