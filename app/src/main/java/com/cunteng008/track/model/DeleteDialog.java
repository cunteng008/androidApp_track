package com.cunteng008.track.model;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cunteng008.track.R;

/**
 * Created by CMJ on 2016/10/14.
 */

public class DeleteDialog extends Dialog {

    private TextView mTextName;
    private TextView mTextNum;
    private Button mOKBtn, mNOBtn;
    private TextView mTitle;
    private ImageView mDeleteIcon;

    public DeleteDialog(Context context) {
        super(context, R.style.DeleteDialog);
        setCustomDialog();
    }
    public DeleteDialog(Context context,int theme) {
        super(context,theme);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).
                inflate(R.layout.dialog_delete, null);
        mTitle = (TextView) mView.findViewById(R.id.delete_title);
        mTextName = (TextView) mView.findViewById(R.id.delete_name);
        mTextNum = (TextView) mView.findViewById(R.id.delete_num);
        mOKBtn = (Button) mView.findViewById(R.id.delete_OK_btn);
        mNOBtn = (Button) mView.findViewById(R.id.delete_NO_btn);
        mDeleteIcon = (ImageView) mView.findViewById(R.id.delete_icon) ;
        super.setContentView(mView);
    }

    public View getTextName(){
        return mTextName;
    }
    public View getTextNum(){
        return mTextNum;
    }
    public View getTitle(){
        return mTitle;
    }
    public View getDeleteIcon() {
        return mDeleteIcon;
    }

    @Override
    public void setContentView(int layoutResID) {
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
    }

    @Override
    public void setContentView(View view) {
    }

    public void setOnOKListener(View.OnClickListener listener){
        mOKBtn.setOnClickListener(listener);
    }

    public void setOnNOListener(View.OnClickListener listener){
        mNOBtn.setOnClickListener(listener);
    }
}
