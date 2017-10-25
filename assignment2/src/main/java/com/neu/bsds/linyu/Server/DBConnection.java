package com.neu.bsds.linyu.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by linyuyu on 10/20/17.
 */
public class DBConnection {

    private String PUBLIC_DNS = "bsds.cbouijouwxsf.us-west-2.rds.amazonaws.com";
    private String PORT = "3306";
    private String DATABASE = "mydevdb";
    private String REMOTE_DATABASE_USERNAME = "user";
    private String DATABASE_USER_PASSWORD = "12345678";
    private Connection connection = null;

    public DBConnection() {}

    public Connection connectJDBCToAWSEC2() {
        System.out.println("----MySQL JDBC Connection Testing -------");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return null;
        }

        System.out.println("MySQL JDBC Driver Registered!");
        //Connection connection = null;

        try {
            connection = DriverManager.
                    getConnection("jdbc:mysql://" + PUBLIC_DNS + ":" + PORT + "/" + DATABASE, REMOTE_DATABASE_USERNAME, DATABASE_USER_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed!:\n" + e.getMessage());
        }

        if (connection != null) {
            System.out.println("SUCCESS!!!! You made it, take control  of your database now!");
        } else {
            System.out.println("FAILURE! Failed to make connection!");
        }

        return connection;
    }

    public void disconnectJDBCToAWSEC2() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //only for test
//    public static void main (String[] args) {
//        new DBConnection().connectJDBCToAWSEC2();
//    }

}
