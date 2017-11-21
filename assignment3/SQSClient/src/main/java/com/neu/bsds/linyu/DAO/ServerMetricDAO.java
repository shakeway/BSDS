package com.neu.bsds.linyu.DAO;

import com.neu.bsds.linyu.DataType.ServerMetric;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linyuyu on 11/20/17.
 */
public class ServerMetricDAO {
    private static final String TABLENAME = "server_table";
    private static final String INSERT_QUERY = "INSERT INTO " + TABLENAME + " (responseTime, errorNum, flag)" + " VALUES (?, ?, ?)";


    public  static String getTABLENAME() {
        return TABLENAME;
    }

    public  static void insert (ServerMetric serverMetric) throws SQLException {

        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();

            //create mysql insert preparedstatement
            preparedStatement = connection.prepareStatement(INSERT_QUERY);
            preparedStatement.setInt(1, serverMetric.getResponseTime());
            preparedStatement.setInt(2, serverMetric.getErrorNum());
            preparedStatement.setInt(3, serverMetric.getFlag());

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

    public static void batchPost(List<ServerMetric> cachedPost) throws SQLException{
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();

            //create mysql insert preparedstatement
            preparedStatement = connection.prepareStatement(INSERT_QUERY);
            for (ServerMetric serverMetric: cachedPost) {
                preparedStatement.setInt(1, serverMetric.getResponseTime());
                preparedStatement.setInt(2, serverMetric.getErrorNum());
                preparedStatement.setInt(3, serverMetric.getFlag());
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


    public static void main(String[] args) {
        //SingleRideDAO.insert(new SingleRideData("1","2","3", 4, "5"));
        Connection connection = DBConnection.getConnection();
//
//        try {
//            insert(new ServerMetric(1, 1, 1));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        List<ServerMetric> test = new ArrayList<ServerMetric>();
        test.add(new ServerMetric(2, 2, 2));
        test.add(new ServerMetric(3, 3, 3));
        try {
            batchPost(test);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
