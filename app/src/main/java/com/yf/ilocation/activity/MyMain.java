package com.yf.ilocation.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.yf.ilocation.R;
import com.yf.ilocation.fragment.F1;
import com.yf.ilocation.fragment.F2;
import com.yf.ilocation.fragment.F3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */

public class MyMain extends FragmentActivity {

    private RadioGroup group;
    private ViewPager vp;
    private F1 f1;
    private F2 f2;
    private F3 f3;
    private List<Fragment> fragments=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
    }

    private void initView() {
        group=(RadioGroup)findViewById(R.id.group);
        vp=(ViewPager)findViewById(R.id.vp);
        f1=new F1();
        f2=new F2();
        f3=new F3();
        fragments.add(f1);
        fragments.add(f2);
        fragments.add(f3);
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

    }
}
