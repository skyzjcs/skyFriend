package com.momo.skyfriend.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RobotChatAdapter extends BaseAdapter {
	public int OTHER=1;
    public int ME=0;
	Context context=null;
	ArrayList<HashMap<String, Object>> chatList=null;
	int[] layout;//因为这里有两种布局，所以装入数组中号根据情况取
	String[] from;
	int[] to;
	
	public RobotChatAdapter(Context context,
			ArrayList<HashMap<String, Object>> chatList, int[] layout,
			int[] to, String[] from) {
		super();
		this.context = context;
		this.chatList = chatList;
		this.layout = layout;
		this.to = to;
		this.from = from;
	}

	@Override
	public int getCount() {
		return chatList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder v=null;
		int who=(Integer) chatList.get(position).get("person");
		convertView=LayoutInflater.from(context).inflate(layout[who==ME?0:1], null);
		v=new ViewHolder();
		v.imageView=(ImageView) convertView.findViewById(to[who*2+0]);
		v.textView=(TextView)convertView.findViewById(to[who*2+1]);
        v.imageView.setBackgroundResource((Integer)chatList.get(position).get(from[0]));
        v.textView.setText(chatList.get(position).get(from[1]).toString());
		return convertView;
	}
	
	class ViewHolder{
		public ImageView imageView=null;
		public TextView textView=null;
	}

}
