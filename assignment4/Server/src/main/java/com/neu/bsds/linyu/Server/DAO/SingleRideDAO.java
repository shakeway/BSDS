package com.neu.bsds.linyu.Server.DAO;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.neu.bsds.linyu.Server.DataType.SingleRideData;
import com.neu.bsds.linyu.Server.Connection.DBConnection;
import org.bson.Document;

import javax.print.Doc;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linyuyu on 10/22/17.
 */
public class SingleRideDAO {

    private static final String TABLENAME = "single_ride";


    public  static String getTABLENAME() {
        return TABLENAME;
    }


    public static void batchPost(List<SingleRideData> cachedPost) throws SQLException {
        PreparedStatement preparedStatement = null;
        MongoDatabase db = null;
        db = DBConnection.getDB();
        MongoCollection<Document> collection = db.getCollection(TABLENAME);

        List<Document> documents = new ArrayList<>();
        for (SingleRideData singleRideData : cachedPost) {
            documents.add(singleRideData.toDocument());
        }

        collection.insertMany(documents);
    }


    public static void main (String[] args) {
        List<SingleRideData> cachedPost = new ArrayList<>();
        cachedPost.add(new SingleRideData("2","2","2",2,"2" ));
        try {
            batchPost(cachedPost);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
