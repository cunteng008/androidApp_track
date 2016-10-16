package com.cunteng008.track.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.cunteng008.track.R;
import com.cunteng008.track.constant.Constant;
import com.cunteng008.track.constant.FileName;
import com.cunteng008.track.model.PersonalInfo;
import com.cunteng008.track.util.File;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    // 定位相关
    private LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;

    //百度地图
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private boolean mIsFirstLoc = true;

    //布局中的控件
    Button mRefurbishButton;
    Button mFriendsButton;
    Button mEenemiesButton;

    //数据
   public static ArrayList<PersonalInfo> mFriendInfoList = new ArrayList<>();
   public static ArrayList<PersonalInfo> mEnemyInfoList = new ArrayList<>();
    //我的位置
    public static BDLocation mMyLocation = new BDLocation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        mRefurbishButton = (Button) findViewById(R.id.refurbish_button);
        mRefurbishButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(MainActivity.this,"刷新",Toast.LENGTH_SHORT).show();

             addOverlay(mFriendInfoList,mEnemyInfoList);

                for(PersonalInfo info:mFriendInfoList){
                    String phone =info.getNum();
                    SmsManager manager = SmsManager.getDefault();
                    //因为一条短信有字数限制，因此要将长短信拆分
                    ArrayList<String> list = manager.divideMessage(Constant.ASK_LOCATION);
                    for(String text:list){
                        manager.sendTextMessage(phone, null, text, null, null);
                    }
                }
                //因为收到短信需要时间
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                addOverlay(mFriendInfoList,mEnemyInfoList);
            }
        });

        mFriendsButton = (Button) findViewById(R.id.m_friends_button);
        mFriendsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent mIntent = new Intent(MainActivity.this,FriendsActivity.class);
                startActivity(mIntent);
            }
        });

        mEenemiesButton = (Button) findViewById(R.id.m_enemies_btn);
        mEenemiesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent mIntent = new Intent(MainActivity.this,EnemiesActivity.class);
                startActivity(mIntent);
            }
        });

        init();
        mLocClient.registerLocationListener(myListener);
        mLocClient.start();
    }

    //定位的相关设置
    private void init(){

        //读取数据
        //可以避免null影响
       if( File.getObject(FileName.FRIEND,MainActivity.this)!=null){
          mFriendInfoList =
                  (ArrayList<PersonalInfo>) File.getObject(FileName.FRIEND,MainActivity.this);
        }
        if( File.getObject(FileName.ENEMY,MainActivity.this)!=null){
            mEnemyInfoList =
                    (ArrayList<PersonalInfo>) File.getObject(FileName.ENEMY,MainActivity.this);
        }

        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);

        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//默认高精度（GPS+NETWORK)，设置定位模式：GPS+NETWORK,GPS,NETWORK
        option.setCoorType("bd09ll");  //默认gcj02，设置返回的定位结果坐标系,这个是百度的坐标系
        option.setScanSpan(1000);//  可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的,即1秒定位一次
        option.setIsNeedAddress(true);//  可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果，即1秒刷新一次画面
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocClient.setLocOption(option);

    }

    //定位SDK监听函数
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

                //设置我始终位于中心
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                mMyLocation = location;
                MapStatus.Builder builder = new MapStatus.Builder();
                //图层设置为当前值
                builder.target(ll).zoom(mBaiduMap.getMapStatus().zoom);
               mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            if(mIsFirstLoc){
                addOverlay(mFriendInfoList,mEnemyInfoList);
                mIsFirstLoc = false;
            }
        }
        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    //显示marker
    private void addOverlay(ArrayList<PersonalInfo> friendInfoList,
                            ArrayList<PersonalInfo> enemyInfoList) {
        //清空地图
        mBaiduMap.clear();
        //创建marker的显示图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.friend_icon);
        LatLng latLng = null;
        Marker marker;
        OverlayOptions options;
        for(PersonalInfo info:friendInfoList){
            //获取经纬度
            latLng = new LatLng(info.getLatitude()+0.1,info.getLongitude()+0.1);
            //设置marker
            options = new MarkerOptions()
                    .position(latLng)//设置位置
                    .icon(bitmap)//设置图标样式
                    .zIndex(17) // 设置marker所在层级
                    .draggable(true); // 设置手势拖拽;
            //添加marker
            marker = (Marker) mBaiduMap.addOverlay(options);
            //使用marker携带info信息，当点击事件的时候可以通过marker获得info信息
            Bundle bundle = new Bundle();
            //info必须实现序列化接口
            bundle.putSerializable("info", info);
            marker.setExtraInfo(bundle);
        }
        //创建marker的显示图标
       bitmap = BitmapDescriptorFactory.fromResource(R.drawable.enemy_icon);
        for(PersonalInfo info:enemyInfoList){
            //获取经纬度
            latLng = new LatLng(info.getLatitude(),info.getLongitude());
            //设置marker
            options = new MarkerOptions()
                    .position(latLng)//设置位置
                    .icon(bitmap)//设置图标样式
                    .zIndex(17) // 设置marker所在层级
                    .draggable(true); // 设置手势拖拽;
            //添加marker
            marker = (Marker) mBaiduMap.addOverlay(options);
            //使用marker携带info信息，当点击事件的时候可以通过marker获得info信息
            Bundle bundle = new Bundle();
            //info必须实现序列化接口
            bundle.putSerializable("info", info);
            marker.setExtraInfo(bundle);
        }
        //将地图显示在最后一个marker的位置
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.setMapStatus(msu);
    }

   //计算两点之间距离
   // @param start
   // @param end
   // @return 米
    public String getDistance(BDLocation start,BDLocation end){
        double lat1 = (Math.PI/180)*start.getLatitude();
        double lat2 = (Math.PI/180)*end.getLatitude();

        double lon1 = (Math.PI/180)*start.getLongitude();
        double lon2 = (Math.PI/180)*end.getLongitude();

        //地球半径
        double R = 6371;

        //两点间距离 km，如果想要米的话，结果*1000
        double d =  Math.acos(Math.sin(lat1)*Math.sin(lat2)+Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon2-lon1))*R;
        if(d<1)
            return (int)d*1000+"m";
        else
            return String.format("%.2f",d)+"km";
    }

    @Override
    protected void onStop(){
        //清理内存前将数据储存
        File.saveObject(mFriendInfoList,FileName.FRIEND,MainActivity.this);
        File.saveObject(mEnemyInfoList,FileName.ENEMY,MainActivity.this);
        super.onStop();
    }
    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        //销毁前将数据储存
        File.saveObject(mFriendInfoList,FileName.FRIEND,MainActivity.this);
        File.saveObject(mEnemyInfoList,FileName.ENEMY,MainActivity.this);
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        super.onDestroy();
    }
}
