package com.momo.skyfriend.adapter;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageRequest;
import com.example.skyfriend.R;
import com.momo.skyfriend.util.StringUtil;
import com.momo.skyfriend.util.VolleyUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;
import android.widget.Spinner;
/**
 * 请求图片的图片适配器
 * @author sky
 *
 */
public class ImageRequestAdapter extends ImageBaseAdapter {
	private Context context;

	public ImageRequestAdapter(Context context, String[] imageUrlArray) {
		super(context, imageUrlArray);
		this.context=context;
	}

	@Override
	int getItemLayout() {
		return R.layout.belle_image;
	}

	@Override
	void setImage(final ImageView imageView, String imageUrl) {
		
		//设置空图片
		imageView.setImageResource(R.drawable.ic_empty);
		
		//取消这个ImageView已有的请求
		VolleyUtil.getQueue(context).cancelAll(imageView);
		
		ImageRequest request=new ImageRequest(StringUtil.preUrl(imageUrl), new Listener<Bitmap>() {

			@Override
			public void onResponse(Bitmap bitmap) {
				imageView.setImageBitmap(bitmap);
			}
		}, 0, 0, Config.RGB_565, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				imageView.setImageResource(R.drawable.ic_empty);
			}
		});
		
		request.setTag(imageView);
		
		VolleyUtil.getQueue(this.context).add(request);
		
	}

}
