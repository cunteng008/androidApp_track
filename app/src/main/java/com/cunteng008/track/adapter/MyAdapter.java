package com.cunteng008.track.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cunteng008.track.R;
import com.cunteng008.track.model.DeleteDialog;
import com.cunteng008.track.model.DetailDialog;
import com.cunteng008.track.model.PersonalInfo;

import java.util.ArrayList;

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
        ViewHolder1 Holder = null;
        //ViewHolder1 EnemyHolder = null;
        //根据position获得View的type
        int type = getItemViewType(position);
        if(convertView==null){
            Holder = new ViewHolder1();
            switch (type){
                case TYPE_Friend:
                    convertView = View.inflate(mContext, R.layout.p_list_item, null);
                    Holder.mark = (ImageView) convertView.findViewById(R.id.mark);
                    Holder.content = (TextView) convertView.findViewById(R.id.content);
                    Holder.deleteImg = (ImageView) convertView.findViewById(R.id.delete_imgView);
                    convertView.setTag(R.id.my_adapter_tag,Holder);
                    break;
                case TYPE_Enemy:
                    convertView = View.inflate(mContext, R.layout.p_list_item, null);
                    Holder.mark = (ImageView) convertView.findViewById(R.id.mark);
                    Holder.content = (TextView) convertView.findViewById(R.id.content);
                    Holder.deleteImg = (ImageView) convertView.findViewById(R.id.delete_imgView);
                    convertView.setTag(R.id.my_adapter_tag,Holder);
                    break;
            }
        }
        else{
            switch (type) {
                case TYPE_Friend:
                    Holder = (ViewHolder1) convertView.getTag(R.id.my_adapter_tag);
                    break;
                case TYPE_Enemy:
                    Holder = (ViewHolder1) convertView.getTag(R.id.my_adapter_tag);
                    break;
            }
        }
        switch (type)
        {

            case TYPE_Friend:
                PersonalInfo fp = mInfoList.get(position);
                Holder.mark.setImageResource(R.drawable.friend_marka);
                Holder.content.setText(fp.getName()+" "+fp.getNum());
                if(mDoWhat.equals("edit")){
                    Holder.deleteImg.setImageResource(R.drawable.delete);
                    Holder.deleteImg.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            deleteDialog(position);
                        }
                    });
                }
                else{
                    Holder.content.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                           detailDialog(position);
                        }
                    });
                }
                break;
            case TYPE_Enemy:
                PersonalInfo ep = mInfoList.get(position);
                Holder.mark.setImageResource(R.drawable.enemy_marka);
                Holder.content.setText(ep.getName()+" "+ep.getNum());
                Holder.deleteImg.setImageResource(R.drawable.delete);
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
        if(mIsFriend){
            title.setText("DELETE FRIEND");
        }
        else {
            title.setText("DELETE ENEMY");
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

    //显示详细内容窗口
    private void detailDialog(final int pos){
        final DetailDialog detailDialog ;
        detailDialog = new DetailDialog(mContext);
        final TextView textName = (TextView) detailDialog.getTextName();
        final TextView textNum = (TextView)  detailDialog.getTextNum();

        textName.setText("Name:"+"\n"+mInfoList.get(pos).getName());
        textNum.setText("Number:"+"\n"+mInfoList.get(pos).getNum());
        detailDialog.setOnOKListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailDialog.dismiss();
            }
        });
        detailDialog.setOnNOListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailDialog.dismiss();
            }
        });
        detailDialog.show();
    }
}
