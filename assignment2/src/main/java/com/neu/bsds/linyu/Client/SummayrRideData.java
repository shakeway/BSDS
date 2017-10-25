package com.neu.bsds.linyu.Client;

/**
 * Created by linyuyu on 10/22/17.
 */
public class SummayrRideData {
    private String dayNum;
    private String skierID;
    private int liftNum;
    private int vertical;

    public SummayrRideData(String dayNum, String skierID, int liftNum, int vertical) {
        this.dayNum = dayNum;
        this.skierID = skierID;
        this.liftNum = liftNum;
        this.vertical = vertical;
    }

    public String getDayNum() {
        return dayNum;
    }

    public String getSkierID() {
        return skierID;
    }

    public int getLieftNum() {
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

    public void setLieftNum(int liftNum) {
        this.liftNum = liftNum;
    }

    public void setVertical(int vertical) {
        this.vertical = vertical;
    }
}
