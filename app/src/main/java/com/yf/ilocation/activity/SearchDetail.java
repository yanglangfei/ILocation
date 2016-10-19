package com.yf.ilocation.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.yf.ilocation.R;
import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */

public class SearchDetail extends Activity implements OnGetRoutePlanResultListener, View.OnClickListener {
    private  double tagLon;
    private  double tagLat;
    private  double fromLat;
    private  double fromLon;
    private MapView searchMap;
    private BaiduMap map;
    private TransitRoutePlanOption plan;
    private ImageView iv_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(this);
        setContentView(R.layout.ui_detail);
        initView();
    }

    private void initView() {
            iv_finish= (ImageView) findViewById(R.id.iv_finish);
            iv_finish.setOnClickListener(this);
        searchMap= (MapView) findViewById(R.id.searchMap);
        map= searchMap.getMap();
        tagLat=getIntent().getDoubleExtra("tolat",0);
        tagLon=getIntent().getDoubleExtra("tolon",0);
        fromLat=getIntent().getDoubleExtra("fromlat",0);
        fromLon=getIntent().getDoubleExtra("fromlon",0);
        LatLng latLng = new LatLng(fromLat, fromLon);
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


        //构建Marker图标
        LatLng tag = new LatLng(tagLat, tagLon);
        BitmapDescriptor bm= BitmapDescriptorFactory
                .fromResource(R.drawable.local);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions opt= new MarkerOptions()
                .position(tag)
                .icon(bm);
        //在地图上添加Marker，并显示
        map.addOverlay(opt);

       RoutePlanSearch search=RoutePlanSearch.newInstance();
        PlanNode from=PlanNode.withLocation(new LatLng(fromLat,fromLon));
        PlanNode to=PlanNode.withLocation(new LatLng(tagLat,tagLon));
        plan=new TransitRoutePlanOption().from(from).to(to).city("上海").policy(TransitRoutePlanOption.TransitPolicy.EBUS_TRANSFER_FIRST);
        search.setOnGetRoutePlanResultListener(this);
        search.transitSearch(plan);
    }



    @Override
    protected void onResume() {
        super.onResume();
        searchMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        searchMap.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        searchMap.onDestroy();
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
        if (transitRouteResult == null || transitRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            //未找到结果
            return;
        }
        if (transitRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            //起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            //result.getSuggestAddrInfo()
            return;
        }


        if(transitRouteResult!=null&&transitRouteResult.getRouteLines().size()>0) {
            for (int i = 0; i < transitRouteResult.getRouteLines().size(); i++) {
                TransitRouteLine trans = transitRouteResult.getRouteLines().get(i);
                List<TransitRouteLine.TransitStep> steps = trans.getAllStep();
                if (steps != null) {
                    for (int i1 = 0; i1 < steps.size(); i1++) {
                        TransitRouteLine.TransitStep step = steps.get(i1);
                        String d = step.getInstructions();
                        Log.i("111", "d:" + d);
                    }
                }
            }
        }

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

    @Override
    public void onClick(View view) {
        this.finish();
    }
}
