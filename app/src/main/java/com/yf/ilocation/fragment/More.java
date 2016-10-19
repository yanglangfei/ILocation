package com.yf.ilocation.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yf.ilocation.R;
import com.yf.ilocation.activity.JieMengActivcity;
import com.yf.ilocation.activity.WeatherActivity;
import com.yf.ilocation.activity.XingZuoActivity;

/**
 * Created by Administrator on 2016/10/13.
 */

public class More extends Fragment implements View.OnClickListener {
    private View view;
    private RelativeLayout lay_weather;
    private RelativeLayout lay_xz;
    private  RelativeLayout lay_jm;
    private SharedPreferences sp;
    private double fromLat;
    private double fromLon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.ui_more,container,false);
        initView();
        return view;
    }

    private void initView() {
        sp=getActivity().getSharedPreferences("locationInfo", Context.MODE_PRIVATE);
        String lat = sp.getString("lat", "");
        String lon = sp.getString("lon", "");
        if(lat.length()>0){
            fromLat=Double.valueOf(lat);
        }
        if(lon.length()>0){
            fromLon=Double.valueOf(lon);
        }
        lay_weather=(RelativeLayout)view.findViewById(R.id.lay_weather);
        lay_xz=(RelativeLayout)view.findViewById(R.id.lay_xz);
        lay_jm= (RelativeLayout) view.findViewById(R.id.lay_jm);
        lay_weather.setOnClickListener(this);
        lay_xz.setOnClickListener(this);
        lay_jm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lay_weather:
               Intent weather= new Intent(getActivity(), WeatherActivity.class);
                weather.putExtra("lat",fromLat);
                weather.putExtra("lon",fromLon);
                startActivity(weather);
                break;
            case R.id.lay_jm:
                Intent jieMeng=new Intent(getActivity(), JieMengActivcity.class);
                startActivity(jieMeng);
                break;
            case R.id.lay_xz:
                Intent xingZuo=new Intent(getActivity(), XingZuoActivity.class);
                startActivity(xingZuo);
                break;
            default:
                break;
        }

    }
}
