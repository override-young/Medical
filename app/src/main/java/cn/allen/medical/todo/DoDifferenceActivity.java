package cn.allen.medical.todo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

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
import cn.allen.medical.entry.BillDifferentEntity;
import cn.allen.medical.entry.DifferencesEntity;
import cn.allen.medical.entry.DoDifferenceEntity;
import cn.allen.medical.utils.CommonAdapter;
import cn.allen.medical.utils.PopupWindow.CommonPopupWindow;
import cn.allen.medical.utils.PopupWindow.CommonUtil;
import cn.allen.medical.utils.ViewHolder;

public class DoDifferenceActivity extends AllenBaseActivity implements CommonPopupWindow
        .ViewInterface {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_number)
    AppCompatTextView tvNumber;
    @BindView(R.id.tv_date)
    AppCompatTextView tvDate;
    @BindView(R.id.refreshLayout)
    MaterialRefreshLayout refreshLayout;
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;
    @BindView(R.id.swipeRecyclerView)
    SwipeRecyclerView swipeRecyclerView;
    @BindView(R.id.tv_add)
    AppCompatTextView tvAdd;


    private Context mContext = this;
    private CommonAdapter<DoDifferenceEntity.ItemsBean> adapter;
    private List<DoDifferenceEntity.ItemsBean> list = new ArrayList<>();
    private List<DoDifferenceEntity.ItemsBean> doDiffList = new ArrayList<>();
    private CommonAdapter<DoDifferenceEntity.ItemsBean> differentAdapter;
    private List<DifferencesEntity> addList = new ArrayList<>();
    private CommonPopupWindow popupWindow;

    private SwipeMenuCreator swipeMenuCreator;
    private String id, key = "";

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    BillDifferentEntity differentEntity = (BillDifferentEntity) msg.obj;
                    tvNumber.setText(differentEntity.getCode());
                    tvDate.setText(differentEntity.getStartTime().replaceAll(" 00:00:00", "") +
                            "\n" + differentEntity.getEndTime().replaceAll(" 00:00:00", ""));

                    break;
                case 1:
                    dismissProgressDialog();
                    break;
                case 2:
                    Logger.e("differentList.size:", doDiffList.size() + "");
                    dismissProgressDialog();
                    if (popupWindow != null && popupWindow.isShowing()) {
                        differentAdapter.setDatas(doDiffList);
                    } else {
                        showPop();
                        differentAdapter.setDatas(doDiffList);
                    }
                    break;
                case 3:
                    dismissProgressDialog();
                    MsgUtils.showLongToast(mContext,"处理差异提交成功!");
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
        return R.layout.activity_do_difference;
    }

    @Override
    protected void initBar() {
        ButterKnife.bind(this);
        actHelper.setToolbarTitleCenter(toolbar, "处理差异");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initUI(@Nullable Bundle savedInstanceState) {
        id = getIntent().getStringExtra("ID");
        setSwipeMenu();
        swipeRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        initAdapter();
        loadData();


    }

    private void initAdapter() {
        swipeRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .VERTICAL, false));
        adapter = new CommonAdapter<DoDifferenceEntity.ItemsBean>(mContext, R.layout
                .do_difference_item_layout) {
            @Override
            public void convert(ViewHolder holder, DoDifferenceEntity.ItemsBean entity,
                                int position) {
                holder.setText(R.id.tv_name, entity.getPackageName());
                holder.setText(R.id.tv_danwei, entity.getUnit());
                holder.setText(R.id.tv_guige, entity.getSpec());
                holder.setText(R.id.tv_difference_count, entity.getCount() + "");
                holder.setOnClickListener(R.id.tv_difference_add, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int count = entity.getCount();

                        entity.setCount(count += 1);
                        notifyDataSetChanged();
                    }
                });
                holder.setOnClickListener(R.id.tv_difference_subtract, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int count = entity.getCount();
                        entity.setCount(count -= 1);
                        notifyDataSetChanged();
                    }
                });
            }
        };
        swipeRecyclerView.setAdapter(adapter);

        differentAdapter = new CommonAdapter<DoDifferenceEntity.ItemsBean>(mContext, R.layout
                .popup_difference_item_layout) {
            @Override
            public void convert(ViewHolder holder, DoDifferenceEntity.ItemsBean entity, int
                    position) {
                holder.setText(R.id.tv_name, entity.getPackageName());
                holder.setText(R.id.tv_danwei, entity.getUnit());
                holder.setText(R.id.tv_guige, entity.getSpec());
                holder.setText(R.id.tv_gys, entity.getSName());
                holder.setBackgroundRes(R.id.tv_add, R.mipmap.pop_add);
                for (DoDifferenceEntity.ItemsBean addEntity : list) {
                    if (addEntity.getSpid().equals(entity.getSpid())) {
                        entity.setAdd(true);
                        holder.setBackgroundRes(R.id.tv_add, R.mipmap.pop_add_gray);
                        break;
                    }
                }
                holder.setOnClickListener(R.id.tv_add, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (entity.isAdd()) {
                            return;
                        }
                        DoDifferenceEntity.ItemsBean itemsBean = new DoDifferenceEntity.ItemsBean();
                        itemsBean.setAdd(true);
                        itemsBean.setCode(entity.getCode());
                        itemsBean.setUnit(entity.getUnit());
                        itemsBean.setSpid(entity.getSpid());
                        itemsBean.setSpec(entity.getSpec());
                        itemsBean.setSName(entity.getSName());
                        itemsBean.setSid(entity.getSid());
                        itemsBean.setPinYin(entity.getPinYin());
                        itemsBean.setPid(entity.getPid());
                        itemsBean.setPackageName(entity.getPackageName());
                        itemsBean.setManufacturerName(entity.getManufacturerName());
                        itemsBean.setCount(1);
                        list.add(itemsBean);
                        adapter.setDatas(list);
                        differentAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
    }

    private void setSwipeMenu() {
        //                        .setImage(R.mipmap.icon_img)
        swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int
                    viewType) {
                int width = 200;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                        .setBackground(R.color.brown)
//                        .setImage(R.mipmap.icon_img)
                        .setText("删除")
                        .setWidth(width)
                        .setTextColorResource(R.color.white)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);
            }
        };
        swipeRecyclerView.setOnItemMenuClickListener(mItemMenuClickListener);


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

    OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

            // 左侧还是右侧菜单：
            int direction = menuBridge.getDirection();
            // 菜单在Item中的Position：
            int menuPosition = menuBridge.getPosition();

            if (direction == swipeRecyclerView.RIGHT_DIRECTION) {
                if (menuPosition == 0) {
//                    Toast.makeText(mContext, "删除第" + position + "个条目", Toast.LENGTH_LONG).show();
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    };

    private void loadData() {
        showProgressDialog("");
        DataHelper.init().getBillDifferentDetails(id, new HttpCallBack<BillDifferentEntity>() {
            @Override
            public void onSuccess(BillDifferentEntity respone) {
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

    private void loadDifference() {
        DataHelper.init().getDifferentList(key, new HttpCallBack<DoDifferenceEntity>() {
            @Override
            public void onSuccess(DoDifferenceEntity respone) {
                doDiffList = respone.getItems();
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
                Logger.e("debug", respone.toString());
                Message msg = new Message();
                msg.what = -1;
                msg.obj = respone.getMessage();
                handler.sendMessage(msg);
            }
        });

    }


    @OnClick({R.id.tv_number, R.id.tv_date, R.id.btn_submit, R.id.tv_add})
    public void onViewClicked(View view) {
        if (actHelper.isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.tv_number:
                break;
            case R.id.tv_date:
                break;
            case R.id.btn_submit:
                for (DoDifferenceEntity.ItemsBean entity : adapter.getDatas()) {
                    if (entity.getCount() != 0) {
                        DifferencesEntity differencesEntity = new DifferencesEntity();
                        differencesEntity.setQuantity(entity.getCount());
                        differencesEntity.setSpid(entity.getSpid());
                        differencesEntity.setRemarks("");
                        addList.add(differencesEntity);
                    }
                }
                if (addList.size()>0){
                    showProgressDialog("");
                    submit();
                }else {
                    MsgUtils.showMDMessage(mContext,"请添加耗材,且添加耗材数量不能为0！");
                }

                break;
            case R.id.tv_add:
                showProgressDialog("");
                loadDifference();
                break;
        }
    }

    private void submit() {
        DataHelper.init().dillDifferentList(id, new Gson().toJson(addList), new HttpCallBack() {
            @Override
            public void onSuccess(Object respone) {

            }

            @Override
            public void onTodo(MeRespone respone) {
                Message msg = new Message();
                msg.what = 3;
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


    public void showPop() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(this).inflate(R.layout.popup_add, null);
        //测量View的宽高
        CommonUtil.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(mContext)
                .setView(R.layout.popup_add)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.5f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();

        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }


    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popup_add:
                AppCompatTextView tvClose = view.findViewById(R.id.tv_close);
                AppCompatEditText etSearchName = view.findViewById(R.id.et_search_name);
                RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                        .VERTICAL, false));

                recyclerView.setAdapter(differentAdapter);
                etSearchName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        key = etSearchName.getText().toString();
                        loadDifference();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


                tvClose.setOnClickListener(new View.OnClickListener() {
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
}
