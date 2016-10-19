package com.yf.ilocation.fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.yf.ilocation.R;
import com.yf.ilocation.activity.SearchDetail;
import com.yf.ilocation.adapter.PAdapter;
import com.yf.ilocation.model.Place;
import com.yf.ilocation.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MyRound extends Fragment implements View.OnClickListener, OnGetPoiSearchResultListener {
    private View view;
    private PoiSearch search;
    private Button btn_search;
    private EditText et_keyword;
    private SharedPreferences sp;
    private Double lonD;
    private Double latD;
    private RecyclerView lv_search;
    private PAdapter adapter;
    private List<Place> places=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.ui_round, container, false);
        initView();
        return view;
    }

    private void initView() {
        lv_search= (RecyclerView) view.findViewById(R.id.lv_search);
        adapter=new PAdapter(places,getActivity());
        lv_search.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        lv_search.setAdapter(adapter);
        sp=getActivity().getSharedPreferences("locationInfo", Context.MODE_PRIVATE);
        String lon = sp.getString("lon", "");
        String lat = sp.getString("lat", "");
        if(lon.length()>0){
             lonD=Double.valueOf(lon);
        }
        if(lat.length()>0){
            latD=Double.valueOf(lat);
        }
        search=PoiSearch.newInstance();
        btn_search= (Button) view.findViewById(R.id.btn_search);
        et_keyword= (EditText) view.findViewById(R.id.et_keyword);
        search.setOnGetPoiSearchResultListener(this);
        btn_search.setOnClickListener(this);
        adapter.setOnItemClickListener(new PAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getActivity(), SearchDetail.class);
                intent.putExtra("tolat",places.get(position).getLocation().latitude);
                intent.putExtra("tolon",places.get(position).getLocation().longitude);
                intent.putExtra("fromlat",latD);
                intent.putExtra("fromlon",lonD);
                getActivity().startActivity(intent);
            }
        });
    }


    @Override
    public void onClick(View view) {
        places.clear();
        String text=et_keyword.getText().toString();
        if(text.length()<=0){
            Toast.makeText(getActivity(), "请输入关键词", Toast.LENGTH_SHORT).show();
            return;
        }
        if(latD==0.0||lonD==0.0){
            Toast.makeText(getActivity(), "无法获取当前位置", Toast.LENGTH_SHORT).show();
            return;
        }
        LatLng latObj=new LatLng(latD,lonD);
        PoiNearbySearchOption near=new PoiNearbySearchOption().location(latObj).keyword(text).radius(10000).pageNum(50);
        search.searchNearby(near);
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if(poiResult!=null){
            if(poiResult.getAllPoi()!=null){
                for (int i = 0; i < poiResult.getAllPoi().size(); i++) {
                    PoiInfo poi = poiResult.getAllPoi().get(i);
                    double distance = StringUtils.GetDistance(latD, lonD, poi.location.latitude, poi.location.longitude);
                    Place place=new Place();
                    place.setAddress(poi.address);
                    place.setTel(poi.phoneNum);
                    place.setName(poi.name);
                    place.setDistance(distance);
                    place.setLocation(poi.location);
                    places.add(place);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
    }
}
