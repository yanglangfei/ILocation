package com.yf.ilocation.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/10/13.
 */

public class F2_2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView=new TextView(getActivity());
        textView.setBackgroundColor(Color.RED);
        textView.setText("Fragment2-2");
        textView.setTextSize(30);

        return textView;
    }


    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity(), "可见", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getActivity(), "不可见", Toast.LENGTH_SHORT).show();
    }

}
