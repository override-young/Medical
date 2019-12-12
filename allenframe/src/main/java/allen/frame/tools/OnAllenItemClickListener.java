package allen.frame.tools;

import android.view.View;

public abstract class OnAllenItemClickListener<T> {
	public void onItemClick(T e){};
	public void onItemClick(View v,T e,int position){};
}