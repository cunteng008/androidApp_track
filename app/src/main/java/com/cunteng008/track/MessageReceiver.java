package com.cunteng008.track;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.nfc.FormatException;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.baidu.location.BDLocation;
import com.cunteng008.track.activity.MainActivity;
import com.cunteng008.track.constant.Constant;
import com.cunteng008.track.constant.FileName;
import com.cunteng008.track.model.PersonalInfo;
import com.cunteng008.track.util.File;

import java.util.ArrayList;

/**
 * Created by CMJ on 2016/10/14.
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

        //读取文件的数据
        ArrayList<PersonalInfo> infoList = new ArrayList<>();
        infoList =(ArrayList<PersonalInfo>) File.getObject(FileName.FRIEND,context);
        if (infoList == null){
            infoList = new ArrayList<>();
        }

        //若收到where are you,则执行
        int i = 0;
        if(fullMessage.equals("where are you?")){
            for(;i<infoList.size();i++){
                if(infoList.get(i).getNum().equals(address)){
                   break;
                }
            }
            if(i == infoList.size()){
                return;
            }
            Intent mIntent = new Intent(context,MainActivity.class);
            String phone =address;
            //Constant.TEXT_Head用来标识短信的内容为指定应用发出的位置信息
            //String.format会出现异常，导致程序立即正常退出
            String myLocation = Constant.MASSAGE_HEAD +  MainActivity.mMyLocation.getLongitude()
                    + "/" +MainActivity.mMyLocation.getLatitude();
            SmsManager manager = SmsManager.getDefault();
            //因为一条短信有字数限制，因此要将长短信拆分
            ArrayList<String> list = manager.divideMessage(myLocation );
            for(String text:list){
                manager.sendTextMessage(phone, null, text, null, null);
            }
            return;
        }

        int loc;
        if((loc = fullMessage.indexOf(":"))< 0){
            return;
        }
        //不包含loc+1
        String head = fullMessage.substring(0,loc+1);
        if(!head.equals(Constant.MASSAGE_HEAD)){
            return;
        }

        //将信息头去掉
        fullMessage = fullMessage.replace(Constant.MASSAGE_HEAD,"");
        int j=0;
        for(PersonalInfo info:infoList){
            if(info.getNum().equals(address)){
                //若收到经纬度，则执行
                String[] strOfLocation = analyzeReceivedMassage(fullMessage);
                double lon;
                double lat;
                try {
                    lon = Double.parseDouble(strOfLocation[0]);
                    lat = Double.parseDouble(strOfLocation[1]);
                }catch (Exception e){
                    return;
                }
                info.setLatitude(lat);
                info.setLongitude(lon);
                infoList.set(j,info);
                File.saveObject(infoList,FileName.FRIEND,context);
                return;
            }
            j++;
        }
    }

    //解析短信
    String[] analyzeReceivedMassage(String text){
        String temp = text ;
        //取得斜杠的位置
        int loc = temp.indexOf('/');
        //得到Latitude(纬度)的字符串
        String lat = temp.substring(loc+1,temp.length());
        //取得Longitude(经度)的字符串
        String lon = temp.substring(0,loc);
        String[] strOfLoction =new String[2];
        strOfLoction[0] = lon;
        strOfLoction[1] = lat;
        return strOfLoction;
    }
}


