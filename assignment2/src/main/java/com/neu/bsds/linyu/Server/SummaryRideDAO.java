package com.neu.bsds.linyu.Server;

import com.neu.bsds.linyu.Client.DataType.SingleRideData;
import com.neu.bsds.linyu.Client.DataType.SummaryRideData;

import java.sql.*;

/**
 * Created by linyuyu on 10/22/17.
 */
public class SummaryRideDAO {

    //private static String TABLENAME = "summary_ride";
    private static String TABLENAME = "summary_table";

//    private static SummaryRideDAO instance = null;
//    protected DBConnection dbConnection;

//    protected SummaryRideDAO() {
//        dbConnection = new DBConnection();
//    }
//
//    public static SummaryRideDAO getInstance() {
//        if (instance == null) {
//            instance = new SummaryRideDAO();
//        }
//        return instance;
//    }

    public static void setTABLENAME(String TABLENAME) {
        SummaryRideDAO.TABLENAME = TABLENAME;
    }

    public static SummaryRideData get(String skierID, String dayNum) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection  connection = null;
        int vertical = 0;
        int liftNum = 0;

        try {
            connection = DBConnection.getConnection();

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
            preparedStatement.close();

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
        return new SummaryRideData(dayNum, skierID, liftNum, vertical);
    }

    public static void deleteRecord (String dayNum) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {

            connection = DBConnection.getConnection();

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

    public static void deleteRecord (String dayNum, String tableName) throws SQLException{
        SummaryRideDAO.setTABLENAME(tableName);
        SummaryRideDAO.deleteRecord(dayNum);
    }


    public static void update (SingleRideData sd) throws SQLException{
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            connection = DBConnection.getConnection();

            //mysql select statement
            String query = "INSERT INTO " + TABLENAME + " (vertical, liftNum, skierID, dayNum, id) VALUES(?, ? ,?, ?, ?)" +
                    " ON CONFLICT (id) DO UPDATE SET " + " vertical=  " + TABLENAME + ".vertical + EXCLUDED.vertical, " +
                    " liftNum= " + TABLENAME + ".liftNum + EXCLUDED.liftNum";

            //create mysql insert preparedstatement
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, sd.getVertical());
            preparedStatement.setInt(2, 1);
            preparedStatement.setString(3, sd.getSkierID());
            preparedStatement.setString(4, sd.getDayNum());
            preparedStatement.setString(5, sd.getDayNum() + "&" + sd.getLiftID());

            //execute the preparedStatement
            preparedStatement.executeUpdate();
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

//        try {
//            SummaryRideDAO.update(new SingleRideData("1", "1", "1", 5, "2"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        try {
//            res = SummaryRideDAO.get("1", "1").toString();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        System.out.println(res);

        try {
            SummaryRideDAO.deleteRecord("1");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
