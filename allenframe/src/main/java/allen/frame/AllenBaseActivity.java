package allen.frame;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import allen.frame.tools.StringUtils;
import allen.frame.widget.MaterialRefreshLayout;

public abstract class AllenBaseActivity extends AppCompatActivity {
	
	public ActivityHelper actHelper;
	private ProgressDialog dialog;
	public Context context = this;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		AllenManager.init(getApplication());
		actHelper = new ActivityHelper(this);
		AllenManager.getInstance().addActivity(this);
		super.onCreate(savedInstanceState);
		if(isStatusBarColorWhite()){
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			}
		}
		setContentView(getLayoutResID());
		initBar();
		initUI(savedInstanceState);
		addEvent();
	}

	protected abstract boolean isStatusBarColorWhite();

	protected abstract int getLayoutResID();
	
	protected abstract void initBar();

	protected abstract void initUI(@Nullable Bundle savedInstanceState);

	protected abstract void addEvent();

	/**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
	protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) findViewById(id);
    }
	
	@Override
	protected void onDestroy() {
		AllenManager.getInstance().closeActivity(this);
		if(dialog!=null&&dialog.isShowing()){
			dialog.dismiss();
			dialog = null;
		}
		super.onDestroy();
	}
	public void showProgressDialog(String msg){
		if(dialog==null?true:!dialog.isShowing()){
			dialog = new ProgressDialog(this, R.style.Allen_Dialog_Theme);
			dialog.setMessage(StringUtils.notEmpty(msg)?msg:"正在处理,请稍等...");
			dialog.setCancelable(false);
			dialog.show();
			dialog.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.dialog_bar));
		}else{
			dialog.setMessage(StringUtils.notEmpty(msg)?msg:"正在处理,请稍等...");
		}
	}
	@SuppressWarnings("deprecation")
	public void showOldProgressDialog(String msg){
		if(dialog==null||!dialog.isShowing()){
			dialog = new ProgressDialog(this, AlertDialog.THEME_HOLO_LIGHT);
			dialog.setMessage(StringUtils.notEmpty(msg)?msg:"正在处理,请稍等...");
			dialog.setCancelable(false);
			dialog.show();
			dialog.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.dialog_bar));
		}else{
			dialog.setMessage(StringUtils.notEmpty(msg)?msg:"正在处理,请稍等...");
		}
	}
	public void dismissProgressDialog(){
		if(dialog!=null&&dialog.isShowing()){
			dialog.dismiss();
		}
	}
	
	public void setCanLoadMore(MaterialRefreshLayout mater,int pageSize,int csize){
		if(csize>0&&csize%pageSize==0){
			mater.setLoadMore(true);
		}else{
			mater.setLoadMore(false);
		}
	}
}
