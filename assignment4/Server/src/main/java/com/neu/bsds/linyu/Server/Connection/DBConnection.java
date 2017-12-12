package com.neu.bsds.linyu.Server.Connection;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

/**
 * Created by linyuyu on 10/20/17.
 */
public class DBConnection {


    public static MongoDatabase getDB() {

        // Standard URI format: mongodb://[dbuser:dbpassword@]host:port/dbname
        MongoClient client = new MongoClient("34.211.159.27", 27017);
        MongoDatabase db = client.getDatabase("test");
        return db;
    }


    //only for test
    public static void main (String[] args) {
        MongoDatabase connection = DBConnection.getDB();
        if (connection == null) {
            System.out.println("null conneciton");
        }
    }

}
