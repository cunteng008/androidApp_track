package com.cunteng008.track.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cunteng008.track.R;
import com.cunteng008.track.constant.Constant;
import com.cunteng008.track.model.AddDialog;
import com.cunteng008.track.model.DeleteDialog;
import com.cunteng008.track.model.PersonalInfo;

import static com.cunteng008.track.util.myTools.ReservedDecimalResult;

public class FriendDetailActivity extends AppCompatActivity {

    //控件
    private Button mFRadarBtn;
    private Button mFEnemiesBtn;
    private Button mAddFriendBtn;
    private AddDialog mAddDialog;
    private Button  mEditBtn;
    private ImageView mDeleteImgBtn;

    private TextView mTxtDetailTitle;
    private TextView mTxtNum;
    private TextView mTxtLL;
    private TextView mTxtAltitude;
    private TextView mTxtAccuracy;
    private TextView mTxtNearestCity;
    private TextView mTxtSecsLastUpdate;
    private TextView mTxtSecsNextUpdate;

    //开关
    private boolean mEditSwitch = false;
    private int mIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);

        //信息的索引
        Intent intent = getIntent();
        mIndex =  Integer.parseInt(intent.getStringExtra("extra_index"));

        mTxtDetailTitle = (TextView) findViewById(R.id.txt_detail_title);
        mTxtDetailTitle.setText(MainActivity.mFriendInfoList.get(mIndex).getName()+" "+
        MainActivity.mFriendInfoList.get(mIndex).getNum());

        mTxtNum = (TextView) findViewById(R.id.txt_friend_number);
        mTxtNum.setText(MainActivity.mFriendInfoList.get(mIndex).getNum());

        double lat =MainActivity.mFriendInfoList.get(mIndex).getLatitude();
        double lon = MainActivity.mFriendInfoList.get(mIndex).getLongitude();
        mTxtLL = (TextView) findViewById(R.id.txt_friend_long_lang);
        mTxtLL.setText(lat+"N"+"/"+
                lon+"E");

        mEditBtn = (Button) findViewById(R.id.f_edit_btn);
        mDeleteImgBtn = (ImageView) findViewById(R.id.delete_imgView);
        mDeleteImgBtn.setVisibility(View.INVISIBLE);
        mEditBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(MainActivity.mFriendInfoList == null){
                    return;
                }
                if(!mEditSwitch){
                    mEditSwitch = true;
                    mEditBtn.setText(Constant.DONE);
                    mDeleteImgBtn.setVisibility(View.VISIBLE);
                }
                else {
                    mEditSwitch = false;
                    mEditBtn.setText(Constant.EDIT);
                    mDeleteImgBtn.setVisibility(View.INVISIBLE);
                }
            }
        });
        mDeleteImgBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                deleteDialog(mIndex);
            }
        });

        mAddFriendBtn = (Button) findViewById(R.id.add_friend_btn);
        mAddFriendBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addDialog();
            }
        });

        mFRadarBtn = (Button) findViewById(R.id.f_radar_btn);
        mFRadarBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        mFEnemiesBtn = (Button) findViewById(R.id.f_enemies_btn);
        mFEnemiesBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(FriendDetailActivity.this,EnemiesActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    // 删除弹窗
    private void deleteDialog(final int pos) {
        final DeleteDialog mDeleteDialog ;
        mDeleteDialog = new DeleteDialog(FriendDetailActivity.this);
        final TextView textName = (TextView) mDeleteDialog.getTextName();
        final TextView textNum = (TextView)  mDeleteDialog.getTextNum();
        final TextView title = (TextView) mDeleteDialog.getTitle();
        final  ImageView deleteIcon = (ImageView) mDeleteDialog.getDeleteIcon();

        textName.setText(MainActivity.mFriendInfoList.get(mIndex).getName());
        textNum.setText(MainActivity.mFriendInfoList.get(mIndex).getNum());

        title.setText("DELETE FRIEND");

        mDeleteDialog.setOnOKListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mFriendInfoList.remove(pos);
                Intent data=new Intent();
                setResult(Activity.RESULT_OK, data);
                Intent mIntent = new Intent(FriendDetailActivity.this,FriendsActivity.class);
                startActivity(mIntent);
                finish();
            }
        });
        mDeleteDialog.setOnNOListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDeleteDialog.dismiss();
            }
        });
        mDeleteDialog.show();
    }

    // 弹窗
    private void addDialog() {
        mAddDialog = new AddDialog(FriendDetailActivity.this);
        final EditText editName = (EditText) mAddDialog.getEditName();
        final EditText editNum = (EditText) mAddDialog.getEditNum();
        mAddDialog.setOnOKListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String num = editNum.getText().toString();
                if( (name.length() == 0) || (num.length() == 0) ){
                    Toast.makeText(FriendDetailActivity.this,"请输入",Toast.LENGTH_SHORT).show();
                    return;
                }
                PersonalInfo p = new PersonalInfo();
                p.setName(name);
                p.setNum(num);
                p.setLatitude(MainActivity.mMyLocation.getLatitude()+0.01);
                p.setLongitude(MainActivity.mMyLocation.getLongitude()+0.01);
                //若之前mFriendList == null，程序会崩溃，长度等于0不等于null
                MainActivity.mFriendInfoList.add(p);
                mAddDialog.dismiss();
            }
        });
        mAddDialog.setOnNOListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddDialog.dismiss();
            }
        });
        mAddDialog.show();
    }


    //设置返回键执行内容
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mIntent = new Intent(FriendDetailActivity.this,FriendsActivity.class);
        startActivity(mIntent);
    }
}
