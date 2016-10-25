package com.cunteng008.track;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.cunteng008.track.activity.MainActivity;
import com.cunteng008.track.constant.Constant;
import com.cunteng008.track.model.PersonalInfo;
import com.cunteng008.track.util.myTools;

import java.util.ArrayList;

import static com.cunteng008.track.util.DES.decryptDES;
import static com.cunteng008.track.util.DES.encryptDES;
import static com.cunteng008.track.util.myTools.analyzeReceivedMassage;
import static com.cunteng008.track.util.myTools.verifyLatLon;

/**
 * Created by CMJ on 2016/10/25.
 */

public class MessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Bundle bundle = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");  //提取短信消息
        SmsMessage[] messages = new SmsMessage[pdus.length];
        for(int i = 0;i<messages.length;i++){
            messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
        }
        String address = messages[0].getOriginatingAddress();  //获取发送方号码
        String fullMessage = "";
        for(SmsMessage message : messages){
            fullMessage += message.getMessageBody();  //获取短信内容
        }
        //解密
        /*
        try {
            //种子为12345678
            fullMessage = decryptDES("12345678",fullMessage);
        }catch (Exception e){
            return;
        } */

        //若收到where are you,则执行
        int i = 0;
        if(fullMessage.equals(Constant.ASK_LOCATION)){
            //在敌人和朋友的号码中查找
            for(; i< MainActivity.mFriendInfoList.size(); i++){
                if(MainActivity.mFriendInfoList.get(i).getNum().equals(address)){
                    break;
                }
            }
            if(i == MainActivity.mFriendInfoList.size()){
                int k = 0;
                for(;k<MainActivity.mEnemyInfoList.size();k++){
                    if(MainActivity.mEnemyInfoList.get(k).getNum().equals(address)){
                        break;
                    }
                }
                if(k == MainActivity.mEnemyInfoList.size()){
                    return;
                }
            }

            Intent mIntent = new Intent(context,MainActivity.class);
            String phone =address;
            //Constant.TEXT_Head用来标识短信的内容为指定应用发出的位置信息
            //String.format会出现异常，导致程序立即正常退出
            String myLocation =  MainActivity.mMyLocation.getLongitude()
                    + "/" +MainActivity.mMyLocation.getLatitude();
            SmsManager manager = SmsManager.getDefault();
            /*
            try {
                myLocation = encryptDES("12345678",myLocation);
            }catch (Exception e){
                return;
            } */
            //因为一条短信有字数限制，因此要将长短信拆分
            ArrayList<String> list = manager.divideMessage(myLocation );
            for(String text:list){
                manager.sendTextMessage(phone, null, text, null, null);
            }
            return;
        }

    }
}
