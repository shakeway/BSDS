package com.neu.bsds.linyu.Server.DAO;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.neu.bsds.linyu.Server.Connection.DBConnection;
import com.neu.bsds.linyu.Server.DataType.ServerMetric;
import com.neu.bsds.linyu.Server.DataType.SingleRideData;
import org.bson.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linyuyu on 11/21/17.
 */
public class ServerMetricDAO {
    private static final String TABLENAME = "server_table";
    private static final String INSERT_QUERY = "INSERT INTO " + TABLENAME + " (responseTime, errorNum, flag)" + " VALUES (?, ?, ?)";


    public  static String getTABLENAME() {
        return TABLENAME;
    }


    public static void batchPost(List<ServerMetric> cachedPost) throws SQLException{

        MongoDatabase db = null;
        db = DBConnection.getDB();
        MongoCollection<Document> collection = db.getCollection(TABLENAME);

        List<Document> documents = new ArrayList<>();
        for (ServerMetric serverMetric: cachedPost) {
            documents.add(serverMetric.toDocument());
        }

        collection.insertMany(documents);
    }

    public static void main (String[] args) {
        List<ServerMetric> cachedPost = new ArrayList<>();
        cachedPost.add(new ServerMetric(1, 1, 1));
        try {
            batchPost(cachedPost);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

