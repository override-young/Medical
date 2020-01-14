package cn.allen.medical.count;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.List;

import allen.frame.AllenManager;
import allen.frame.tools.OnAllenItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.allen.medical.R;
import cn.allen.medical.adapter.MenuAdapter;
import cn.allen.medical.entry.MeMenu;
import cn.allen.medical.entry.MenuEnum;
import cn.allen.medical.entry.SysltjEntity;
import cn.allen.medical.utils.Constants;
import cn.allen.medical.warning.CompanyWarningActivity;
import cn.allen.medical.warning.ContractWarningActivity;
import cn.allen.medical.warning.WarningFragment;

public class CountFragment extends Fragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    private MenuAdapter adapter;
    private List<MeMenu> list;

    private SharedPreferences shared;

    public static CountFragment init() {
        CountFragment fragment = new CountFragment();
        return fragment;
    }

    public CountFragment setList(List<MeMenu> list){
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

    private void initUI(){
        shared = AllenManager.getInstance().getStoragePreference();
        adapter = new MenuAdapter(shared.getInt(Constants.User_Role,0)==7);
        if(shared.getInt(Constants.User_Role,0)==7){
            GridLayoutManager manager = new GridLayoutManager(getActivity(),2);
            rv.setLayoutManager(manager);
        }else{
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rv.setLayoutManager(manager);
        }
        rv.setAdapter(adapter);
        loadData();
    }

    private void addEvent(){
        adapter.setItemClickListener(new OnAllenItemClickListener<MeMenu>() {
            @Override
            public void onItemClick(MeMenu menu) {
                switch (menu.getCode()){
                    case MenuEnum.count_rk:
                        startActivity(new Intent(getContext(),SelectSumChartActivity.class));
                        break;
                    case MenuEnum.count_kcsl:
                        startActivity(new Intent(getContext(),KucunCountActivity.class));
                        break;
                    case MenuEnum.count_ly:
                        startActivity(new Intent(getActivity(),CountLyActivity.class));
                        break;
                    case MenuEnum.count_sy:
                        startActivity(new Intent(getContext(),SysltjActivity.class));
                        break;
                    case MenuEnum.gys_count_fh://发货数量统计

                        break;

                }
            }
        });
    }

    private void loadData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }).start();
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    adapter.setData(list);
                    break;
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
