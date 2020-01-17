package cn.allen.medical.count;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import allen.frame.ActivityHelper;
import allen.frame.AllenBaseActivity;
import allen.frame.entry.Type;
import allen.frame.tools.ChoiceTypeDialog;
import allen.frame.tools.DateUtils;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import allen.frame.widget.CustomDatePicker;
import allen.frame.widget.MaterialRefreshLayout;
import allen.frame.widget.MaterialRefreshListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.allen.medical.R;
import cn.allen.medical.data.DataHelper;
import cn.allen.medical.data.HttpCallBack;
import cn.allen.medical.data.MeRespone;
import cn.allen.medical.entry.MeMenu;
import cn.allen.medical.entry.MenuEnum;
import cn.allen.medical.entry.SysltjEntity;
import cn.allen.medical.utils.CommonAdapter;
import cn.allen.medical.utils.CommonUtils;
import cn.allen.medical.utils.PopupWindow.CommonPopupWindow;
import cn.allen.medical.utils.PopupWindow.CommonUtil;
import cn.allen.medical.utils.ViewHolder;

public class CountLyActivity extends AllenBaseActivity implements CommonPopupWindow.ViewInterface {
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
    @BindView(R.id.tv_hospital)
    AppCompatTextView tvHospital;
    private CommonAdapter<SysltjEntity.ItemsBean> adapter;
    private List<SysltjEntity.ItemsBean> list = new ArrayList<>();
    private List<SysltjEntity.ItemsBean> sublist = new ArrayList<>();
    private List<Type> ksList = new ArrayList<>();
    private boolean isRefresh = false;
    private boolean isFirstLoad = false;
    private int page = 0, pageSize = 20;
    private String ksID = "", startDate = "", endDate = "";
    private String preStartDate = "", preEndDate = "";
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
                    dismissProgressDialog();
                    mater.finishRefreshing();
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES, "");
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
                    if (list.isEmpty()){
                        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL,getResources().getString(R.string.no_data),R.mipmap.no_data);
                    }
                    adapter.setDatas(list);
                    actHelper.setCanLoadMore(mater, pageSize, sublist);
                    break;
                case 1:

                    break;
                case 2:
                    dismissProgressDialog();
                    mater.finishRefreshing();
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES, "");
                    if (isFirstLoad) {
                        isFirstLoad = false;
                        if (!ksList.isEmpty()) {
                            ksID = ksList.get(0).getId();
                            lyKs.setText(ksList.get(0).getName());
                            actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
                            loadData();
                        } else {
                            ksID = "";
                            lyKs.setText("");
                            actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL,getResources().getString(R.string.no_data),R.mipmap.no_data);
                        }
                    } else {
                        int len = ksList == null ? 0 : ksList.size();
                        if (len > 0) {
                            ChoiceTypeDialog dialog = new ChoiceTypeDialog(context, handler, 3);
                            dialog.showDialog("请选择科室", lyKs, ksList);
                        } else {
                            actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL,getResources().getString(R.string.no_internet),R.mipmap.no_internet);
                            MsgUtils.showMDMessage(context, "科室数据获取失败,请重试!");
                        }
                    }
                    break;
                case 3:
                    ksID = ksList.get((int) msg.obj).getId();
                    isRefresh = true;
                    page = 0;
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START, "");
                    loadData();
                    break;
                case 4:
                    dismissProgressDialog();
                    mater.finishRefreshing();
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES, "");
                    if (!hospitalList.isEmpty()) {
                        tvHospital.setText(hospitalList.get(0).getName());
                        hospitalId = hospitalList.get(0).getId();
                        isRefresh = true;
                        page = 0;
                        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START, "");
                        loadDataOfSup();
                    }else {
                        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL,getResources().getString(R.string.no_data),R.mipmap.no_data);
                    }
                    break;
                case 5:
                    hospitalId = hospitalList.get((int) msg.obj).getId();
                    isRefresh = true;
                    page = 0;
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START, "");
                    loadDataOfSup();
                    break;
                case 100:
                    isRefresh = true;
                    page = 0;
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START, "");
                    if (meMenu.getCode().equals(MenuEnum.count_ly)) {
                        loadData();
                    }else {
                        loadDataOfSup();
                    }
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
        return R.layout.count_ly_layout;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        meMenu = (MeMenu) getIntent().getSerializableExtra("Menu");
        actHelper.setToolbarTitleCenter(bar, meMenu.getText());
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START, "");
        startDate = DateUtils.getStringByFormat(DateUtils.getFirstdayofThisMonth(), "yyyy-MM-dd");
        endDate = DateUtils.getCurrentDate("yyyy-MM-dd");
        preStartDate = startDate;
        preEndDate = endDate;
        lyDate.setText(startDate + "至" + endDate);
        initAdapter();
        if (meMenu.getCode().equals(MenuEnum.count_ly)) {
            tvHospital.setVisibility(View.GONE);
            lyKs.setVisibility(View.VISIBLE);
            isFirstLoad = true;
            loadKs();
        } else {
            tvHospital.setVisibility(View.VISIBLE);
            lyKs.setVisibility(View.GONE);
            loadHzdw();
        }

