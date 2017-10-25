package com.neu.bsds.linyu.Server;

import com.neu.bsds.linyu.Client.SingleRideData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by linyuyu on 10/22/17.
 */
public class SingleRideDAO {
    private static Connection connection;
    //private static String TABLENAME = "single_ride";
    private static String TABLENAME = "test_table";

    public static void insert (SingleRideData singleRideData) throws SQLException {
        PreparedStatement preparedStatement = null;
        connection = null;
        try {

            connection = new DBConnection().connectJDBCToAWSEC2();

            //mysql insert statement
            String query = "INSERT INTO " + TABLENAME + " (resortID, dayNum, skierID, liftID, timestamp)"
                    + " VALUES (?, ?, ?, ?, ?)";

            //create mysql insert preparedstatement
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, singleRideData.getResortID());
            preparedStatement.setString(2, singleRideData.getDayNum());
            preparedStatement.setString(3, singleRideData.getSkierID());
            preparedStatement.setInt(4, singleRideData.getLiftID());
            preparedStatement.setString(5, singleRideData.getTimestamp());

            //execute the preparedStatement
            preparedStatement.execute();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public static void main(String[] args) {
        //SingleRideDAO.insert(new SingleRideData("1","2","3", 4, "5"));
//        connection = new DBConnection().connectJDBCToAWSEC2();
//        try {
//            Statement stmt = connection.createStatement();
//            String query = "insert into test_table (resortID, dayNum, skierID, liftID, timestamp) values('1', '2', '3', 4, '7')";
//            stmt.executeUpdate(query);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
            SingleRideDAO.insert(new SingleRideData("1","1","1",1,"1"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
