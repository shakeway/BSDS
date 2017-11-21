package com.neu.bsds.linyu.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by linyuyu on 11/20/17.
 */
public class DBConnection {
    private static String PUBLIC_DNS = "postgresql.cbouijouwxsf.us-west-2.rds.amazonaws.com";
    private static String PORT = "5432";
    private static String DATABASE = "mydevdb";
    private static String REMOTE_DATABASE_USERNAME = "uuuu";
    private static String DATABASE_USER_PASSWORD = "12345678";

    public static Connection getConnection() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        Connection connection = null;
        try {
            connection = DriverManager.
                    getConnection("jdbc:postgresql://" + PUBLIC_DNS + ":" + PORT + "/" + DATABASE, REMOTE_DATABASE_USERNAME, DATABASE_USER_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return connection;
    }

    public void disconnectJDBCToAWSEC2(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
