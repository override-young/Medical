package cn.allen.medical.count;

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
import java.util.List;

import allen.frame.tools.OnAllenItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.allen.medical.R;
import cn.allen.medical.adapter.MenuAdapter;
import cn.allen.medical.entry.MeMenu;
import cn.allen.medical.entry.SysltjEntity;
import cn.allen.medical.warning.CompanyWarningActivity;
import cn.allen.medical.warning.ContractWarningActivity;

public class CountFragment extends Fragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    private MenuAdapter adapter;
    private List<MeMenu> list;

    public static CountFragment init() {
        CountFragment fragment = new CountFragment();
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

    private void initUI(){
        adapter = new MenuAdapter(true);
        GridLayoutManager manager = new GridLayoutManager(getActivity(),2);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        loadData();
    }

    private void addEvent(){
        adapter.setItemClickListener(new OnAllenItemClickListener<MeMenu>() {
            @Override
            public void onItemClick(MeMenu menu) {
                switch (menu.getId()){
                    case "1":
                        startActivity(new Intent(getContext(),SelectSumChartActivity.class));
                        break;
                    case "2":
                        startActivity(new Intent(getContext(),KucunCountActivity.class));
                        break;
                    case "3":

                        break;
                    case "4":
                        startActivity(new Intent(getContext(),SysltjActivity.class));
                        break;
                }
            }
        });
    }

    private void loadData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                list = new ArrayList<>();
                list.add(new MeMenu("1","入库数量统计",0,R.mipmap.count_rk));
                list.add(new MeMenu("2","库存数量统计",0,R.mipmap.count_kcsl));
                list.add(new MeMenu("3","领用数量统计",0,R.mipmap.count_ly));
                list.add(new MeMenu("4","使用数量统计",0,R.mipmap.count_sy));
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
