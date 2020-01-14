package cn.allen.medical.todo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import allen.frame.ActivityHelper;
import allen.frame.tools.Logger;
import allen.frame.tools.MsgUtils;
import allen.frame.widget.MaterialRefreshLayout;
import allen.frame.widget.MaterialRefreshListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.allen.medical.R;
import cn.allen.medical.data.DataHelper;
import cn.allen.medical.data.HttpCallBack;
import cn.allen.medical.data.MeRespone;
import cn.allen.medical.entry.ToDoContractEntity;
import cn.allen.medical.entry.ToDoGysEntity;
import cn.allen.medical.entry.TodoCount;
import cn.allen.medical.utils.CommonAdapter;
import cn.allen.medical.utils.OnUpdateCountListener;
import cn.allen.medical.utils.ViewHolder;

import static android.app.Activity.RESULT_OK;

public class GTodoFragment extends Fragment {

    @BindView(R.id.rv)
    RecyclerView recyclerview;
    Unbinder unbinder;
    @BindView(R.id.refreshLayout)
    MaterialRefreshLayout refreshLayout;

    private ActivityHelper activityHelper;
    private CommonAdapter<ToDoGysEntity.ItemsBean> adapter;
    private List<ToDoGysEntity.ItemsBean> list = new ArrayList<>();
    private List<ToDoGysEntity.ItemsBean> sublist = new ArrayList<>();
    private boolean isRefresh = false;
    private int page = 0, pageSize = 20;

    public static GTodoFragment init() {
        GTodoFragment fragment = new GTodoFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.count_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        activityHelper=new ActivityHelper(getActivity(),view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        addEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCountData();
    }

    private void initUI() {
        initAdapter();
        activityHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_START,"");
        loadData();
    }

    private void addEvent() {
        adapter.setOnItemClickListener(onItemClickListener);
        refreshLayout.setMaterialRefreshListener(refreshListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            isRefresh = true;
            page = 0;
            loadData();
        }
    }

    private void initAdapter() {
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager
                .VERTICAL, false));
        adapter = new CommonAdapter<ToDoGysEntity.ItemsBean>(getContext(), R.layout
                .to_do_gys_item_layout) {
            @Override
            public void convert(ViewHolder holder, ToDoGysEntity.ItemsBean entity, int
                    position) {
                holder.setText(R.id.tv_num, entity.getOrderNo());
                holder.setText(R.id.tv_cgdw, entity.getOrgName());
//                int state = entity.getStatus();
                holder.setText(R.id.tv_state, "待审核");

            }
        };
        recyclerview.setAdapter(adapter);
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
            Intent intent = new Intent(getContext(), GysToDoDetailsActivity.class);
            intent.putExtra("ID", list.get(position).getId());
            startActivityForResult(intent, 100);
        }

        @Override
        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
            return false;
        }
    };


    private void loadData() {
        DataHelper.init().getTodoGys(page++, new HttpCallBack<ToDoGysEntity>() {
            @Override
            public void onSuccess(ToDoGysEntity respone) {
                sublist = respone.getItems();
                pageSize = respone.getPageSize();
                handler.sendEmptyMessage(1);
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
                Logger.e("debug", respone.toString());
                Message msg = new Message();
                msg.what = -1;
                msg.obj = respone.getMessage();
                handler.sendMessage(msg);
            }
        });
    }


    private TodoCount entry;

    private void loadCountData() {
        DataHelper.init().todoCount(false, new HttpCallBack<TodoCount>() {
            @Override
            public void onSuccess(TodoCount respone) {
                entry = respone;
                Logger.e("todo", entry.toString());
            }

            @Override
            public void onTodo(MeRespone respone) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void tokenErro(MeRespone respone) {

            }

            @Override
            public void onFailed(MeRespone respone) {

            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (entry != null) {
                        if (listener != null) {
                            postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    listener.count(entry.getOrderCount());
                                }
                            }, 1000);
                        }
                    }
                    break;
                case 1:
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
                    setCanLoadMore(refreshLayout, pageSize, list);
                    break;
                case 2:
                    activityHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_SUCCES,"");
                    refreshLayout.finishRefreshing();
                    break;
                case -1:
                    activityHelper.setLoadUi(ActivityHelper.PROGRESS_STATE_FAIL,"");
                    MsgUtils.showMDMessage(getContext(), (String) msg.obj);
                    break;
            }
        }
    };

    private void setCanLoadMore(MaterialRefreshLayout mater, int pageSize, List<?> list) {
        int csize = list == null ? 0 : list.size();
        if (csize > 0 && csize % pageSize == 0) {
            mater.setLoadMore(true);
        } else {
            mater.setLoadMore(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public GTodoFragment setUpdateCount(OnUpdateCountListener listener) {
        this.listener = listener;
        return this;
    }

    private OnUpdateCountListener listener;
}
