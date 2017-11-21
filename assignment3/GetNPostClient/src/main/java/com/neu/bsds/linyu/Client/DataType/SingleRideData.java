package com.neu.bsds.linyu.Client.DataType;

import java.io.Serializable;

/**
 * Created by linyuyu on 10/19/17.
 */
public class SingleRideData implements Serializable{
    private String resortID;
    private String dayNum;
    private String skierID;
    private int liftID;
    private String timestamp;
    private static int[] VERTICAL = {200, 300, 400, 500};


    public SingleRideData() {
    }

    public void setResortID(String resortID) {
        this.resortID = resortID;
    }

    public void setDayNum(String dayNum) {
        this.dayNum = dayNum;
    }

    public void setSkierID(String skierID) {
        this.skierID = skierID;
    }

    public void setLiftID(int liftID) {
        this.liftID = liftID;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    //the constructor is used to create instance which is just for test PostClient
    public SingleRideData(String dayNum, String skierID) {
        this.dayNum = dayNum;
        this.skierID = skierID;
    }


    public SingleRideData(String resortID, String dayNum, String skierID, int liftID, String timestamp) {
        this.resortID = resortID;
        this.dayNum = dayNum;
        this.timestamp = timestamp;
        this.skierID = skierID;
        this.liftID = liftID;
    }

    public String getResortID() {
        return resortID;
    }

    public String getDayNum() {
        return dayNum;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getSkierID() {
        return skierID;
    }

    public int getLiftID() {
        return liftID;
    }

    public int getVertical() {
        return VERTICAL[(liftID - 1) / 10];
    }

    @Override
    public String toString() {
        return "SingleRideData{" +
                "resortID='" + resortID + '\'' +
                ", dayNum='" + dayNum + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", skierID='" + skierID + '\'' +
                ", liftID=" + liftID +
                '}';
    }

    public String getId() { return dayNum + "&" + skierID; };
}
