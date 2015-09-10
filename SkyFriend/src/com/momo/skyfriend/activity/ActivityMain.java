package com.momo.skyfriend.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.example.skyfriend.R;
import com.momo.skyfriend.fragment.BelleFragment;

public class ActivityMain extends ActivityBase implements OnPageChangeListener{
	private List<Fragment> mFragments=new ArrayList<Fragment>();
	private ViewPager mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initFragments();
	}

	private void initFragments() {
		mViewPager=(ViewPager) findViewById(R.id.viewPager);
		mFragments.add(new BelleFragment());
		mViewPager.setAdapter(new MPagerAdapter());
		mViewPager.setCurrentItem(0,false);
		mViewPager.setOnPageChangeListener(this);
	}

	/**
	 * 自定义ViewPager适配器
	 * @author caiweijian
	 */
	public class MPagerAdapter extends FragmentStatePagerAdapter {

        public MPagerAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		setFragmentUserVisibleHint(position);
	}

	private void setFragmentUserVisibleHint(int position) {
		for(int i=0;i<mFragments.size();i++){
			if(i==position){
				mFragments.get(i).setUserVisibleHint(true);
			}else{
				mFragments.get(i).setUserVisibleHint(false);
			}
		}
	}
}
