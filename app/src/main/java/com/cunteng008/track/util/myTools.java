package com.cunteng008.track.util;

import java.math.BigDecimal;

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
}
