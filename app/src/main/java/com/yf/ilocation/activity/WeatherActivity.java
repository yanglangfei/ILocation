package com.yf.ilocation.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.yf.ilocation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */

public class WeatherActivity extends Activity implements OnGetGeoCoderResultListener, View.OnClickListener {
    private  double lat;
    private  double lon;
    private TextView tv_ad,tv_state,tv_wind,tv_temp,tv_wr,tv_remark,day1,day1_wea,nightWea,day2,day2_wea,nightWea2,day3,day3_wea,nightWea3;
    private RelativeLayout bg;
    private  String getWeather="http://op.juhe.cn/onebox/weather/query";
    private List<TextView> days=new ArrayList<>();
    private  List <TextView> dayWeath=new ArrayList<>();
    private  List<TextView> nightArray=new ArrayList<>();
    private ImageView iv_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_weather);
        initView();
    }

    private void initView() {
            iv_finish= (ImageView) findViewById(R.id.iv_finish);
            iv_finish.setOnClickListener(this);
        days.clear();
        dayWeath.clear();
        nightArray.clear();
        lat=getIntent().getDoubleExtra("lat",0);
        lon=getIntent().getDoubleExtra("lon",0);
        if(lat==0||lon==0){
            Toast.makeText(this, "无法获取位置", Toast.LENGTH_SHORT).show();
            return;
        }
        GeoCoder code=GeoCoder.newInstance();
        code.setOnGetGeoCodeResultListener(this);
        bg= (RelativeLayout) findViewById(R.id.bg);
        tv_ad= (TextView) findViewById(R.id.tv_ad);
        tv_state= (TextView) findViewById(R.id.tv_state);
        tv_wind= (TextView) findViewById(R.id.tv_wind);
        tv_temp= (TextView) findViewById(R.id.tv_temp);
        tv_wr= (TextView) findViewById(R.id.tv_wr);
        tv_remark= (TextView) findViewById(R.id.tv_remark);
        day1= (TextView) findViewById(R.id.day1);
        day1_wea= (TextView) findViewById(R.id.day1_wea);
        nightWea= (TextView) findViewById(R.id.nightWea1);
        day2= (TextView) findViewById(R.id.day2);
        day2_wea= (TextView) findViewById(R.id.day2_wea);
        nightWea2= (TextView) findViewById(R.id.nightWea2);
        day3= (TextView) findViewById(R.id.day3);
        day3_wea= (TextView) findViewById(R.id.day3_wea);
        nightWea3= (TextView) findViewById(R.id.nightWea3);
        nightArray.add(nightWea);
        nightArray.add(nightWea2);
        nightArray.add(nightWea3);
        dayWeath.add(day1_wea);
        dayWeath.add(day2_wea);
        dayWeath.add(day3_wea);
        days.add(day1);
        days.add(day2);
        days.add(day3);
        ReverseGeoCodeOption rever=new ReverseGeoCodeOption().location(new LatLng(lat,lon));
        code.reverseGeoCode(rever);
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            //没有找到检索结果
            return;
        }
        String address = reverseGeoCodeResult.getAddress();
        String city=address.split("市")[0];
        tv_ad.setText(city);
        initWeatherData(city);

    }

    private void initWeatherData(String city) {
        RequestParams param=new RequestParams(getWeather);
        param.addParameter("cityname",city);
        param.addParameter("key","26c63618167a8768db318e1c0a10c9d7");
        x.http().get(param, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
               if(result!=null){
                   try {
                       JSONObject object=new JSONObject(result);
                       int error_code=object.optInt("error_code");
                       if(error_code==0){
                           JSONObject resultObj=object.getJSONObject("result");
                           JSONObject data=resultObj.getJSONObject("data");
                           JSONObject realtime=data.getJSONObject("realtime");
                           JSONObject weather=realtime.getJSONObject("weather");
                           JSONObject pm25=data.getJSONObject("pm25");
                           JSONObject pm25O=pm25.optJSONObject("pm25");
                           String  dese=pm25O.optString("des");
                           String quality=pm25O.optString("quality");
                           JSONObject wind=realtime.getJSONObject("wind");
                           String img=weather.optString("img");
                           String info=weather.optString("info");
                           String direct=wind.optString("direct");
                           String power=wind.optString("power");
                           String temperature=weather.optString("temperature");
                           setTodayBg(img);
                           tv_state.setText(info);
                           tv_wind.setText(direct+"  "+power);
                           tv_temp.setText(temperature+"度");
                           tv_wr.setText("污染指数  "+quality);
                           tv_remark.setText(dese);
                           JSONArray weatherData=data.getJSONArray("weather");
                           for (int i = 0; i < weatherData.length(); i++) {
                               JSONObject obj=weatherData.getJSONObject(i);
                               JSONObject infoObj=obj.getJSONObject("info");
                               JSONArray day=infoObj.getJSONArray("day");
                               JSONArray night=infoObj.getJSONArray("night");
                               String date=obj.optString("date");
                               days.get(i).setText(date);
                               dayWeath.get(i).setText("白天  "+day.toString().split(",")[1]);
                               nightArray.get(i).setText("晚上   "+night.toString().split(",")[1]);
                           }
                       }
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setTodayBg(String img) {
        if(img.equals("0")){
            //晴朗
            bg.setBackground(ContextCompat.getDrawable(this,R.drawable.qing_bg));
        }else if(img.equals("1")){
            //多云
            bg.setBackground(ContextCompat.getDrawable(this,R.drawable.duoyun_bg));
        }else if(img.equals("2")){
            //阴天
            bg.setBackground(ContextCompat.getDrawable(this,R.drawable.yin_zuidiceng));
        }else  if(img.equals("3")||img.equals("4")||img.equals("5")||img.equals("6")||img.equals("7")||img.equals("8")){
            bg.setBackground(ContextCompat.getDrawable(this,R.drawable.yu_bg));
        }else if(img.equals("18")){
            bg.setBackground(ContextCompat.getDrawable(this,R.drawable.wu_bg));
        }else  if(img.equals("14")||img.equals("15")||img.equals("16")){
            bg.setBackground(ContextCompat.getDrawable(this,R.drawable.xue_bg));
        }else {
            bg.setBackground(ContextCompat.getDrawable(this,R.drawable.qing_bg));
        }
    }

    private void initWeatherData() {

    }

    @Override
    public void onClick(View view) {
        this.finish();
    }
}
