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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.allen.medical.R;
import cn.allen.medical.data.DataHelper;
import cn.allen.medical.data.HttpCallBack;
import cn.allen.medical.data.MeRespone;
import cn.allen.medical.entry.ContractDetailsEntity;
import cn.allen.medical.entry.GysToDoDetailsEntity;
import cn.allen.medical.utils.CommonAdapter;
import cn.allen.medical.utils.ViewHolder;
import cn.allen.medical.utils.WarningDialog;

public class GysToDoDetailsActivity extends AllenBaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_order_num)
    AppCompatTextView tvOrderNum;
    @BindView(R.id.tv_unit)
    AppCompatTextView tvUnit;
    @BindView(R.id.tv_state)
    AppCompatTextView tvState;
    @BindView(R.id.tv_shr)
    AppCompatTextView tvShr;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;
    private Context mContext = this;
    private CommonAdapter<GysToDoDetailsEntity.DetailsListBean> adapter;
    private List<GysToDoDetailsEntity.DetailsListBean> list = new ArrayList<>();
    private String id = "";
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    GysToDoDetailsEntity gysToDoDetailsEntity = (GysToDoDetailsEntity) msg.obj;
                    tvUnit.setText(gysToDoDetailsEntity.getOrgName());
                    tvOrderNum.setText(gysToDoDetailsEntity.getOrderNo());
                    tvShr.setText(gysToDoDetailsEntity.getRecipientName());
//                    int state = gysToDoDetailsEntity.getStatus();
//                    if (state == 5) {
//                        tvState.setText("待审核");
//                    } else if (state == 10) {
//                        tvState.setText("已撤销");
//                    } else if (state == 15) {
//                        tvState.setText("正常");
//                    } else if (state == 20) {
//                        tvState.setText("未生效");
//                    } else if (state == 30) {
//                        tvState.setText("已过期");
//                    } else if (state == 40) {
//                        tvState.setText("驳回");
//                    }
                    list = gysToDoDetailsEntity.getDetailsList();
                    adapter.setDatas(list);
                    break;
                case 1:
                    dismissProgressDialog();
                    break;
                case 2:
                    dismissProgressDialog();
                    MsgUtils.showLongToast(mContext, "成功！");
                    setResult(RESULT_OK);
                    finish();
                    break;
                case 3:
                    passVer();
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
        return R.layout.activity_gys_to_do;
    }


    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        actHelper.setToolbarTitleCenter(toolbar, "订单详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        id = getIntent().getStringExtra("ID");
        loadData();
        initAdapter();
    }

    private void loadData() {
        showProgressDialog("");
        DataHelper.init().getGysToDoDetails(id, new HttpCallBack<GysToDoDetailsEntity>() {
            @Override
            public void onSuccess(GysToDoDetailsEntity respone) {
                Message msg = new Message();
                msg.what = 0;
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

    private void initAdapter() {
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .VERTICAL, false));
        adapter = new CommonAdapter<GysToDoDetailsEntity.DetailsListBean>(mContext, R
                .layout.gys_to_do_details_item_layout) {
            @Override
            public void convert(ViewHolder holder, GysToDoDetailsEntity.DetailsListBean
                    entity, int position) {
                holder.setText(R.id.tv_name, entity.getPName());
                holder.setText(R.id.tv_danwei, entity.getPUnit());
                holder.setText(R.id.tv_guige, entity.getPSpec());
                holder.setText(R.id.tv_count, entity.getQuantity() + "");

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
    }

    @OnClick({R.id.btn_submit})
    public void onViewClicked(View view) {
        if (actHelper.isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_submit:
                WarningDialog warningDialog = new WarningDialog(mContext, handler, "温馨提示",
                        "确认该订单吗?", 3);
                warningDialog.show();

                break;
        }
    }

    private void passVer() {
        showProgressDialog("");
        DataHelper.init().getGysToDoExamine(id, new HttpCallBack() {
            @Override
            public void onSuccess(Object respone) {

            }

            @Override
            public void onTodo(MeRespone respone) {
                Message msg = new Message();
                msg.what = 2;
                msg.obj = respone.getMessage();
                handler.sendMessage(msg);
            }

            @Override
            public void tokenErro(MeRespone respone) {
                Message msg = new Message();
                msg.what = -1;
                msg.obj = respone.getMessage();
                handler.sendMessage(msg);
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

}
