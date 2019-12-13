package cn.allen.medical.todo;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.view.ViewGroup;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import allen.frame.AllenBaseActivity;
import allen.frame.widget.MaterialRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.allen.medical.R;
import cn.allen.medical.entry.DoDifferenceEntity;
import cn.allen.medical.utils.CommonAdapter;
import cn.allen.medical.utils.ViewHolder;

public class DoDifferenceActivity extends AllenBaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_number)
    AppCompatTextView tvNumber;
    @BindView(R.id.tv_date)
    AppCompatTextView tvDate;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    MaterialRefreshLayout refreshLayout;
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;
    @BindView(R.id.swipeRecyclerView)
    SwipeRecyclerView swipeRecyclerView;


    private Context mContext = this;
    private CommonAdapter<DoDifferenceEntity> adapter;
    private List<DoDifferenceEntity> list = new ArrayList<>();

    private SwipeMenuCreator swipeMenuCreator;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:

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


        for (int i = 0; i < 3; i++) {
            list.add(new DoDifferenceEntity());
        }
        setSwipeMenu();
        swipeRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        initAdapter();

    }

    private void initAdapter() {
        swipeRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
                .VERTICAL, false));
        adapter = new CommonAdapter<DoDifferenceEntity>(mContext, R.layout
                .do_difference_item_layout) {
            @Override
            public void convert(ViewHolder holder, DoDifferenceEntity entity, int position) {

            }
        };
        swipeRecyclerView.setAdapter(adapter);
        adapter.setDatas(list);
    }

    private void setSwipeMenu() {
        //                        .setImage(R.mipmap.icon_img)
        swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
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
                if(menuPosition==0){
                    Toast.makeText(mContext, "编辑第" + position + "个条目", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(mContext, "删除第" + position + "个条目", Toast.LENGTH_LONG).show();
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    };

    @OnClick({R.id.tv_number, R.id.tv_date, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_number:
                break;
            case R.id.tv_date:
                break;
            case R.id.btn_submit:
                break;
        }
    }

}
