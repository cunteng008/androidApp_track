package com.cunteng008.track.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.cunteng008.track.model.DeleteDialog;
import com.cunteng008.track.model.PersonalInfo;

import static com.cunteng008.track.R.drawable.enemy_icon;
import static com.cunteng008.track.util.myTools.ReservedDecimalResult;

public class EnemyDetailActivity extends AppCompatActivity {

    //控件,E是enemy界面的控件
    private Button mERadarBtn;
    private Button mEFriendsBtn;
    private Button mAddEnemyBtn;
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
        setContentView(R.layout.activity_enemy_detail);

        //信息的索引
        Intent intent = getIntent();
        mIndex =  Integer.parseInt(intent.getStringExtra("extra_index"));

        mTxtDetailTitle = (TextView) findViewById(R.id.txt_detail_title);
        mTxtDetailTitle.setText(MainActivity.mEnemyInfoList.get(mIndex).getName()+" "+
                MainActivity.mEnemyInfoList.get(mIndex).getNum());

        mTxtNum = (TextView) findViewById(R.id.txt_enemy_number);
        mTxtNum.setText(MainActivity.mEnemyInfoList.get(mIndex).getNum());

        double lat = MainActivity.mEnemyInfoList.get(mIndex).getLatitude();
        double lon = MainActivity.mEnemyInfoList.get(mIndex).getLongitude();
        mTxtLL = (TextView) findViewById(R.id.txt_enemy_long_lang);
        mTxtLL.setText(lat+"N"+"/"+
                lon+"E");

        mEditBtn = (Button) findViewById(R.id.e_edit_btn);
        mDeleteImgBtn = (ImageView) findViewById(R.id.delete_imgView);
        mDeleteImgBtn.setVisibility(View.INVISIBLE);
        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mEnemyInfoList == null) {
                    return;
                }
                if (!mEditSwitch) {
                    mEditSwitch = true;
                    mEditBtn.setText(Constant.DONE);
                    mDeleteImgBtn.setVisibility(View.VISIBLE);
                } else {
                    mEditSwitch = false;
                    mEditBtn.setText(Constant.EDIT);
                    mDeleteImgBtn.setVisibility(View.INVISIBLE);
                }
            }
        });

        mDeleteImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog(mIndex);
            }
        });

        mAddEnemyBtn = (Button)findViewById(R.id.add_enemy_btn);
        mAddEnemyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        mERadarBtn = (Button) findViewById(R.id.e_radar_btn);
        mERadarBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        mEFriendsBtn = (Button) findViewById(R.id.e_enemies_btn);
        mEFriendsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(EnemyDetailActivity.this,FriendsActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

        // 删除弹窗
    private void deleteDialog(final int pos) {
        final DeleteDialog mDeleteDialog ;
        mDeleteDialog = new DeleteDialog(EnemyDetailActivity.this);
        final TextView textName = (TextView) mDeleteDialog.getTextName();
        final TextView textNum = (TextView)  mDeleteDialog.getTextNum();
        final TextView title = (TextView) mDeleteDialog.getTitle();
        final  ImageView deleteIcon = (ImageView) mDeleteDialog.getDeleteIcon();

        title.setText("DELETE ENEMY");

        textName.setText(MainActivity.mEnemyInfoList.get(mIndex).getName());
        textNum.setText(MainActivity.mEnemyInfoList.get(mIndex).getNum());
        deleteIcon.setImageResource(R.drawable.enemy_delete_icon);

        mDeleteDialog.setOnOKListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.mEnemyInfoList.remove(pos);
                Intent data=new Intent();
                setResult(Activity.RESULT_OK, data);
                Intent mIntent = new Intent(EnemyDetailActivity.this,EnemiesActivity.class);
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
        mAddDialog = new AddDialog(EnemyDetailActivity.this);
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
                    Toast.makeText(EnemyDetailActivity.this,"请输入",Toast.LENGTH_SHORT).show();
                    return;
                }
                PersonalInfo p = new PersonalInfo();
                p.setName(name);
                p.setNum(num);
                p.setLatitude(-1);
                p.setLongitude(-1);
                MainActivity.mEnemyInfoList.add(p);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mIntent = new Intent(EnemyDetailActivity.this,EnemiesActivity.class);
        startActivity(mIntent);
    }

}
