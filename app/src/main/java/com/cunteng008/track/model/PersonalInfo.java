package com.cunteng008.track.model;

import java.io.Serializable;

/**
 * Created by CMJ on 2016/10/11.
 */

public class PersonalInfo implements Serializable {
    private String mNum;
    private String mName;
    private double mLatitude;
    private double mLongitude;

    public String getNum() {
        return mNum;
    }

    public void setNum(String num) {
        mNum = num;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }
}
