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
}
