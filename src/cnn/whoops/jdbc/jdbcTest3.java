package cnn.whoops.jdbc;

import cnn.whoops.utils.JDBCUtils;

import java.sql.*;

/**
 * @author: whoops
 * @date: 2021/8/10 - 20:20
 * @description:
 */
public class jdbcTest3 {
    public static void main(String[] args) {
        login("'or '1=1","'or '1=1");
//        login("zhangsan","123456");
    }

    public static void login(String username,String password){
        Connection conn = null;
        Statement st = null;

        try {
            conn = JDBCUtils.getConnection();
            st = conn.createStatement();
            String sql = "select * from users where name = '" + username + "' and password = '"+password+"'";

            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()){
                System.out.print("name = " + resultSet.getString("name") + "，");
                System.out.print("password = " + resultSet.getString("password") + "，");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn,st,null);
        }
    }
}
