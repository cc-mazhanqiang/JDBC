package cnn.whoops.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author: whoops
 * @date: 2021/8/10 - 22:30
 * @description:
 */
public class C3P0Utils {

    private static DataSource dataSource;

    static {

        dataSource = new ComboPooledDataSource("MySQL");

    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


    //释放连接资源
    public static void release(Connection conn, Statement stmt, ResultSet rs){
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
