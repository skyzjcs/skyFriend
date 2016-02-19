package com.momo.skyfriend.fragment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.skyfriend.R;
import com.momo.skyfriend.adapter.RobotChatAdapter;
import com.momo.skyfriend.util.GetPostUtil;

public class RobotFragment extends FragmentBase implements OnClickListener{
	public static final int TAKE_PHOTO=1;//拍照片
	public static final int CROP_PHOTO=2;//裁剪照片
	private String fileName;  
	private Context mcontext;
	protected ListView robotListView;
	protected Button btn_send;
	protected EditText editText;
	protected ImageView ivTakePhoto;
	private Uri imageUri;
	
	public int OTHER = 1;
	public int ME = 0;

	protected ArrayList<HashMap<String, Object>> chatList = null;
	String[] from = { "image", "text" };
	int[] to = { R.id.robot_image_me, R.id.robot_text_me,
			R.id.robot_image_other, R.id.robot_text_other };
	int[] layout = { R.layout.msg_left, R.layout.msg_right };

	protected RobotChatAdapter adapter;
	
	private Handler h1 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			if(msg.what==1){
				JSONObject jo;
				try {
					jo = new JSONObject(msg.obj.toString());
					addTextToList(jo.get("text").toString(), OTHER);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				adapter.notifyDataSetChanged();
				robotListView.setSelection(chatList.size() - 1);
			}
		}
		
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.sf_robot, null);
		mcontext=getActivity();

		btn_send = (Button) v.findViewById(R.id.robot_bottom_sendbutton);
		editText = (EditText) v.findViewById(R.id.robot_bottom_edittext);
		robotListView = (ListView) v.findViewById(R.id.robot_list);
		ivTakePhoto=(ImageView) v.findViewById(R.id.iv_takephoto);
		
		chatList = new ArrayList<HashMap<String, Object>>();
		adapter = new RobotChatAdapter(getActivity(), chatList, layout, to,
				from);
		btn_send.setOnClickListener(this);
		robotListView.setAdapter(adapter);
		
		ivTakePhoto.setOnClickListener(this);
		
		return v;
	}

	/**
	 * 将内容根据ME、OTHER加入map链表
	 * @param text
	 * @param who
	 */
	protected void addTextToList(String text, int who) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("person", who);
		map.put("image", who == ME ? R.drawable.mine_head
				: R.drawable.mine_head);
		map.put("text", text);
		chatList.add(map);
	}

	/** 根据地址返回json字符串
	 * @param content
	 */
	private void getRobot(final String content) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String APIKEY = "a8d10500e5d25b0df9c7cabb4046d8f8";
					String INFO;
					INFO = URLEncoder.encode(content, "utf-8");
					String getURL = "http://www.tuling123.com/openapi/api?key="
							+ APIKEY + "&info=" + INFO;
					Log.i("lala", "getURL:" + getURL);
					String a = GetPostUtil.sendGet(getURL);
					Log.i("lala", "打印的json:" + a);
					Message msg=new Message();
					msg.what=1;
					msg.obj=a;
					h1.sendMessage(msg);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.robot_bottom_sendbutton:
			String myWord = null;
			/**
			 * 这是一个发送消息的监听器，注意如果文本框中没有内容，那么getText()的返回值可能为
			 * null，这时调用toString()会有异常！所以这里必须在后面加上一个""隐式转换成String实例
			 * ，并且不能发送空消息。
			 */
			myWord = (editText.getText() + "").toString();
			if (myWord.length() == 0)
				return;
			
			addTextToList(myWord, ME);
			//发送请求
			getRobot(editText.getText().toString());
			/**
			 * 更新数据列表，并且通过setSelection方法使ListView始终滚动在最底端
			 */
			editText.setText("");
			adapter.notifyDataSetChanged();
			robotListView.setSelection(chatList.size() - 1);
			break;
		case R.id.iv_takephoto:
			File outputImage = new File(Environment.getExternalStorageDirectory(),"tempImage.jpg");
			try {
				if(outputImage.exists()){
					outputImage.delete();
				}
				outputImage.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			imageUri=Uri.fromFile(outputImage);
			Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(intent, TAKE_PHOTO);
			break;

		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case TAKE_PHOTO:
			if(resultCode == getActivity().RESULT_OK){
				Intent intent=new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(imageUri, "image/*");
				intent.putExtra("scale", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, CROP_PHOTO);
			}
			break;
		case CROP_PHOTO:
			if(resultCode == getActivity().RESULT_OK){
				try {
					Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
					SavePicInLocal(bitmap);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			break;

		default:
			break;
		}
	}
	
	// 保存拍摄的照片到手机的sd卡  
    private void SavePicInLocal(Bitmap bitmap) {  
        FileOutputStream fos = null;  
        BufferedOutputStream bos = null;  
        ByteArrayOutputStream baos = null; // 字节数组输出流  
        try {  
            baos = new ByteArrayOutputStream();  
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);  
            byte[] byteArray = baos.toByteArray();// 字节数组输出流转换成字节数组  
            String saveDir = Environment.getExternalStorageDirectory()  
                    + "/liangPic";  
            File dir = new File(saveDir);  
            if (!dir.exists()) {  
                dir.mkdir(); // 创建文件夹  
            }  
            fileName = saveDir + "/" + System.currentTimeMillis() + ".PNG";  
            File file = new File(fileName);  
            file.delete();  
            if (!file.exists()) {  
                file.createNewFile();// 创建文件  
                Log.e("PicDir", file.getPath());  
                Toast.makeText(mcontext, fileName, Toast.LENGTH_LONG)  
                        .show();  
            }  
            // 将字节数组写入到刚创建的图片文件中  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(byteArray);  
  
        } catch (Exception e) {  
            e.printStackTrace();  
  
        } finally {  
            if (baos != null) {  
                try {  
                    baos.close();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        System.out.println(fileName+"成功");
    } 
}
