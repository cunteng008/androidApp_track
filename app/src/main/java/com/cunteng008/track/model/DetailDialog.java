package com.cunteng008.track.model;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cunteng008.track.R;

/**
 * Created by CMJ on 2016/10/14.
 */

public class DetailDialog extends Dialog {
    private TextView mTextName;
    private TextView mTextNum;
    private TextView mTextPositon;
    private TextView mTextAltutide;
    private TextView mTextAccuracy;

    //private Button mOKBtn, mNOBtn;

    public DetailDialog(Context context) {
        super(context, R.style.DetailDialog);
        setCustomDialog();
    }
    public DetailDialog(Context context, int theme) {
        super(context,theme);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).
                inflate(R.layout.dialog_detail, null);
        mTextAltutide = (TextView) mView.findViewById(R.id.detail_altitude);
        mTextName = (TextView) mView.findViewById(R.id.detail_name);
        mTextNum = (TextView) mView.findViewById(R.id.detail_num);
        mTextPositon = (TextView) mView.findViewById(R.id.detail_pos);
        mTextAccuracy = (TextView) mView.findViewById(R.id.detail_accuracy);
        //mOKBtn = (Button) mView.findViewById(R.id.delete_OK_btn);
        //mNOBtn = (Button) mView.findViewById(R.id.delete_NO_btn);
        super.setContentView(mView);
    }

    public View getTextName(){
        return mTextName;
    }
    public View getTextNum(){
        return mTextNum;
    }
    public View getTextPositon() {
        return mTextPositon;
    }

    public View getTextAltutide() {
        return mTextAltutide;
    }

    public View getTextAccuracy() {
        return mTextAccuracy;
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
        //mOKBtn.setOnClickListener(listener);
    }

    public void setOnNOListener(View.OnClickListener listener){
        //mNOBtn.setOnClickListener(listener);
    }
}
