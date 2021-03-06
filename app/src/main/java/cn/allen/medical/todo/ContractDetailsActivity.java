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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import allen.frame.ActivityHelper;
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
import cn.allen.medical.entry.ContractDetailsEntity;
import cn.allen.medical.utils.CommonAdapter;
import cn.allen.medical.utils.ViewHolder;
import cn.allen.medical.utils.WarningDialog;

public class ContractDetailsActivity extends AllenBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_contract_num)
    AppCompatTextView tvContractNum;
    @BindView(R.id.tv_unit)
    AppCompatTextView tvUnit;
    @BindView(R.id.tv_cjr)
    AppCompatTextView tvCjr;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;
    @BindView(R.id.tv_start_date)
    AppCompatTextView tvStartDate;
    @BindView(R.id.tv_end_date)
    AppCompatTextView tvEndDate;
    @BindView(R.id.tv_state)
    AppCompatTextView tvState;

    private Context mContext = this;
    private CommonAdapter<ContractDetailsEntity.SupplierProductListBean> adapter;
    private List<ContractDetailsEntity.SupplierProductListBean> list = new ArrayList<>();
    private List<String> picList=new ArrayList<>();
    private String id = "";
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES, "");
                    dismissProgressDialog();
                    ContractDetailsEntity contractDetailsEntity = (ContractDetailsEntity) msg.obj;
                    if (contractDetailsEntity!=null) {
                        tvContractNum.setText(contractDetailsEntity.getContractNo());
                        tvUnit.setText(contractDetailsEntity.getPartyAName());
                        tvStartDate.setText(contractDetailsEntity.getContractStartTime().replaceAll(" 00:00:00", ""));

                        tvEndDate.setText(contractDetailsEntity.getContractStopTime().replaceAll
                                (" 00:00:00", ""));
                        int state = contractDetailsEntity.getStatus();
                        if (state == 5) {
                            tvState.setText("待审核");
                        } else if (state == 10) {
                            tvState.setText("已撤销");
                        } else if (state == 15) {
                            tvState.setText("正常");
                        } else if (state == 20) {
                            tvState.setText("未生效");
                        } else if (state == 30) {
                            tvState.setText("已过期");
                        } else if (state == 40) {
                            tvState.setText("驳回");
                        }
                        picList = contractDetailsEntity.getPictures();
                        list = contractDetailsEntity.getSupplierProductList();
                        adapter.setDatas(list);
                    }else {
                        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL, getResources()
                                .getString(R.string.no_data), R.mipmap.no_data);
                    }
                    break;
                case 1:

                    break;
                case 2:
                    dismissProgressDialog();
//                    MsgUtils.showLongToast(mContext,"成功！");
                    setResult(RESULT_OK);
                    finish();
                    break;
                case 3:
                    passVer();
                    break;
                case -1:
                    dismissProgressDialog();
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL, getResources()
                            .getString(R.string.no_internet), R.mipmap.no_internet);
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
        return R.layout.activity_contract_details;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.inflateMenu(R.menu.menu_contract);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actHelper.isFastClick()){
            return super.onOptionsItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.item_contract:
                if (picList!=null&&!picList.isEmpty()){
                    Intent intent=new Intent(mContext,PicsActivity.class);
                    intent.putStringArrayListExtra("Urls", (ArrayList<String>) picList);
                    startActivity(intent);
                }else {
                    MsgUtils.showMDMessage(mContext,"当前合同没有对应图片!");
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        actHelper.setToolbarTitleCenter(toolbar,"合同详情");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        id = getIntent().getStringExtra("ID");
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
        loadData();
        initAdapter();
    }

    private void loadData() {
        DataHelper.init().getContractDetails(id, new HttpCallBack<ContractDetailsEntity>() {
            @Override
            public void onSuccess(ContractDetailsEntity respone) {
                Message msg = new Message();
                msg.what = 0;
                msg.obj = respone;
                handler.sendMessage(msg);
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
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .VERTICAL, false));
        adapter = new CommonAdapter<ContractDetailsEntity.SupplierProductListBean>(mContext, R
                .layout.contract_details_item_layout) {
            @Override
            public void convert(ViewHolder holder, ContractDetailsEntity.SupplierProductListBean
                    entity, int position) {
                holder.setText(R.id.tv_name, entity.getPackageName());
                holder.setText(R.id.tv_danwei, entity.getUnit());
                holder.setText(R.id.tv_guige, entity.getSpec());

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

    @OnClick({R.id.tv_contract_num, R.id.tv_unit, R.id.tv_cjr, R.id.btn_submit})
    public void onViewClicked(View view) {
        if (actHelper.isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.tv_contract_num:
                break;
            case R.id.tv_unit:
                break;
            case R.id.tv_cjr:
                break;
            case R.id.btn_submit:
                WarningDialog warningDialog=new WarningDialog(mContext,handler,"温馨提示","确定通过审核吗?",3);
                warningDialog.show();

                break;
        }
    }

    private void passVer(){
        showProgressDialog("");
        DataHelper.init().getContractExamine(id, true, "", new HttpCallBack() {
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
