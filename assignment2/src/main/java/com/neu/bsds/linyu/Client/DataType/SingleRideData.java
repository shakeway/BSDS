package com.neu.bsds.linyu.Client.DataType;

/**
 * Created by linyuyu on 10/19/17.
 */
public class SingleRideData {
    private String resortID;
    private String dayNum;
    private String timestamp;
    private String skierID;
    private int liftID;
    private static int[] VERTICAL = {200, 300, 400, 500};


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
