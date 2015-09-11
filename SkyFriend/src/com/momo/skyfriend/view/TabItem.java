package com.momo.skyfriend.view;

import com.example.skyfriend.R;
import com.momo.skyfriend.util.StringUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 自定义选项卡内部元素
 * @author yangze
 *
 */
public class TabItem extends LinearLayout {
	private ImageView iv_icon;//图标
	private TextView tv_text;//图标文字
	private String mText;
	private TextView tv_msg;//信息
	private String mMsgCount;
	private int mIconResId,mTextId,mIconResIdChecked;//图片id,文字id,图片是否检查
	private int mSelectedTextColorId,mUnSelectedTextColorId;//选择和非选文字颜色

	public TabItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public TabItem(Context context,int iconResId, int iconResIdChecked, String text){
		super(context);
		mIconResId=iconResId;
		mIconResIdChecked=iconResIdChecked;
		mText=text;
		init();
	}
	
	/**
	 * 初始化TabItem(底部图标小按钮)
	 */
	private void init() {
		setOrientation(VERTICAL);
		LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,1);//这里不设置权重可不行啊！！！
		setLayoutParams(params);
		ViewGroup vg=(ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.tab_item_view, null);
		iv_icon=(ImageView) vg.findViewById(R.id.icon);
		tv_text=(TextView)vg.findViewById(R.id.text);
		tv_msg=(TextView)vg.findViewById(R.id.msg);
		//根据情况设置tab_item的布局
		if(mIconResId!=0){
			iv_icon.setImageResource(mIconResId);
		}
		if(!StringUtil.isEmpty(mText)){//代码设置
			tv_text.setText(mText);
		}else{	//从布局文件中拿
			tv_text.setText(getResources().getString(mTextId));
		}
		if(StringUtil.isEmpty(mMsgCount)){
			tv_msg.setVisibility(View.GONE);
		}else{
			tv_msg.setVisibility(View.VISIBLE);
		}
		addView(vg);
	}

	//get、set方法
	public int getIconResId() {
		return mIconResId;
	}

	public void setIconResId(int mIconResId) {
		this.mIconResId = mIconResId;
	}

	public String getText() {
		return mText;
	}

	public void setText(String mText) {
		this.mText = mText;
	}
	
	public void setText(int id){
		this.mTextId=id;
	}

	public String getMsgCount() {
		return mMsgCount;
	}
	
	public void setMsgCount(String mMsgCount){
		this.mMsgCount=mMsgCount;
		if(StringUtil.isEmpty(mMsgCount)||"0".equals(mMsgCount)){
			tv_msg.setVisibility(View.GONE);
		}else{
			tv_msg.setVisibility(View.VISIBLE);
			tv_msg.setText(mMsgCount);
		}
	}
	
	/**
	 * 设置选择和非选字体颜色
	 */
	public void setSelectedTextColor(int selId, int unselId){
		mSelectedTextColorId=selId;
		mUnSelectedTextColorId=unselId;
	}
	
	/**
	 * 设置该item是否选中的样式
	 * @param checked
	 */
	public void setChecked(boolean checked){
		if(checked){
			iv_icon.setImageResource(mIconResIdChecked);
			tv_text.setTextColor(getResources().getColor(mSelectedTextColorId));
		}
	}
}
