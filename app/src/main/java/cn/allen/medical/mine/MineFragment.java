package cn.allen.medical.mine;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import allen.frame.ActivityHelper;
import allen.frame.AllenManager;
import allen.frame.tools.MsgUtils;
import allen.frame.widget.CircleImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.allen.medical.LoginActivity;
import cn.allen.medical.R;
import cn.allen.medical.data.DataHelper;
import cn.allen.medical.data.HttpCallBack;
import cn.allen.medical.data.MeRespone;
import cn.allen.medical.entry.User;
import cn.allen.medical.utils.Constants;

public class MineFragment extends Fragment {
    @BindView(R.id.user_photo)
    CircleImageView userPhoto;
    @BindView(R.id.user_name)
    AppCompatTextView userName;
    Unbinder unbinder;
    @BindView(R.id.user_psw)
    AppCompatTextView userPsw;
    @BindView(R.id.user_info)
    AppCompatTextView userInfo;
    @BindView(R.id.user_exit)
    AppCompatTextView userExit;

    private ActivityHelper helper;
    private User user;

    public static MineFragment init() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        helper = new ActivityHelper(getActivity(),view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.user_psw, R.id.user_info, R.id.user_exit})
    public void onViewClicked(View view) {
        if(helper.isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.user_psw:
                startActivity(new Intent(getActivity(),ChangePswActivity.class));
                break;
            case R.id.user_info:
                startActivity(new Intent(getActivity(),AboutUsActivity.class));
                break;
            case R.id.user_exit:
                MsgUtils.showMDMessage(getActivity(), "是否退出登录?", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        exit();
                    }
                }, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                break;
        }
    }
    private void exit(){
        helper.showProgressDialog("");
        DataHelper.init().exit(new HttpCallBack() {
            @Override
            public void onSuccess(Object respone) {

            }

            @Override
            public void onTodo(MeRespone respone) {
                handler.sendEmptyMessage(0);
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

    private void userInfo(){
        DataHelper.init().uerInfo(new HttpCallBack<User>() {
            @Override
            public void onSuccess(User respone) {
                user = respone;
            }

            @Override
            public void onTodo(MeRespone respone) {

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
                    helper.dismissProgressDialog();
                    helper.getSharedPreferences().edit().putString(Constants.User_Token,"").commit();
                    AllenManager.getInstance().back2Activity(LoginActivity.class);
                    break;
                case -1:
                    helper.dismissProgressDialog();
                    MsgUtils.showMDMessage(getActivity(), (String) msg.obj);
                    break;
                case 1:
                    Glide.with(getActivity())
                            .load(user.getPictureUrl())
                            .into(userPhoto);
                    userName.setText(user.getUserName());
                    break;
            }
        }
    };
}
