package com.neu.bsds.linyu.Server.DAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.WriteModel;
import com.neu.bsds.linyu.Server.DataType.SingleRideData;
import com.neu.bsds.linyu.Server.DataType.SummaryRideData;
import com.neu.bsds.linyu.Server.Connection.DBConnection;
import org.bson.Document;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * Created by linyuyu on 10/22/17.
 */
public class SummaryRideDAO {

    private static String TABLENAME = "summary_table";

    public static SummaryRideData get(String skierID, String dayNum) throws SQLException {

        MongoDatabase db = DBConnection.getDB();
        MongoCollection collection = db.getCollection(TABLENAME);
        Document document = (Document) collection.find(and(eq("skierID",skierID), eq("dayNum", dayNum))).first();
        if (document == null)
            return null;
        return new SummaryRideData(
                document.getString("dayNum"),
                document.getString("skierID"),
                document.getInteger("liftNum"),
                document.getInteger("vertical"));
    }


    public static void batchUpdate (List<SingleRideData> cachedPost) throws SQLException {

        MongoDatabase db = DBConnection.getDB();
        MongoCollection<Document> collection = db.getCollection(TABLENAME);

        List<WriteModel<Document>> writeModels = new ArrayList<>();
        for (SingleRideData singleRideData: cachedPost){
            writeModels.add(singleRideData.toWriteModel());
        }
        collection.bulkWrite(writeModels);
    }

    public static void main (String[] args) {
        List<SingleRideData> cachedPost = new ArrayList<>();
        cachedPost.add(new SingleRideData("1","1000","1000",1,"1" ));
//        try {
//            batchUpdate(cachedPost);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        try {
            System.out.println(get("1000", "1000").toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
