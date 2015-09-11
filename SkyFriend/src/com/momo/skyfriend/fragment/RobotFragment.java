package com.momo.skyfriend.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.skyfriend.R;
import com.momo.skyfriend.adapter.RobotChatAdapter;
import com.momo.skyfriend.util.GetPostUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class RobotFragment extends FragmentBase {
	public int OTHER=1;
    public int ME=0;
    
    protected ArrayList<HashMap<String, Object>> chatList=null;
    String[] from={"image","text"};
    int[] to={R.id.robot_image_me,R.id.robot_text_me,R.id.robot_image_other,R.id.robot_text_other};
    int[] layout={R.layout.msg_left,R.layout.msg_right};
    
    protected ListView robotListView;
    protected Button btn_send;
    protected EditText editText;
    
    protected RobotChatAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.sf_robot, null);
		
		btn_send=(Button) v.findViewById(R.id.robot_bottom_sendbutton);
		editText=(EditText) v.findViewById(R.id.robot_bottom_edittext);
		robotListView=(ListView) v.findViewById(R.id.robot_list);
		chatList=new ArrayList<HashMap<String, Object>>();
		addTextToList("不管你是谁", ME);
		addTextToList("我不想回答", OTHER);
		
		adapter=new RobotChatAdapter(getActivity(), chatList, layout, to, from);
		btn_send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String myWord=null;
				/**
                 * 这是一个发送消息的监听器，注意如果文本框中没有内容，那么getText()的返回值可能为
                 * null，这时调用toString()会有异常！所以这里必须在后面加上一个""隐式转换成String实例
                 * ，并且不能发送空消息。
                 */
				myWord=(editText.getText()+"").toString();
				if(myWord.length()==0) return;
				editText.setText("");
				addTextToList(myWord, ME);
				/**
                 * 更新数据列表，并且通过setSelection方法使ListView始终滚动在最底端
                 */
                adapter.notifyDataSetChanged();
                robotListView.setSelection(chatList.size()-1);
			}
		});
		robotListView.setAdapter(adapter);
//		getRobot();
		return v;
	}

	protected void addTextToList(String text,int who) {
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("person", who);
		map.put("image", who==ME?R.drawable.mine_head:R.drawable.mine_head);
		map.put("text", text);
		chatList.add(map);
	}
	
	//根据地址返回json字符串
	private void getRobot() {
	    try {
	    	String APIKEY = "a5b6f341d7981afc732dec4b3cb00379"; 
			String INFO = URLEncoder.encode("北京今日天气", "utf-8");
			String getURL = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + INFO;
Log.i("lala", "getURL:"+getURL);			
			String a=GetPostUtil.sendGet(getURL);
Log.i("lala", "打印的json:"+a);
	    } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
	}
}
