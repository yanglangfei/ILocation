package com.yf.ilocation.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
 * Created by Administrator on 2016/10/10.
 */

public class Main extends Activity {
    private MapView mapView;
    private BaiduMap map;
    private LocationClient client;
    private BDLocationListener listener=new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            int type = bdLocation.getLocType();
            if (type == BDLocation.TypeNetWorkLocation) {
                String pro = bdLocation.getProvince();
                String city = bdLocation.getCity();
                String adree = bdLocation.getAddrStr();
                float direct = bdLocation.getDirection();
                String dis = bdLocation.getDistrict();
                String floor = bdLocation.getFloor();
                Log.i("111", "loc:" + pro + "   " + city + "  " + adree + "   " + direct + "   " + dis + "   " + floor);
                double lat = bdLocation.getLatitude();
                double lon = bdLocation.getLongitude();
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

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.ui_location);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    private void initView() {
        mapView= (MapView) findViewById(R.id.mapView);
        map=mapView.getMap();
        map.setTrafficEnabled(true);
        map.showMapPoi(true);
        client=new LocationClient(getApplicationContext());
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
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
