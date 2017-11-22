package com.neu.bsds.linyu.Server.DAO;

import com.neu.bsds.linyu.Server.Connection.DBConnection;
import com.neu.bsds.linyu.Server.DataType.ServerMetric;

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

}

