package cn.allen.medical.todo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.Glide;

import allen.frame.tools.DownLoadNewHelper;
import allen.frame.tools.MsgUtils;
import allen.frame.widget.PhotoView;
import cn.allen.medical.R;
import cn.allen.medical.utils.Constants;

public class PicFragment extends Fragment {

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case -2:
                    MsgUtils.showMDMessage(getActivity(), msg.obj.toString());
                    break;
            }
        }
    };
    public static PicFragment getInstance(String url) {
        PicFragment fragment = new PicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
			savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pic, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);

        String url = DownLoadNewHelper.doEncoderUrlStr(getArguments().getString("url", ""));
        PhotoView imageView = (PhotoView) view.findViewById(R.id.pic);
        imageView.setScaleType(ScaleType.CENTER_INSIDE);
        imageView.enable();
        imageView.enableRotate();
        Glide.with(this).load(url).fitCenter().dontAnimate().error(R.mipmap.no_internet).into(imageView);

        imageView.setOnClickListener(l);

    }

    private OnClickListener l = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pic:
                    Intent intent=new Intent();
                    intent.putExtra("fileType","img");
                    getActivity().setResult(300,intent);
                    getActivity().finish();
                    break;
                default:
                    break;
            }
        }
    };
}
