package com.yf.ilocation.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.yf.ilocation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */

public class MyRoound extends Activity {
    private GridView gv_round;
    private List<String> wheres=new ArrayList<>();
    private RoundAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.ui_round);
        initView();
        initData();
    }

    private void initData() {
        wheres.clear();
        wheres.add("公园");
        wheres.add("美食");
        wheres.add("银行");
        wheres.add("KTV");
        wheres.add("公交");
        wheres.add("旅游");
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        gv_round= (GridView) findViewById(R.id.gv_round);
        adapter=new RoundAdapter();
        gv_round.setAdapter(adapter);
    }


    class  RoundAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return wheres.size();
        }

        @Override
        public Object getItem(int i) {
            return wheres.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView =new TextView(MyRoound.this);
            textView.setText(wheres.get(i));
            textView.setTextColor(Color.GREEN);
            textView.setHeight(200);
            textView.setGravity(Gravity.CENTER);
            return textView;
        }
    }
}
