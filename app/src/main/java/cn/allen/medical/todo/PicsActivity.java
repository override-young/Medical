package cn.allen.medical.todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import allen.frame.AllenIMBaseActivity;
import allen.frame.adapter.FragmentAdapter;
import allen.frame.tools.StringUtils;
import cn.allen.medical.R;

public class PicsActivity extends AllenIMBaseActivity {
	
	private ViewPager pager;
	private List<Fragment> fralist;
	private ArrayList<String> list;
	private FragmentAdapter adapter;
	private TextView des;
	private ScrollView scrollView;
	private Context context = this;
	private int index = 0;
	private Toolbar bar;

	@Override
	protected boolean isStatusBarColorWhite() {
		return true;
	}

	@Override
	protected int getLayoutResID() {
		return R.layout.activity_pic;
	}
	@Override
	protected void initBar() {
		index = getIntent().getIntExtra("index", 0);
		bar = findViewById(R.id.toolbar);
		actHelper.setToolbarTitleCenter(bar, "查看图片");
		setSupportActionBar(bar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	@Override
	protected void initUI(Bundle savedInstanceState) {
		list = getIntent().getStringArrayListExtra("Urls");
		pager =  findViewById(R.id.pager);
		des =  findViewById(R.id.dec);
		scrollView=findViewById(R.id.scrollView);
		fralist = new ArrayList<Fragment>();
		addpager();
		adapter = new FragmentAdapter(getSupportFragmentManager(), fralist);
		pager.setAdapter(adapter);
		change(index);
	}
	@Override
	protected void addEvent() {
		bar.setNavigationOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		pager.addOnPageChangeListener(listener);
	}
	private int len;
	private void addpager(){
		len = list==null?0:list.size();
		for(int i=0;i<len;i++){
			fralist.add(PicFragment.getInstance(list.get(i)));
		}
	}
	
	private void change(int index){
		if(len==1){
			des.setVisibility(View.GONE);
		}else{
			des.setVisibility(View.VISIBLE);
			des.setText((index+1)+"/"+len);
		}
		pager.setCurrentItem(index, true);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private OnPageChangeListener listener = new OnPageChangeListener() {
		
		@Override
		public void onPageSelected(int arg0) {
			change(arg0);
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}
	};

}
