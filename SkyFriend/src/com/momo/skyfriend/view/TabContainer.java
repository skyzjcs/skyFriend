package com.momo.skyfriend.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import android.widget.LinearLayout;

/**
 * 自定义选项卡
 */
public class TabContainer extends LinearLayout {
	private int mSelectedTextColorId,mUnSelectedTextColorId;//选择和非选文字颜色
	private List<TabItem> tabs=new ArrayList<TabItem>();
	
	@SuppressLint("NewApi")
	public TabContainer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public TabContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TabContainer(Context context) {
		super(context);
		init();
	}

	private void init() {
		setOrientation(HORIZONTAL);
	}
	
	public TabContainer addItem(int iconResId,int iconResIdUnchecked,String text){
		TabItem tabItemView=new TabItem(getContext(), iconResId, iconResIdUnchecked,text);
		tabItemView.setSelectedTextColor(mSelectedTextColorId, mUnSelectedTextColorId);
		tabItemView.setTag(getChildCount());//返回ViewGroup中的child数量
		tabItemView.setClickable(true);
		tabItemView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(listener!=null){
					setSelection((Integer)(v.getTag()));
					listener.onChecked((Integer)(v.getTag()), v);
				}
			}
		});
		tabs.add(tabItemView);
		addView(tabItemView);
		return this;
	}
	
	public TabContainer addItem(int iconResId,int iconResIdUnchecked,int textId){
		return	addItem(iconResId, iconResIdUnchecked, getResources().getString(textId));
	}
	
	/**
	 * 设置初始位置
	 * @param position
	 */
	public void setSelection(int position){
		for(int i=0;i<tabs.size();i++){
			if(i==position){
				tabs.get(i).setChecked(true);
			}else{
				tabs.get(i).setChecked(false);
			}
		}
	}
	
	public void setSelectedTextColor(int selId,int unselId){
		mSelectedTextColorId=selId;
		mUnSelectedTextColorId=unselId;
	}
	
	//定义回调接口
	public  interface OnCheckedListener{
		public void onChecked(int position,View v);
	}
	
	private OnCheckedListener listener;
	
	public void setOnCheckedListener(OnCheckedListener listener){
		this.listener=listener;
	}
}
