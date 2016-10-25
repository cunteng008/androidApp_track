package com.cunteng008.track.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by CMJ on 2016/10/21.
 */

public class myTools {
    //decimal digits 小数位数，四舍五入法保留小数
    public static double ReservedDecimalResult(double f,int decimalDigits){
        BigDecimal b   =   new   BigDecimal(f);
        double  f1 =   b.setScale(decimalDigits,BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

    //正则表达式验证
    public static boolean verifyLatLon(String str) {
        // 验证规则
        String regEx = "[0-9]*.[0-9]*/[0-9]*.[0-9]*";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 字符串是否与正则表达式相匹配
        boolean result = matcher.matches();
        return result;
    }

    //解析短信,返回lon，lat两个字符串
    public static String[] analyzeReceivedMassage(String text) {
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
