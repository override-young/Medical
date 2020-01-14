package cn.allen.medical.todo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import allen.frame.AllenManager;
import allen.frame.tools.Logger;
import allen.frame.tools.OnAllenItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.allen.medical.R;
import cn.allen.medical.adapter.MenuAdapter;
import cn.allen.medical.data.DataHelper;
import cn.allen.medical.data.HttpCallBack;
import cn.allen.medical.data.MeRespone;
import cn.allen.medical.entry.MeMenu;
import cn.allen.medical.entry.MenuEnum;
import cn.allen.medical.entry.TodoCount;
import cn.allen.medical.utils.Constants;
import cn.allen.medical.utils.OnUpdateCountListener;

public class GTodoFragment extends Fragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;

    public static GTodoFragment init() {
        GTodoFragment fragment = new GTodoFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.count_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
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

    }

    private void addEvent() {

    }
    private TodoCount entry;
    private void loadCountData() {
        DataHelper.init().todoCount(false, new HttpCallBack<TodoCount>() {
            @Override
            public void onSuccess(TodoCount respone) {
                entry = respone;
                Logger.e("todo",entry.toString());
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
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    if(entry!=null){
                        if(listener!=null){
                            postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    listener.count(entry.getOrderCount());
                                }
                            },1000);
                        }
                    }
                    break;
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public GTodoFragment setUpdateCount(OnUpdateCountListener listener){
        this.listener = listener;
        return this;
    }
    private OnUpdateCountListener listener;
}
