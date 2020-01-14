package cn.allen.medical.count;

import android.annotation.SuppressLint;
import android.content.Context;
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
import allen.frame.entry.Type;
import allen.frame.tools.ChoiceTypeDialog;
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
import cn.allen.medical.entry.KucunCountEntity;
import cn.allen.medical.entry.MeMenu;
import cn.allen.medical.entry.MenuEnum;
import cn.allen.medical.entry.SelectSumChartEntity;
import cn.allen.medical.utils.CommonAdapter;
import cn.allen.medical.utils.ViewHolder;

public class KucunCountActivity extends AllenBaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    MaterialRefreshLayout refreshLayout;
    @BindView(R.id.tv_hospital)
    AppCompatTextView tvHospital;

    private Context mContext = this;
    private CommonAdapter<KucunCountEntity.ItemsBean> adapter;
    private List<KucunCountEntity.ItemsBean> list = new ArrayList<>();
    private List<KucunCountEntity.ItemsBean> sublist = new ArrayList<>();
    private boolean isRefresh = false;
    private int page = 0, pageSize = 20;
    private MeMenu meMenu;

    private int isOnlyHospital = 1;
    private List<Type> hospitalList;
    private String hospitalId = "";
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
                case 2:
                    if (!hospitalList.isEmpty()) {
                        tvHospital.setText(hospitalList.get(0).getName());
                        hospitalId = hospitalList.get(0).getId();
                        loadDataOfSup();
                    }

                    break;
                case 3:
                    hospitalId = hospitalList.get((int) msg.obj).getId();
                    isRefresh = true;
                    page = 0;
                    loadDataOfSup();
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
        return R.layout.activity_kucun_count;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        meMenu = (MeMenu) getIntent().getSerializableExtra("Menu");
        actHelper.setToolbarTitleCenter(toolbar, meMenu.getText());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        initAdapter();
        if (meMenu.getCode().equals(MenuEnum.count_kcsl)) {
            tvHospital.setVisibility(View.GONE);
            loadData();
        } else {
            tvHospital.setVisibility(View.VISIBLE);
            loadHzdw();
        }
    }

    private void initAdapter() {
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .VERTICAL, false));
        adapter = new CommonAdapter<KucunCountEntity.ItemsBean>(mContext, R.layout
                .kucun_count_item_layout) {
            @Override
            public void convert(ViewHolder holder, KucunCountEntity.ItemsBean entity, int
                    position) {
                holder.setText(R.id.tv_name, entity.getPName());
                holder.setText(R.id.tv_danwei, entity.getPUnit());
                holder.setText(R.id.tv_guige, entity.getPSpec());
                if (meMenu.getCode().equals(MenuEnum.count_kcsl)) {
                    holder.setText(R.id.tv_count, entity.getQuantity() + "");
                }else {
                    holder.setText(R.id.tv_count, entity.getTotalStock() + "");
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
        showProgressDialog("");
        DataHelper.init().getKucunCount(page++, new HttpCallBack<KucunCountEntity>() {
            @Override
            public void onSuccess(KucunCountEntity respone) {
                sublist = respone.getItems();
                pageSize = respone.getPageSize();
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

    @OnClick(R.id.tv_hospital)
    public void onViewClicked() {
        int len = hospitalList == null ? 0 : hospitalList.size();
        if (len > 1) {
            ChoiceTypeDialog dialog = new ChoiceTypeDialog(context, handler, 3);
            dialog.showDialog("请选择医院", tvHospital, hospitalList);
        } else {
            loadHzdw();
        }
    }

    private void loadHzdw() {
        showProgressDialog("");
        DataHelper.init().getHospitalList(isOnlyHospital, new HttpCallBack<List<Type>>() {
            @Override
            public void onSuccess(List<Type> respone) {
                hospitalList = respone;
                handler.sendEmptyMessage(2);
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
        });
    }

    private void loadDataOfSup() {
        showProgressDialog("");
        DataHelper.init().getGysKucunCount(hospitalId, page, new HttpCallBack<KucunCountEntity>() {

            @Override
            public void onSuccess(KucunCountEntity respone) {
                sublist = respone.getItems();
                pageSize = respone.getPageSize();
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
