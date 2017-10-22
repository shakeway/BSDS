package com.neu.bsds.linyu.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by linyuyu on 10/20/17.
 */
public class DBConnection {

    private String PUBLIC_DNS = "ec2-34-208-35-14.us-west-2.compute.amazonaws.com";
    private String PORT = "3306";
    private String DATABASE = "dbTest";
    private String REMOTE_DATABASE_USERNAME = "remoteu";
    private String DATABASE_USER_PASSWORD = "localhost";

    public void connectJDBCToAWSEC2() {


        System.out.println("----MySQL JDBC Connection Testing -------");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        System.out.println("MySQL JDBC Driver Registered!");
        Connection connection = null;

        try {
            connection = DriverManager.
                    getConnection("jdbc:mysql://" + PUBLIC_DNS + ":" + PORT + "/" + DATABASE, REMOTE_DATABASE_USERNAME, DATABASE_USER_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed!:\n" + e.getMessage());
        }

        if (connection != null) {
            System.out.println("SUCCESS!!!! You made it, take control     your database now!");
        } else {
            System.out.println("FAILURE! Failed to make connection!");
        }

    }

    public static void main(String[] args) {
        DBConnection dbc = new DBConnection();
        dbc.connectJDBCToAWSEC2();
    }
}
