package com.momo.skyfriend.fragment;

import com.example.skyfriend.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FaceFragment extends FragmentBase {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup v=(ViewGroup) inflater.inflate(R.layout.sf_face, null);
		return v;
	}
	
}
