package cn.allen.medical.todo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import allen.frame.tools.OnAllenItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.allen.medical.R;
import cn.allen.medical.adapter.MenuAdapter;
import cn.allen.medical.count.CountFragment;
import cn.allen.medical.data.DataHelper;
import cn.allen.medical.data.HttpCallBack;
import cn.allen.medical.data.MeRespone;
import cn.allen.medical.entry.MeMenu;
import cn.allen.medical.entry.MenuEnum;
import cn.allen.medical.entry.MenuEnum;
import cn.allen.medical.entry.TodoCount;
import cn.allen.medical.utils.OnUpdateCountListener;
import cn.allen.medical.warning.CompanyWarningActivity;
import cn.allen.medical.warning.ContractWarningActivity;

public class TodoFragment extends Fragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    private MenuAdapter adapter;
    private List<MeMenu> list;

    public static TodoFragment init() {
        TodoFragment fragment = new TodoFragment();
        return fragment;
    }

    public TodoFragment setList(List<MeMenu> list){
        this.list = list;
        return this;
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
        loadData();
    }

    private void initUI() {
        adapter = new MenuAdapter(false);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        adapter.setData(list);
    }

    private void addEvent() {
        adapter.setItemClickListener(new OnAllenItemClickListener<MeMenu>() {
            @Override
            public void onItemClick(MeMenu menu) {
                switch (menu.getCode()){
                    case MenuEnum.todo_ht://待确认合同
                        startActivity(new Intent(getContext(),ToDoContractActivity.class));
                        break;
                    case MenuEnum.todo_zd_ks://待确认账单（科室）
                        startActivity(new Intent(getContext(),ToDoBillActivity.class).putExtra("CODE",MenuEnum.todo_zd_ks));
                        break;
                    case MenuEnum.todo_zd_sb://待确认账单（设备室）
                        startActivity(new Intent(getContext(),ToDoBillActivity.class).putExtra("CODE",MenuEnum.todo_zd_sb));
                        break;
                    case MenuEnum.todo_jg://待确认价格
                        startActivity(new Intent(getContext(),ToDoPriceActivity.class));
                        break;
                }
            }
        });
    }
    private TodoCount entry;
    private void loadData() {
        DataHelper.init().todoCount(new HttpCallBack<TodoCount>() {
            @Override
            public void onSuccess(TodoCount respone) {
                entry = respone;
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
                        Map<String,Integer> map = new HashMap<>();
                        map.put(MenuEnum.todo_ht,entry.getContractCount());
                        map.put(MenuEnum.todo_jg,entry.getPriceCount());
                        map.put(MenuEnum.todo_zd_ks,entry.getDepartmentBillCount());
                        map.put(MenuEnum.todo_zd_sb,entry.getBillCount());
                        adapter.setCount(map);
                        int index = 0;
                        for(MeMenu menu:list){
                            index = index + map.get(menu.getCode());
                        }
                        if(listener!=null){
                            listener.count(index);
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

    public TodoFragment setUpdateCount(OnUpdateCountListener listener){
        this.listener = listener;
        return this;
    }
    private OnUpdateCountListener listener;
}
