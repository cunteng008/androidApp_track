package com.cunteng008.track.model;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cunteng008.track.R;

/**
 * Created by CMJ on 2016/10/13.
 */

public class AddDialog extends Dialog {
    private EditText mEditName;
    private EditText mEditNum;
    private Button mOKBtn, mNOBtn;
    private TextView mTitle;

    public AddDialog(Context context) {
        super(context,R.style.AddDialog);
        setCustomDialog();
    }
    public AddDialog(Context context,int theme) {
        super(context,theme);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).
                inflate(R.layout.dialog_add, null);
        mTitle = (TextView) mView.findViewById(R.id.add_title);
        mEditName = (EditText) mView.findViewById(R.id.add_name);
        mEditNum = (EditText) mView.findViewById(R.id.add_num);
        mOKBtn = (Button) mView.findViewById(R.id.add_OK_btn);
        mNOBtn = (Button) mView.findViewById(R.id.add_NO_btn);
        super.setContentView(mView);
    }

    public View getEditName(){
        return mEditName;
    }
    public View getEditNum(){
        return mEditNum;
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
