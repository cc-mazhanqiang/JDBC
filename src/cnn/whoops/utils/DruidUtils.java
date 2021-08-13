package cnn.whoops.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author: whoops
 * @date: 2021/8/10 - 22:51
 * @description:
 */
public class DruidUtils {
    private static DataSource dataSource;

    static {

        InputStream is = DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        Properties properties = new Properties();

        try {
            properties.load(is);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    // 定义公有的得到数据源的方法
    public static DataSource getDataSource(){
        return dataSource;
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
