package com.cunteng008.track.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cunteng008.track.R;
import com.cunteng008.track.model.DeleteDialog;
import com.cunteng008.track.model.PersonalInfo;

import java.util.ArrayList;

import static com.cunteng008.track.R.drawable.enemy_delete_icon;

public class MyAdapter extends BaseAdapter {

    private static final int TYPE_Friend = 1;
    private static final int TYPE_Enemy = 0;

    private Context mContext;
    private ArrayList<PersonalInfo> mInfoList=new ArrayList<PersonalInfo>();
    private boolean mIsFriend;
    private String mDoWhat;

    public MyAdapter(Context context, ArrayList<PersonalInfo> listItems,
                     boolean isFriend,String doWhat) {
        mInfoList = listItems;
        mIsFriend = isFriend;
        mContext = context;
        mDoWhat = doWhat;
    }

    @Override
    public int getItemViewType(int position) {
        int result = 0;
        if(mIsFriend){
            result = TYPE_Friend;
        }
        else {
            result = TYPE_Enemy;
        }
        return result;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
    @Override
    public int getCount() {
        return mInfoList.size();
    }
    @Override
    public Object getItem(int position) {
        return mInfoList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder1 holderFriend = null;
        ViewHolder1 holderEnemy = null;
        int type = getItemViewType(position);
        if(convertView==null){
            holderFriend = new ViewHolder1();
            holderEnemy = new ViewHolder1();
            switch (type){
                case TYPE_Friend:
                    convertView = View.inflate(mContext, R.layout.list_item, null);
                    holderFriend.mark = (ImageView) convertView.findViewById(R.id.mark);
                    holderFriend.content = (TextView) convertView.findViewById(R.id.content);
                    holderFriend.deleteImg = (ImageView) convertView.findViewById(R.id.delete_imgView);
                    convertView.setTag(R.id.my_adapter_friend_tag,holderFriend);
                    break;
                case TYPE_Enemy:
                    convertView = View.inflate(mContext, R.layout.list_item, null);
                    holderEnemy.mark = (ImageView) convertView.findViewById(R.id.mark);
                    holderEnemy.content = (TextView) convertView.findViewById(R.id.content);
                    holderEnemy.deleteImg = (ImageView) convertView.findViewById(R.id.delete_imgView);
                    convertView.setTag(R.id.my_adapter_enemy_tag,holderEnemy);
                    break;
            }
        }
        else{
            switch (type) {
                case TYPE_Friend:
                    holderFriend = (ViewHolder1) convertView.getTag(R.id.my_adapter_friend_tag);
                    break;
                case TYPE_Enemy:
                    holderEnemy = (ViewHolder1) convertView.getTag(R.id.my_adapter_enemy_tag);
                    break;
            }
        }
        switch (type)
        {
            case TYPE_Friend:
                PersonalInfo fp = mInfoList.get(position);
                holderFriend.mark.setImageResource(R.drawable.friend_icon);
                holderFriend.content.setText(fp.getName()+" "+fp.getNum());

                holderFriend.deleteImg.setImageResource(R.drawable.delete);
                holderFriend.deleteImg.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        deleteDialog(position);
                    }
                });
                if(mDoWhat.equals("edit")){
                   holderFriend.deleteImg.setVisibility(View.VISIBLE);
                }else {
                    holderFriend.deleteImg.setVisibility(View.INVISIBLE);
                }
                break;
            case TYPE_Enemy:
                PersonalInfo ep = mInfoList.get(position);
                holderEnemy.mark.setImageResource(R.drawable.enemy_icon);
                holderEnemy.content.setText(ep.getName()+" "+ep.getNum());
                holderEnemy.content.setTextColor(Color.rgb(236, 50, 57));

                holderEnemy.deleteImg.setImageResource(R.drawable.delete);
                holderEnemy.deleteImg.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        deleteDialog(position);
                    }
                });
                if(mDoWhat.equals("edit")){
                    holderEnemy.deleteImg.setVisibility(View.VISIBLE);
                }else {
                    holderEnemy.deleteImg.setVisibility(View.INVISIBLE);
                }
                break;
        }
        return convertView;
    }

    private static class ViewHolder1 {
        ImageView mark;
        TextView content;
        ImageView deleteImg ;
    }

    // 删除弹窗
    private void deleteDialog(final int pos) {
        final DeleteDialog mDeleteDialog ;
        mDeleteDialog = new DeleteDialog(mContext);
        final TextView textName = (TextView) mDeleteDialog.getTextName();
        final TextView textNum = (TextView)  mDeleteDialog.getTextNum();
        final TextView title = (TextView) mDeleteDialog.getTitle();
        final  ImageView deleteIcon = (ImageView) mDeleteDialog.getDeleteIcon();
        if(mIsFriend){
            title.setText("DELETE FRIEND");
        }
        else {
            title.setText("DELETE ENEMY");
            deleteIcon.setImageResource(enemy_delete_icon);
        }
        textName.setText(mInfoList.get(pos).getName());
        textNum.setText(mInfoList.get(pos).getNum());
         mDeleteDialog.setOnOKListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInfoList.remove(pos);
                notifyDataSetChanged();
                 mDeleteDialog.dismiss();
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

}
