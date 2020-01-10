package cn.allen.medical.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import allen.frame.adapter.TypeAdapter;
import allen.frame.entry.Type;
import cn.allen.medical.R;

public class WarningDialog extends Dialog {
    private Context context;
    private Handler handler;
    private String title;
    private String warningInfo;
    private int reciveid;

    public WarningDialog(Context context, Handler handler,String title,String warningInfo, int reciveid) {
        super(context);
        this.context = context;
        this.handler = handler;
        this.title=title;
        this.warningInfo=warningInfo;
        this.reciveid = reciveid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        setContentView(R.layout.warning_dialog_layout);
//        WindowManager windowManager = ((Activity) context).getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.width = display.getWidth() * 4 / 5;// 设置dialog宽度为屏幕的4/5
//        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);//点击外部Dialog消失
        TextView tvTitle=findViewById(R.id.tv_title);
        TextView tvWarnintInfo=findViewById(R.id.tv_warning_info);
        TextView tvCancel=findViewById(R.id.btn_cancel);
        TextView tvSubmit=findViewById(R.id.btn_submit);
        tvTitle.setText(title);
        tvWarnintInfo.setText(warningInfo);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg=new Message();
                msg.what=reciveid;
                handler.sendMessage(msg);
                dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
