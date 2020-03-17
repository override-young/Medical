package cn.allen.medical.count;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.view.WheelView;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import allen.frame.ActivityHelper;
import allen.frame.AllenBaseActivity;
import allen.frame.entry.Type;
import allen.frame.tools.ChoiceTypeDialog;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.allen.medical.R;
import cn.allen.medical.data.DataHelper;
import cn.allen.medical.data.HttpCallBack;
import cn.allen.medical.data.MeRespone;
import cn.allen.medical.entry.MeMenu;
import cn.allen.medical.entry.MenuEnum;
import cn.allen.medical.entry.SelectSumChartEntity;
import cn.allen.medical.utils.YearPickerDialog;

public class SelectSumChartActivity extends AllenBaseActivity implements
        OnChartValueSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_year)
    AppCompatTextView tvYear;
    @BindView(R.id.barchart)
    HorizontalBarChart mHorizontalBarChart;
    @BindView(R.id.tv_hospital)
    AppCompatTextView tvHospital;

    private YearPickerDialog yearPickerDialog;
    private List<SelectSumChartEntity> list;
    private String year;
    private MeMenu meMenu;

    private int isOnlyHospital = 0;
    private List<Type> hospitalList;
    private String hospitalId = "";
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    setData();
                    break;
                case 1:
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES,"");
                    dismissProgressDialog();
                    break;
                case 2:
                    actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES,"");
                    dismissProgressDialog();
                    if (!hospitalList.isEmpty()) {
                        tvHospital.setText(hospitalList.get(0).getName());
                        hospitalId = hospitalList.get(0).getId();
                        showProgressDialog("");
                        loadDataOfSup();
                    }else {
                        tvHospital.setText("");
                        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL,getResources().getString(R.string.no_data),R.mipmap.no_data);
                    }

                    break;
                case 3:
                    hospitalId = hospitalList.get((int) msg.obj).getId();
                    showProgressDialog("");
                    loadDataOfSup();
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
        return R.layout.activity_select_sum_chart;
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
        actHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES,"");
        initBarChart();
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        year = y + "";
        tvYear.setText(year);
        if (meMenu.getCode().equals(MenuEnum.count_rk)) {
            tvHospital.setVisibility(View.GONE);
            loadDataOfHospital();
        } else {
            tvHospital.setVisibility(View.VISIBLE);
            loadHzdw();
        }
    }

    private void initBarChart() {
        //设置相关属性
//        mHorizontalBarChart.setOnChartValueSelectedListener(this);
        mHorizontalBarChart.setDrawBarShadow(false);
        mHorizontalBarChart.setDrawValueAboveBar(true);
        mHorizontalBarChart.getDescription().setEnabled(false);
        mHorizontalBarChart.setMaxVisibleValueCount(60);
        mHorizontalBarChart.setPinchZoom(false);
        mHorizontalBarChart.setDrawGridBackground(false);

        //x轴
        XAxis xl = mHorizontalBarChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGranularity(10f);
        xl.setLabelCount(12);
        xl.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int month = (int) (value / 10f);
                return month + "月";
            }
        });

        //y轴
        YAxis yl = mHorizontalBarChart.getAxisLeft();
        yl.setEnabled(false);
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setAxisMinimum(0f);


        //y轴
        YAxis yr = mHorizontalBarChart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(true);
        yr.setAxisMinimum(0f);
        yr.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (int) value + "个";
            }
        });


        mHorizontalBarChart.setFitBars(true);


        Legend l = mHorizontalBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);
    }

    //设置数据
    private void setData() {
        float barWidth = 9f;
        float spaceForBar = 10f;
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int month = list.get(i).getData1();
            int val = list.get(i).getData2();
            yVals1.add(new BarEntry(month * spaceForBar, val));
        }
        BarDataSet set1;
        if (mHorizontalBarChart.getData() != null &&
                mHorizontalBarChart.getData().getDataSetCount() > 0) {
            Logger.e("debug", "统计数据更新...");
            set1 = (BarDataSet) mHorizontalBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mHorizontalBarChart.getData().notifyDataChanged();
            mHorizontalBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "");
            set1.setColor(getResources().getColor(R.color.light_green2));
