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
import allen.frame.entry.Type;
import allen.frame.tools.ChoiceTypeDialog;
import allen.frame.tools.DateUtils;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import allen.frame.widget.MaterialRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.allen.medical.R;
import cn.allen.medical.data.DataHelper;
import cn.allen.medical.data.HttpCallBack;
import cn.allen.medical.data.MeRespone;
import cn.allen.medical.entry.SysltjEntity;
import cn.allen.medical.utils.CommonAdapter;
import cn.allen.medical.utils.ViewHolder;

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
    private CommonAdapter<SysltjEntity.ItemsBean> adapter;
    private List<SysltjEntity.ItemsBean> list=new ArrayList<>();
    private List<SysltjEntity.ItemsBean> sublist=new ArrayList<>();
    private List<Type> ksList=new ArrayList<>();
    private boolean isRefresh = false;
    private boolean isStart=false;
    private boolean isFirstLoad=false;
    private int page = 0, pageSize = 20;
    private String ksID="",startDate="",endDate="";

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (isRefresh) {
                        list = sublist;
                        mater.finishRefresh();
                    } else {
                        if (page == 1) {
                            list = sublist;
                        } else {
                            list.addAll(sublist);
                        }
                        mater.finishRefreshLoadMore();
                    }
                    adapter.setDatas(list);
                    actHelper.setCanLoadMore(mater, pageSize, list);
                    break;
                case 1:
                    dismissProgressDialog();
                    mater.finishRefreshing();
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES,"");
                    break;
                case 2:
                    if (isFirstLoad) {
                        isFirstLoad=false;
                        if (!ksList.isEmpty()) {
                            ksID = ksList.get(0).getId();
                            lyKs.setText(ksList.get(0).getName());
                            loadData();
                        }else {
                            ksID="";
                            lyKs.setText("请选择");
                            loadData();
                        }
                    }else {
                        int len = ksList == null ? 0 : ksList.size();
                        if (len > 1) {
                            ChoiceTypeDialog dialog = new ChoiceTypeDialog(context, handler, 3);
                            dialog.showDialog("请选择科室", lyKs, ksList);
                        } else {
                            MsgUtils.showMDMessage(context, "科室数据获取失败,请重试!");
                        }
                    }
                    break;
                case 3:
                    ksID=ksList.get((int)msg.obj).getId();
                    isRefresh=true;
                    page=0;
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
                    loadData();
                    break;
                case 100:
                    if (isStart) {
                        startDate = (String) msg.obj;
                        isStart=false;
                        DateUtils.doSetDateDialog(context,handler);
                    }else {
                        endDate=(String) msg.obj;
                        lyDate.setText(startDate+"至"+endDate);
                        isRefresh=true;
                        page=0;
                        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
                        loadData();
                    }
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
        startDate=DateUtils.getStringByFormat(DateUtils.getFirstdayofThisMonth(),"yyyy-MM-dd");
        endDate=DateUtils.getCurrentDate("yyyy-MM-dd");
        lyDate.setText(startDate+"至"+endDate);
        initAdapter();
        isFirstLoad=true;
        loadKs();
//        loadData();
    }

    private void loadKs() {
        DataHelper.init().getKeShi(new HttpCallBack<List<Type>>() {
            @Override
            public void onSuccess(List<Type> respone) {
                ksList=respone;
                handler.sendEmptyMessage(2);
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

    private void initAdapter() {
        rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager
                .VERTICAL, false));
        adapter=new CommonAdapter<SysltjEntity.ItemsBean>(context,R.layout.count_item_layout) {
            @Override
            public void convert(ViewHolder holder, SysltjEntity.ItemsBean entity, int position) {
                holder.setText(R.id.count_item_name,entity.getPName());
                holder.setText(R.id.count_item_units,entity.getPUnit());
                holder.setText(R.id.count_item_spec,entity.getPSpec());
                holder.setText(R.id.count_item_quantity,entity.getQuantity()+"");
            }
        };
        rv.setAdapter(adapter);
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
        DataHelper.init().getLiyongCount(page++,ksID,startDate,endDate,new HttpCallBack<SysltjEntity>() {
            @Override
            public void onSuccess(SysltjEntity respone) {
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



    @OnClick({R.id.ly_ks, R.id.ly_date})
    public void onViewClicked(View view) {
        if(actHelper.isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.ly_ks:
                int len = ksList == null ? 0 : ksList.size();
                if (len > 1) {
                    ChoiceTypeDialog dialog = new ChoiceTypeDialog(context, handler, 3);
                    dialog.showDialog("请选择科室", lyKs, ksList);
                } else {
                    loadKs();
                }

                break;
            case R.id.ly_date:
                isStart=true;
                DateUtils.doSetDateDialog(context,handler);
                break;
        }
    }
}
