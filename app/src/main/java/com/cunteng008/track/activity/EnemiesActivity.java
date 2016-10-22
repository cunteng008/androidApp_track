package com.cunteng008.track.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cunteng008.track.R;
import com.cunteng008.track.adapter.MyAdapter;
import com.cunteng008.track.constant.Constant;
import com.cunteng008.track.constant.MyAdapterConstant;
import com.cunteng008.track.model.AddDialog;
import com.cunteng008.track.model.PersonalInfo;

import static com.cunteng008.track.R.drawable.enemy_icon;

public class EnemiesActivity extends AppCompatActivity {

    private MyAdapter mMyAdapter;
    private ListView mELv;

    //控件,E是enemy界面的控件
    private Button mERadarBtn;
    private Button mEFriendsBtn;
    private Button mAddEnemyBtn;
    private AddDialog mAddDialog;
    private Button  mEditBtn;

    //开关
    private boolean mEditSwitch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enemies);

        init();

        mEditBtn = (Button) findViewById(R.id.e_edit_btn);
        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEditSwitch) {
                    mEditSwitch = true;
                    mEditBtn.setText(Constant.DONE);
                    //敌人显示选择false
                    mMyAdapter = new MyAdapter(EnemiesActivity.this, MainActivity.mEnemyInfoList,
                            false, MyAdapterConstant.EDIT);
                    mELv = (ListView) findViewById(R.id.e_lv);  /*定义一个动态数组*/
                    mELv.setAdapter(mMyAdapter);
                } else {
                    mEditSwitch = false;
                    mEditBtn.setText(Constant.EDIT);
                    //敌人显示选择false
                    mMyAdapter = new MyAdapter(EnemiesActivity.this, MainActivity.mEnemyInfoList,
                            false, MyAdapterConstant.DEFAULT);
                    mELv = (ListView) findViewById(R.id.e_lv);  /*定义一个动态数组*/
                    mELv.setAdapter(mMyAdapter);
                }
            }
        });

        mELv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            // i 表示第几个item响应
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent(EnemiesActivity.this,EnemyDetailActivity.class);
                mIntent.putExtra("extra_index",i+"");
                startActivityForResult(mIntent,2);
                finish();
            }
        });

        mAddEnemyBtn = (Button) findViewById(R.id.add_friend_btn);
        mAddEnemyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                addDialog();
            }
        });

        mERadarBtn = (Button) findViewById(R.id.e_radar_btn);
        mERadarBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        mEFriendsBtn = (Button) findViewById(R.id.e_friends_btn);
        mEFriendsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(EnemiesActivity.this,FriendsActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    private void init(){
        mMyAdapter = new MyAdapter(EnemiesActivity.this, MainActivity.mEnemyInfoList,
                    false, MyAdapterConstant.DEFAULT);
        mELv = (ListView) findViewById(R.id.e_lv);  /*定义一个动态数组*/
        mELv.setAdapter(mMyAdapter);
    }

    // 弹窗
    private void addDialog() {
        mAddDialog = new AddDialog(EnemiesActivity.this);
        final EditText editName = (EditText) mAddDialog.getEditName();
        final EditText editNum = (EditText) mAddDialog.getEditNum();
        ImageView addIcon = (ImageView) mAddDialog.getAddIcon();
        TextView title = (TextView) mAddDialog.getTitle();
        title.setText("ADD ENEMY");
        addIcon.setImageResource(enemy_icon);
        mAddDialog.setOnOKListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String num = editNum.getText().toString();
                if( (name.length() == 0) || (num.length() == 0) ){
                    Toast.makeText(EnemiesActivity.this,"请输入",Toast.LENGTH_SHORT).show();
                    return;
                }
                PersonalInfo p = new PersonalInfo();
                p.setName(name);
                p.setNum(num);
                p.setLatitude(-1);
                p.setLongitude(-1);
                MainActivity.mEnemyInfoList.add(p);
                mMyAdapter.notifyDataSetChanged();
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