//        set1.setColor(Color.parseColor("#96DEDA"));

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(barWidth);
            mHorizontalBarChart.setData(data);
        }
        mHorizontalBarChart.animateY(1000);
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

    @OnClick({R.id.tv_year, R.id.tv_hospital})
    public void onViewClicked(View view) {
        if (actHelper.isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_year:
//                Calendar calendar = Calendar.getInstance();
//                if (yearPickerDialog == null) {
//                    new YearPickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog
//                            .OnDateSetListener() {
//
//                        @Override
//                        public void onDateSet(DatePicker view, int year, int monthOfYear, int
//                                dayOfMonth) {
//                            calendar.set(Calendar.YEAR, year);
////                            calendar.set(Calendar.MONTH, monthOfYear);
//                            tvYear.setText(year + "");
//                            SelectSumChartActivity.this.year = year + "";
//                            if (meMenu.getCode().equals(MenuEnum.count_rk)) {
//                                loadDataOfHospital();
//                            } else {
//                                loadDataOfSup();
//                            }
//
//                        }
//                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get
//                            (Calendar.DATE)).show();
//                } else {
//                    yearPickerDialog.show();
//                }
                dataPicker();
                break;
            case R.id.tv_hospital:
                int len = hospitalList == null ? 0 : hospitalList.size();
                if (len > 0) {
                    ChoiceTypeDialog dialog = new ChoiceTypeDialog(context, handler, 3);
                    dialog.showDialog("请选择医院", tvHospital, hospitalList);
                } else {
                    showProgressDialog("");
                    loadHzdw();
                }

                break;
        }
    }

    private void dataPicker() {

        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                year=getTime(date);
                tvYear.setText(year);
                showProgressDialog("");
                if (meMenu.getCode().equals(MenuEnum.count_rk)) {
                    loadDataOfHospital();
                } else {
                    loadDataOfSup();
                }
            }
        })
                .setType(new boolean[]{true, false, false, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
//                .setTitleSize(20)//标题文字大小
                .setTitleText("选择年份")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setContentTextSize(18)
                .setItemVisibleCount(5)
                .setLineSpacingMultiplier(2)
                .setDividerType(WheelView.DividerType.WRAP)
                .setTitleColor(getResources().getColor(R.color.light_green2))//标题文字颜色
                .setSubmitColor(getResources().getColor(R.color.light_green2))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.gray))//取消按钮文字颜色
                .setTextColorCenter(getResources().getColor(R.color.light_green2))
//                .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
////                .setRangDate(startDate,endDate)//起始终止年月日设定
//                //.setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        //"YYYY-MM-DD HH:MM:SS"        "yyyy-MM-dd"
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }


    private void loadHzdw() {
        DataHelper.init().getHospitalList(isOnlyHospital, new HttpCallBack<List<Type>>() {
            @Override
            public void onSuccess(List<Type> respone) {
                hospitalList = respone;
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
                Message msg = new Message();
                msg.what = -1;
                msg.obj = respone.getMessage();
                handler.sendMessage(msg);
            }
        });
    }

    private void loadDataOfSup() {
        DataHelper.init().getGysSelectSumChart(hospitalId, year, new
                HttpCallBack<List<SelectSumChartEntity>>() {

                    @Override
                    public void onSuccess(List<SelectSumChartEntity> respone) {
                        if (respone.size() == 0) {
                            for (int i = 0; i < 12; i++) {
                                respone.add(new SelectSumChartEntity(i + 1, 0));
                            }
                        }
                        list = respone;
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

    private void loadDataOfHospital() {
        DataHelper.init().getSelectSumChart(year, new HttpCallBack<List<SelectSumChartEntity>>() {
            @Override
            public void onSuccess(List<SelectSumChartEntity> respone) {
                if (respone.size() == 0) {
                    for (int i = 0; i < 12; i++) {
                        respone.add(new SelectSumChartEntity(i + 1, 0));
                    }
                }
                list = respone;
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
