package com.neu.bsds.linyu.Server;

import java.sql.*;

/**
 * Created by linyuyu on 10/22/17.
 */
public class SummaryRideDAO {
    private static Connection connection;
    //private static String TABLENAME = "summary_ride";
    private static String TABLENAME = "summary_test";

    public static String get(String skierID, String dayNum) throws SQLException {
        PreparedStatement preparedStatement = null;
        connection = null;
        int vertical = 0;
        int liftNum = 0;

        try {
            connection = new DBConnection().connectJDBCToAWSEC2();

            //mysql select statement
            String query = "SELECT * FROM " + TABLENAME + " WHERE (skierID = ? and dayNum = ?)";

            //create mysql insert preparedstatement
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, skierID);
            preparedStatement.setString(2, dayNum);

            //execute the preparedStatement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                vertical = rs.getInt("vertical");
                liftNum = rs.getInt("liftNum");
            }
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
        return "vertical: " + vertical + "liftNum: " + liftNum;
    }

    public static void deleteRecord (String dayNum) throws SQLException {
        PreparedStatement preparedStatement = null;
        connection = null;
        try {

            connection = new DBConnection().connectJDBCToAWSEC2();

            //mysql insert statement
            String query = "DELETE FROM " + TABLENAME + " WHERE dayNum = ?";

            //create mysql insert preparedstatement
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dayNum);

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
        String res = "";
        try {
            res = SummaryRideDAO.get("1", "1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.print(res);
        try {
            SummaryRideDAO.deleteRecord("1");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
