package com.neu.bsds.linyu.Server.DataType;

import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;
import org.bson.Document;

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

    public SingleRideData() {
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

    public void setLiftID(int liftID) {
        this.liftID = liftID;
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

    public Document toDocument() {
        return new Document("resortID", resortID)
                .append("dayNum", dayNum)
                .append("timestamp", timestamp)
                .append("skierID", skierID)
                .append("liftID", liftID);
    }

    public WriteModel<Document> toWriteModel() {
        return new UpdateOneModel<Document>(
//                new Document("id", dayNum + "&" + skierID),
                new Document("dayNum", dayNum).append("skierID", skierID),
                new Document("$inc", new Document("liftNum", 1).append("vertical", this.getVertical())),
                new UpdateOptions().upsert(true));
    }
}

