package com.momo.skyfriend.fragment;

import com.example.skyfriend.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener{
	private ImageView iv_belle;
	private ImageView iv_robot;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sf_main);
		initView();
	}

	private void initView() {
		iv_belle=(ImageView) findViewById(R.id.iv_belle);
		iv_robot=(ImageView) findViewById(R.id.iv_robot);
		
		iv_belle.setOnClickListener(this);
		iv_robot.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_belle:
			startActivity(new Intent(this,BelleActivity.class));
			break;
		
		case R.id.iv_robot:
			startActivity(new Intent(this,RobotActivity.class));
			break;

		default:
			break;
		}
		
	}


}
