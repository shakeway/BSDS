package com.neu.bsds.linyu;

/**
 * Created by linyuyu on 10/20/17.
 */
public class SkierData {
    private String resortID;
    private String dayNum;
    private String timestamp;
    private String skierID;
    private String lieftID;

    public SkierData() {
    }

    public SkierData(String resortID, String dayNum, String skierID, String lieftID, String timestamp) {
        this.resortID = resortID;
        this.dayNum = dayNum;
        this.timestamp = timestamp;
        this.skierID = skierID;
        this.lieftID = lieftID;
    }

    public void setResortID(String resortID) {
        this.resortID = resortID;
    }

    public void setDayNum(String dayNum) {
        this.dayNum = dayNum;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setSkierID(String skierID) {
        this.skierID = skierID;
    }

    public void setLieftID(String lieftID) {
        this.lieftID = lieftID;
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

    public String getLieftID() {
        return lieftID;
    }
}

