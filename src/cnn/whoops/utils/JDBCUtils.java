package cnn.whoops.utils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author: whoops
 * @date: 2021/8/10 - 16:26
 * @description:
 */
public class JDBCUtils {

    private static String driver = null;
    private static String url = null;
    private static String username = null;
    private static String password = null;

    static {
        try {
            Properties properties = new Properties();
            InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(is);

            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");

            //加载驱动
            Class.forName(driver);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //获取连接
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }


    //释放连接资源
    public static void release(Connection conn, Statement st, ResultSet rs){
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (st != null)
                st.close();
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
