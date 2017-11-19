package com.neu.bsds.linyu.Client.DataType;

/**
 * Created by linyuyu on 10/22/17.
 */
public class SummaryRideData {
    private String dayNum;
    private String skierID;
    private int liftNum;
    private int vertical;

    public SummaryRideData(String dayNum, String skierID, int liftNum, int vertical) {
        this.dayNum = dayNum;
        this.skierID = skierID;
        this.liftNum = liftNum;
        this.vertical = vertical;
    }

    public SummaryRideData() {
    }

    public String getId() { return dayNum + "&" + skierID; };

    public String getDayNum() {
        return dayNum;
    }

    public String getSkierID() {
        return skierID;
    }

    public int getLiftNum() {
        return liftNum;
    }

    public int getVertical() {
        return vertical;
    }

    public void setDayNum(String dayNum) {
        this.dayNum = dayNum;
    }

    public void setSkierID(String skierID) {
        this.skierID = skierID;
    }

    public void setLiftNum(int liftNum) {
        this.liftNum = liftNum;
    }

    public void setVertical(int vertical) {
        this.vertical = vertical;
    }

    @Override
    public String toString() {
        return "SummaryRideData{" +
                "dayNum='" + dayNum + '\'' +
                ", skierID='" + skierID + '\'' +
                ", liftNum=" + liftNum +
                ", vertical=" + vertical +
                '}';
    }
}
