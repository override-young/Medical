package cn.allen.medical.todo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import allen.frame.tools.MsgUtils;
import allen.frame.widget.MaterialRefreshLayout;
import allen.frame.widget.MaterialRefreshListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.allen.medical.R;
import cn.allen.medical.data.DataHelper;
import cn.allen.medical.data.HttpCallBack;
import cn.allen.medical.data.MeRespone;
import cn.allen.medical.entry.MenuEnum;
import cn.allen.medical.entry.ToDoBillEntity;
import cn.allen.medical.entry.ToDoContractEntity;
import cn.allen.medical.utils.CommonAdapter;
import cn.allen.medical.utils.ViewHolder;

public class ToDoBillActivity extends AllenBaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    MaterialRefreshLayout refreshLayout;

    private Context mContext = this;
    private CommonAdapter<ToDoBillEntity.ItemsBean> adapter;
    private List<ToDoBillEntity.ItemsBean> list = new ArrayList<>();
    private List<ToDoBillEntity.ItemsBean> sublist = new ArrayList<>();
    private boolean isRefresh=false;
    private int page = 0,pageSize=20;
    private String code;
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
        return R.layout.activity_to_do_bill;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        actHelper.setToolbarTitleCenter(toolbar, "待确认账单");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        code = getIntent().getStringExtra("CODE");
        showProgressDialog("");
        if (code.equals(MenuEnum.todo_zd_ks)) {
            loadDataByDep();
        } else if (code.equals(MenuEnum.todo_zd_sb)){
            loadDataByDevice();
        }
        initAdapter();
    }


    private void loadDataByDevice() {
        DataHelper.init().getTodoBillByDevice(page++, httpCallBack);
    }

    private void loadDataByDep() {
        DataHelper.init().getTodoBillByDep(page++, httpCallBack);
    }

    private void initAdapter() {
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .VERTICAL, false));
        adapter = new CommonAdapter<ToDoBillEntity.ItemsBean>(mContext, R.layout
                .to_do_bill_item_layout) {
            @Override
            public void convert(ViewHolder holder, ToDoBillEntity.ItemsBean entity, int position) {
                holder.setText(R.id.tv_bill_num, entity.getCode());
                holder.setText(R.id.tv_bill_date, entity.getStartTime().replaceAll(" 00:00:00",
                        "") + "至" + entity.getEndTime().replaceAll(" 00:00:00", ""));
            }
        };
        recyclerview.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode==RESULT_OK){
            page=0;
            isRefresh=true;
            if (code.equals(MenuEnum.todo_zd_ks)) {
                loadDataByDep();
            } else if (code.equals(MenuEnum.todo_zd_sb)){
                loadDataByDevice();
            }
        }
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

    private MaterialRefreshListener refreshListener=new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            page=0;
            isRefresh=true;
            if (code.equals(MenuEnum.todo_zd_ks)) {
                loadDataByDep();
            } else if (code.equals(MenuEnum.todo_zd_sb)){
                loadDataByDevice();
            }
        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            isRefresh=false;
            if (code.equals(MenuEnum.todo_zd_ks)) {
                loadDataByDep();
            } else if (code.equals(MenuEnum.todo_zd_sb)){
                loadDataByDevice();
            }
        }
    };

    private CommonAdapter.OnItemClickListener onItemClickListener = new CommonAdapter
            .OnItemClickListener() {


        @Override
        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
            Intent intent = new Intent(mContext, BillDetailsActivity.class);
            intent.putExtra("ID",list.get(position).getId());
            intent.putExtra("CODE",code);
            startActivityForResult(intent,100);
        }

        @Override
        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
            return false;
        }
    };

    private HttpCallBack<ToDoBillEntity> httpCallBack = new HttpCallBack<ToDoBillEntity>() {
        @Override
        public void onSuccess(ToDoBillEntity respone) {
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
            Message msg = new Message();
            msg.what = -1;
            msg.obj = respone.getMessage();
            handler.sendMessage(msg);
        }
    };


}
