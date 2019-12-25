package cn.allen.medical.todo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.allen.medical.R;
import cn.allen.medical.data.DataHelper;
import cn.allen.medical.data.HttpCallBack;
import cn.allen.medical.data.MeRespone;
import cn.allen.medical.entry.BillDetailsEntity;
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
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;

    private Context mContext=this;
    private CommonAdapter<PriceDetailsEntity> adapter;
    private List<PriceDetailsEntity> list=new ArrayList<>();
    private String id;
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    PriceDetailsEntity entity= (PriceDetailsEntity) msg.obj;
                    tvGys.setText(entity.getOrgName());
                    tvBgrq.setText(entity.getCreateTime());
                    list.add(entity);
                    adapter.setDatas(list);
                    break;
                case 1:
                    dismissProgressDialog();
                    break;
                case 2:
                    dismissProgressDialog();
                    MsgUtils.showLongToast(mContext,"成功！");
                    setResult(RESULT_OK);
                    finish();
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
        id = getIntent().getStringExtra("ID");
        initAdapter();
        loadData();
    }

    private void loadData() {
        showProgressDialog("");
        DataHelper.init().getPriceDetails(id, new HttpCallBack<PriceDetailsEntity>() {
            @Override
            public void onSuccess(PriceDetailsEntity respone) {
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
        adapter=new CommonAdapter<PriceDetailsEntity>(mContext,R.layout.price_details_item_layout) {
            @Override
            public void convert(ViewHolder holder, PriceDetailsEntity entity, int position) {
                holder.setText(R.id.tv_name,entity.getPName());
                holder.setText(R.id.tv_after,entity.getPrice()+"");
                holder.setText(R.id.tv_guige,entity.getPSpec());
                holder.setText(R.id.tv_before,entity.getPrePrice()+"");
                holder.setText(R.id.tv_danwei,entity.getPUnit());
                holder.setText(R.id.tv_date,entity.getPriceStartTime());
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
        if (actHelper.isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.tv_gys:
                break;
            case R.id.tv_bgrq:
                break;
            case R.id.btn_submit:
                MsgUtils.showMDMessage(mContext, "确定通过审核?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        passVer();
                    }
                });
                break;
        }
    }

    private void passVer(){
        showProgressDialog("");
        DataHelper.init().getPriceExamine(id,true, "", new HttpCallBack() {
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
