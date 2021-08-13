package com.whoops.jdbc7;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author: whoops
 * @date: 2021/8/13 - 15:34
 * @description:
 */
public class DruidConnTest {

    private static DataSource dataSource = null;
    static{
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            Properties properties = new Properties();
            properties.load(is);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        Connection conn = dataSource.getConnection();
        return conn;
    }

    public static void release(Connection conn, PreparedStatement ps, ResultSet rs){
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