//        loadData();
    }

    private void loadHzdw() {
        DataHelper.init().getHospitalList(isOnlyHospital, new HttpCallBack<List<Type>>() {
            @Override
            public void onSuccess(List<Type> respone) {
                hospitalList = respone;
                handler.sendEmptyMessage(4);
            }

            @Override
            public void onTodo(MeRespone respone) {
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

    private void loadKs() {
        DataHelper.init().getKeShi(new HttpCallBack<List<Type>>() {
            @Override
            public void onSuccess(List<Type> respone) {
                ksList = respone;
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
        adapter = new CommonAdapter<SysltjEntity.ItemsBean>(context, R.layout.count_item_layout) {
            @Override
            public void convert(ViewHolder holder, SysltjEntity.ItemsBean entity, int position) {
                holder.setText(R.id.count_item_name, entity.getPName());
                holder.setText(R.id.count_item_units, entity.getPUnit());
                holder.setText(R.id.count_item_spec, entity.getPSpec());
                holder.setText(R.id.count_item_quantity, entity.getQuantity() + "");
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
        mater.setMaterialRefreshListener(refreshListener);
    }

    private MaterialRefreshListener refreshListener = new MaterialRefreshListener() {
        @Override
        public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
            isRefresh = true;
            page = 0;
            if (meMenu.getCode().equals(MenuEnum.count_ly)) {
                loadData();
            }else {
                loadDataOfSup();
            }
        }

        @Override
        public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
            isRefresh = false;
            if (meMenu.getCode().equals(MenuEnum.count_ly)) {
                loadData();
            }else {
                loadDataOfSup();
            }
        }
    };


    private void loadData() {
        DataHelper.init().getLiyongCount(page++, ksID, startDate, endDate, new
                HttpCallBack<SysltjEntity>() {
                    @Override
                    public void onSuccess(SysltjEntity respone) {
                        sublist = respone.getItems();
                        pageSize = respone.getPageSize();
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

    private void loadDataOfSup() {
        DataHelper.init().getGysLiyongCount(page++, hospitalId, startDate, endDate, new
                HttpCallBack<SysltjEntity>() {
                    @Override
                    public void onSuccess(SysltjEntity respone) {
                        sublist = respone.getItems();
                        pageSize = respone.getPageSize();
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


    @OnClick({R.id.ly_ks, R.id.ly_date, R.id.tv_hospital})
    public void onViewClicked(View view) {
        if (actHelper.isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.ly_ks:
                int len = ksList == null ? 0 : ksList.size();
                if (len > 0) {
                    ChoiceTypeDialog dialog = new ChoiceTypeDialog(context, handler, 3);
                    dialog.showDialog("请选择科室", lyKs, ksList);
                } else {
                    loadKs();
                }

                break;
            case R.id.ly_date:
                showPop();
                break;
            case R.id.tv_hospital:
                int length = hospitalList == null ? 0 : hospitalList.size();
                if (length > 1) {
                    ChoiceTypeDialog dialog = new ChoiceTypeDialog(context, handler, 5);
                    dialog.showDialog("请选择医院", tvHospital, hospitalList);
                } else {
                    loadHzdw();
                }
                break;
        }
    }

    private CommonPopupWindow popupWindow;

    public void showPop() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(this).inflate(R.layout.date_range_picker_layout, null);
        //测量View的宽高
        CommonUtil.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(context)
                .setView(R.layout.date_range_picker_layout)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.5f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();

        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.date_range_picker_layout:
                CustomDatePicker datePickerStart = view.findViewById(R.id.datePickerStart);
                CustomDatePicker datePickerEnd = view.findViewById(R.id.datePickerEnd);
                TextView cancelBtn = view.findViewById(R.id.cancelBtn);
                TextView submitBtn = view.findViewById(R.id.submitBtn);
                datePickerStart.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
                datePickerEnd.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
                datePickerStart.setPickerMargin(3);
                datePickerStart.setDividerColor(getResources().getColor(R.color.gray));
                datePickerEnd.setDividerColor(getResources().getColor(R.color.gray));
                datePickerEnd.setPickerMargin(3);

                if (!CommonUtils.isNull(startDate) && !CommonUtils.isNull(endDate)) {
                    datePickerStart.setDate(startDate);
                    datePickerEnd.setDate(endDate);
                    datePickerStart.setOnDateChangedListener(startDateChangedListener);
                    datePickerEnd.setOnDateChangedListener(endDateChangedListener);
//                    datePickerStart.init(Integer.valueOf(startYear), Integer.valueOf
// (startMonth), Integer.valueOf(startDay),startDateChangedListener);
//                    datePickerEnd.init(Integer.valueOf(endYear), Integer.valueOf(endMonth),
// Integer.valueOf(endDay), endDateChangedListener);
                } else {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int monthOfYear = calendar.get(Calendar.MONTH);
                    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                    datePickerStart.init(year, monthOfYear, dayOfMonth, startDateChangedListener);
                    datePickerEnd.init(year, monthOfYear, dayOfMonth, endDateChangedListener);
                }

                submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startDate = preStartDate;
                        endDate = preEndDate;
                        lyDate.setText(startDate + "至" + endDate);
                        handler.sendEmptyMessage(100);
                        popupWindow.dismiss();
                    }
                });
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });
                break;
        }
    }

    private DatePicker.OnDateChangedListener startDateChangedListener = new DatePicker
            .OnDateChangedListener() {


        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // 获取一个日历对象，并初始化为当前选中的时间
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd");
            preStartDate = format.format(calendar.getTime());
        }
    };

    private DatePicker.OnDateChangedListener endDateChangedListener = new DatePicker
            .OnDateChangedListener() {


        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
// 获取一个日历对象，并初始化为当前选中的时间
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd");
            preEndDate = format.format(calendar.getTime());
        }
    };


}
