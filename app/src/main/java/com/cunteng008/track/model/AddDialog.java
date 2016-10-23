package com.cunteng008.track.model;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cunteng008.track.R;

/**
 * Created by CMJ on 2016/10/13.
 */

public class AddDialog extends Dialog {

    private static int default_width = 160; //默认宽度
    private static int default_height = 120;//默认高度

    private EditText mEditName;
    private EditText mEditNum;
    private ImageView mAddIcon;
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
        mAddIcon = (ImageView) mView.findViewById(R.id.add_icon);
        mOKBtn = (Button) mView.findViewById(R.id.add_OK_btn);
        mNOBtn = (Button) mView.findViewById(R.id.add_NO_btn);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        super.setContentView(mView);

    }

    public View getEditName(){
        return mEditName;
    }
    public View getEditNum(){
        return mEditNum;
    }
    public View getAddIcon() {
        return mAddIcon;
    }
    public TextView getTitle() {
        return mTitle;
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
