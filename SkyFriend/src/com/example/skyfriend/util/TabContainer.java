package com.example.skyfriend.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import android.widget.LinearLayout;

/**
 * 自定义选项卡
 */
public class TabContainer extends LinearLayout {

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
		
		
		return this;
	}
}
