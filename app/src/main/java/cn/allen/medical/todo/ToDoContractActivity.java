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

import allen.frame.ActivityHelper;
import allen.frame.AllenBaseActivity;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import allen.frame.widget.MaterialRefreshLayout;
import allen.frame.widget.MaterialRefreshListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.allen.medical.MainActivity;
import cn.allen.medical.R;
import cn.allen.medical.data.DataHelper;
import cn.allen.medical.data.HttpCallBack;
import cn.allen.medical.data.MeRespone;
import cn.allen.medical.entry.KucunCountEntity;
import cn.allen.medical.entry.MeMenu;
import cn.allen.medical.entry.ToDoContractEntity;
import cn.allen.medical.entry.User;
import cn.allen.medical.utils.CommonAdapter;
import cn.allen.medical.utils.ViewHolder;

public class ToDoContractActivity extends AllenBaseActivity {
    private static String TAG = "ToDoContractActivity";


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    MaterialRefreshLayout refreshLayout;

    private Context mContext = this;
    private CommonAdapter<ToDoContractEntity.ItemsBean> adapter;
    private List<ToDoContractEntity.ItemsBean> list = new ArrayList<>();
    private List<ToDoContractEntity.ItemsBean> sublist = new ArrayList<>();
    private boolean isRefresh = false;
    private int page = 0,pageSize=20;
    private MeMenu meMenu;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
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
        return R.layout.activity_to_do_contract;
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
        loadData();
    }


    private void initAdapter() {
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .VERTICAL, false));
        adapter = new CommonAdapter<ToDoContractEntity.ItemsBean>(mContext, R.layout
                .to_do_contract_item_layout) {
            @Override
            public void convert(ViewHolder holder, ToDoContractEntity.ItemsBean entity, int
                    position) {
                holder.setText(R.id.tv_contract_num, entity.getContractNo());
                holder.setText(R.id.tv_gys, entity.getPartyAName());
                int state = entity.getStatus();
                if (state == 5) {
                    holder.setText(R.id.tv_state, "待审核");
                } else if (state == 10) {
                    holder.setText(R.id.tv_state, "已撤销");
                } else if (state == 15) {
                    holder.setText(R.id.tv_state, "正常");
                } else if (state == 20) {
                    holder.setText(R.id.tv_state, "未生效");
                } else if (state == 30) {
                    holder.setText(R.id.tv_state, "已过期");
                } else if (state == 40) {
                    holder.setText(R.id.tv_state, "驳回");
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
        adapter.setOnItemClickListener(onItemClickListener);
        refreshLayout.setMaterialRefreshListener(refreshListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode==RESULT_OK){
            isRefresh = true;
            page = 0;
            actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
            loadData();
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
            Intent intent = new Intent(mContext, ContractDetailsActivity.class);
            intent.putExtra("ID", list.get(position).getId());
            startActivityForResult(intent,100);
        }

        @Override
        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
            return false;
        }
    };

    private void loadData() {
        DataHelper.init().getTodoContract(page++, new HttpCallBack<ToDoContractEntity>() {
            @Override
            public void onSuccess(ToDoContractEntity respone) {
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
