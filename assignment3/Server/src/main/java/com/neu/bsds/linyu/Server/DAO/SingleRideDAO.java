package com.neu.bsds.linyu.Server.DAO;

import com.neu.bsds.linyu.Server.DataType.SingleRideData;
import com.neu.bsds.linyu.Server.Connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by linyuyu on 10/22/17.
 */
public class SingleRideDAO {

    private static final String TABLENAME = "test_table";
    private static final String INSERT_QUERY = "INSERT INTO " + TABLENAME + " (resortID, dayNum, skierID, liftID, timestamp)" + " VALUES (?, ?, ?, ?, ?)";


    public  static String getTABLENAME() {
        return TABLENAME;
    }

    public  static void insert (SingleRideData singleRideData) throws SQLException {

        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();

            //create mysql insert preparedstatement
            preparedStatement = connection.prepareStatement(INSERT_QUERY);
            preparedStatement.setString(1, singleRideData.getResortID());
            preparedStatement.setString(2, singleRideData.getDayNum());
            preparedStatement.setString(3, singleRideData.getSkierID());
            preparedStatement.setInt(4, singleRideData.getLiftID());
            preparedStatement.setString(5, singleRideData.getTimestamp());

            //execute the preparedStatement
            preparedStatement.execute();

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

    public static void batchPost(List<SingleRideData> cachedPost) throws SQLException{
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();

            //create mysql insert preparedstatement
            preparedStatement = connection.prepareStatement(INSERT_QUERY);
            for (SingleRideData singleRideData: cachedPost) {
                preparedStatement.setString(1, singleRideData.getResortID());
                preparedStatement.setString(2, singleRideData.getDayNum());
                preparedStatement.setString(3, singleRideData.getSkierID());
                preparedStatement.setInt(4, singleRideData.getLiftID());
                preparedStatement.setString(5, singleRideData.getTimestamp());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
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

    public static void deleteRecord (String dayNum) throws SQLException {
        SummaryRideDAO.deleteRecord(dayNum, SingleRideDAO.getTABLENAME());
    }

}
