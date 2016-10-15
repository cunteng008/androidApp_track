package com.cunteng008.track.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cunteng008.track.R;
import com.cunteng008.track.adapter.MyAdapter;
import com.cunteng008.track.constant.Constant;
import com.cunteng008.track.constant.FileName;
import com.cunteng008.track.constant.MyAdapterConstant;
import com.cunteng008.track.model.AddDialog;
import com.cunteng008.track.model.PersonalInfo;
import com.cunteng008.track.util.File;

import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity {

    private ArrayList<PersonalInfo> mFriendsList = new ArrayList<PersonalInfo>();
    private MyAdapter mMyAdapter;
    private ListView mFLv;
    private int mFLvPosition = 0;

    //控件
    private Button mFRadarBtn;
    private Button mFEnemiesBtn;
    private Button mAddFriendBtn;
    private AddDialog mAddDialog;
    private Button  mEditBtn;

    //开关
    private boolean mEditSwitch = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        mFriendsList =
                (ArrayList<PersonalInfo>) File.getObject(FileName.FRIEND,FriendsActivity.this);
        if(mFriendsList != null){
            mMyAdapter = new MyAdapter(FriendsActivity.this,mFriendsList,
                    true, MyAdapterConstant.DEFAULT);
            mFLv = (ListView) findViewById(R.id.f_lv);  /*定义一个动态数组*/
            mFLv.setAdapter(mMyAdapter);
        }else {
            mFriendsList = new ArrayList<PersonalInfo>();
        }

        mEditBtn = (Button) findViewById(R.id.f_edit_btn);
        mEditBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(mFriendsList == null){
                    return;
                }
                if(!mEditSwitch){
                    mEditSwitch = true;
                    mMyAdapter = new MyAdapter(FriendsActivity.this,mFriendsList,
                            true, MyAdapterConstant.EDIT);
                    mFLv = (ListView) findViewById(R.id.f_lv);  /*定义一个动态数组*/
                    mFLv.setAdapter(mMyAdapter);
                }
                else {
                    mEditSwitch = false;
                    mMyAdapter = new MyAdapter(FriendsActivity.this,mFriendsList,
                            true, MyAdapterConstant.DEFAULT);
                    mFLv = (ListView) findViewById(R.id.f_lv);  /*定义一个动态数组*/
                    mFLv.setAdapter(mMyAdapter);
                }
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
                File.saveObject(mFriendsList,FileName.FRIEND,FriendsActivity.this);
                finish();
            }
        });

        mFEnemiesBtn = (Button) findViewById(R.id.f_enemies_button);
        mFEnemiesBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                File.saveObject(mFriendsList,FileName.FRIEND,FriendsActivity.this);
                Intent i = new Intent(FriendsActivity.this,EnemiesActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    // 弹窗
    private void addDialog() {
        mAddDialog = new AddDialog(FriendsActivity.this);
        final EditText editName = (EditText) mAddDialog.getEditName();
        final EditText editNum = (EditText) mAddDialog.getEditNum();
        mAddDialog.setOnOKListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String num = editNum.getText().toString();
                if( (name.length() == 0) || (num.length() == 0) ){
                    Toast.makeText(FriendsActivity.this,"请输入",Toast.LENGTH_SHORT).show();
                    return;
                }
                PersonalInfo p = new PersonalInfo();
                p.setName(name);
                p.setNum(num);
                //若之前mFriendList == null，程序会崩溃，长度等于0不等于null
                mFriendsList.add(p);
                if(mFriendsList.size()==1){
                    mMyAdapter = new MyAdapter(FriendsActivity.this,mFriendsList,
                            true, MyAdapterConstant.DEFAULT);
                    mFLv = (ListView) findViewById(R.id.f_lv);  /*定义一个动态数组*/
                    mFLv.setAdapter(mMyAdapter);
                }else {
                    mMyAdapter.notifyDataSetChanged();
                }
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
}
