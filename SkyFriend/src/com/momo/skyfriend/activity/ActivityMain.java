package com.momo.skyfriend.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

import com.example.skyfriend.R;
import com.momo.skyfriend.fragment.BelleFragment;
import com.momo.skyfriend.view.TabContainer;
import com.momo.skyfriend.view.TabContainer.OnCheckedListener;

public class ActivityMain extends ActivityBase implements OnPageChangeListener{
	private List<Fragment> mFragments=new ArrayList<Fragment>();
	private ViewPager mViewPager;
	private TabContainer mTabContainer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initFragments();
		initTabs();
	}

	/**
	 * 初始化选项卡 
	 */
	@SuppressLint("ResourceAsColor")
	private void initTabs() {
		mTabContainer.setSelectedTextColor(R.color.sea_blue, R.color.gray_textcolor);
		mTabContainer.addItem(R.drawable.home_unsel, R.drawable.home_sel, "美女库")
			.addItem(R.drawable.community_unsel, R.drawable.community_sel, "机器人")
			.addItem(R.drawable.me_unsel, R.drawable.me_sel, "脸谱");
		mTabContainer.setBackgroundResource(R.color.tab_gray_bg);
		mTabContainer.setSelection(0);
		mTabContainer.setOnCheckedListener(new OnCheckedListener() {
			@Override
			public void onChecked(int position, View v) {
				mViewPager.setCurrentItem(position,false);
			}
		});
	}

	private void initFragments() {
		mViewPager=(ViewPager) findViewById(R.id.viewPager);
		mFragments.add(new BelleFragment());
		mViewPager.setAdapter(new MPagerAdapter());
		mViewPager.setCurrentItem(0,false);
		mViewPager.setOnPageChangeListener(this);
		mTabContainer=(TabContainer) findViewById(R.id.tabContainer);
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
