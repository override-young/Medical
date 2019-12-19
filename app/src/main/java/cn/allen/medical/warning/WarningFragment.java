package cn.allen.medical.warning;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import cn.allen.medical.data.DataHelper;
import cn.allen.medical.data.HttpCallBack;
import cn.allen.medical.data.MeRespone;
import cn.allen.medical.entry.MeMenu;
import cn.allen.medical.entry.MenuEnum;
import cn.allen.medical.entry.WaringCount;
import cn.allen.medical.todo.TodoFragment;
import cn.allen.medical.utils.Constants;
import cn.allen.medical.utils.OnUpdateCountListener;

public class WarningFragment extends Fragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    private MenuAdapter adapter;
    private List<MeMenu> list;

    public static WarningFragment init() {
        WarningFragment fragment = new WarningFragment();
        return fragment;
    }

    public WarningFragment setList(List<MeMenu> list){
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

    private void initUI(){
        adapter = new MenuAdapter(true);
        GridLayoutManager manager = new GridLayoutManager(getActivity(),2);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        adapter.setData(list);
    }

    private void addEvent(){
        adapter.setItemClickListener(new OnAllenItemClickListener<MeMenu>() {
            @Override
            public void onItemClick(MeMenu menu) {
                switch (menu.getId()){
                    case "1":
                        startActivity(new Intent(getContext(),CompanyWarningActivity.class));
                        break;
                    case "2":
                        startActivity(new Intent(getContext(),ConsumableStoreWarningActivity.class));
                        break;
                    case "3":
                        startActivity(new Intent(getContext(),ConsumableQualityWarningActivity.class));
                        break;
                    case "4":
                        startActivity(new Intent(getContext(),ContractWarningActivity.class));
                        break;
                }
            }
        });
    }

    private WaringCount entry;
    private void loadData(){
        DataHelper.init().waringCount(new HttpCallBack<WaringCount>() {
            @Override
            public void onSuccess(WaringCount respone) {
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
                    if(listener!=null){
                        listener.count(entry.getTotalCount());
                    }
                    if(entry!=null){
                        Map<String,Integer> map = new HashMap<>();
                        map.put(MenuEnum.waring_hc,entry.getProductCertWarningCount());
                        map.put(MenuEnum.waring_ht,entry.getContractWarningCount());
                        map.put(MenuEnum.waring_zz,entry.getEnterpriseCertWarningCount());
                        map.put(MenuEnum.waring_xq,entry.getStockWarningCount());
                        adapter.setCount(map);
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

    public WarningFragment setUpdateCount(OnUpdateCountListener listener){
        this.listener = listener;
        return this;
    }
    private OnUpdateCountListener listener;
}
