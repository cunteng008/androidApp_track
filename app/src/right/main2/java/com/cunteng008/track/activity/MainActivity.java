package com.cunteng008.track.activity;


import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.cunteng008.track.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends AppCompatActivity {

    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    //定位SDK的核心类
    private LocationClient mLocationClient = null;
    //监听器，用于监听位置是否有刷新
    private BDLocationListener mListener = new MyLocationListener();
    private boolean mIsFirstLocate = true;

    private double mMyLatitude;
    private double mMyLongitude;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //显示模式，普通显示
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //定位器
        mLocationClient = new LocationClient(getApplicationContext());
        //定位器的相关设置
        initLocation();
        //安装监听器
        mLocationClient.registerLocationListener(mListener);
        //开始定位
        mLocationClient.start();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        //释放控制器
        mBaiduMap.setMyLocationEnabled(false);

        //释放监听器
        if (mLocationClient != null)
            mLocationClient.unRegisterLocationListener(mListener);

        //释放定位器
        mLocationClient.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        super.onPause();
        mMapView.onPause();
    }

    private void init() {
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        //定位器
        mLocationClient = new LocationClient(getApplicationContext());
    }


    private void initLocation() {
        LocationClientOption option = new LocationClientOption();

        //默认高精度（GPS+NETWORK)，设置定位模式：GPS+NETWORK,GPS,NETWORK
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //默认gcj02，设置返回的定位结果坐标系,这个是百度的坐标系
        option.setCoorType("bd09ll");
        //即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的,即1秒定位一次
        option.setScanSpan(1000);
        //设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //设置是否使用gps
        option.setOpenGps(true);
        //设置是否当GPS有效时按照1S/1次频率输出GPS结果，即1秒刷新一次画面
        option.setLocationNotify(true);
        //设置是否需要位置语义化结果,BDLocation.getLocationDescribe
        option.setIsNeedLocationDescribe(true);
        //设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);
        //定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);
        //设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);
        //设置是否需要过滤GPS仿真结果，默认需要
        option.setEnableSimulateGps(false);

        mLocationClient.setLocOption(option);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient.connect();
        AppIndex.AppIndexApi.start(mClient, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(mClient, getIndexApiAction());
        mClient.disconnect();
    }

    //监听器，接收位置
    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //监听实时位置

            // mapView 销毁后不再处理新接收的位置
            if (location == null || mMapView == null)
                return;

            //获取位置，并改变地图的显示
            //获取精度半径，方向（0-360），，纬度，经度
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            //更新地图中心到定位的位置
            mBaiduMap.setMyLocationData(locData);    //设置定位数据

            //第一次定位,暂时不知道有什么用
            if (mIsFirstLocate) {
                mIsFirstLocate = false;

                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll, 16);   //设置地图中心点以及缩放级别
                mBaiduMap.animateMapStatus(u);
            }
        }
    }

    private void setMark() {
        //定义Maker坐标点
        LatLng point = new LatLng(mMyLatitude, mMyLongitude);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }


}
