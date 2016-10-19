package com.yf.ilocation.fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.yf.ilocation.R;
/**
 * Created by Administrator on 2016/10/13.
 */

public class MyLocation extends Fragment {
    private View view;
    private MapView mapView;
    private BaiduMap map;
    private SharedPreferences sp;
    private BDLocationListener listener=new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            int type = bdLocation.getLocType();
            if (type == BDLocation.TypeGpsLocation||type==BDLocation.TypeNetWorkLocation||type==BDLocation.TypeOffLineLocation) {
                double lat = bdLocation.getLatitude();
                double lon = bdLocation.getLongitude();
                edit.putString("lat",lat+"");
                edit.putString("lon",lon+"");
                edit.commit();
                LatLng latLng = new LatLng(lat, lon);
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
                map.animateMapStatus(update);

                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.now);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(latLng)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                map.addOverlay(option);
            }else  if(type==BDLocation.TypeServerError){
                Toast.makeText(getContext(), "服务端网络定位失败", Toast.LENGTH_SHORT).show();
            }else  if(type==BDLocation.TypeNetWorkException){
                Toast.makeText(getActivity(), "请检查网络是否通畅", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), "定位失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private LocationClient client;
    private SharedPreferences.Editor edit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SDKInitializer.initialize(getActivity().getApplicationContext());
        view=inflater.inflate(R.layout.ui_location,null);
        initView();
        return view;
    }

    private void initView() {
        mapView= (MapView) view.findViewById(R.id.mapView);
        sp=getActivity().getSharedPreferences("locationInfo", Context.MODE_PRIVATE);
        edit=sp.edit();
        map=mapView.getMap();
        map.setTrafficEnabled(true);
        map.showMapPoi(true);
        map.setIndoorEnable(true);
        client=new LocationClient(getActivity().getApplicationContext());
        initLocation();
        client.registerLocationListener(listener);
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=0;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        client.setLocOption(option);
        client.start();
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
