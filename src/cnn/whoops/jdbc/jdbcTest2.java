package cnn.whoops.jdbc;

import cnn.whoops.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author: whoops
 * @date: 2021/8/10 - 16:43
 * @description:
 */
public class jdbcTest2 {
    public static void main(String[] args) {

        Connection conn = null;
        Statement st = null;

        try {
            conn = JDBCUtils.getConnection();
            st = conn.createStatement();
            String sql = " INSERT INTO `users`(`id`,`name`,`password`,`email`,`birthday`)" +
                    "VALUES(4,'莘莘','123456','shenshen@sina.com','1980-12-04')";

            int i = st.executeUpdate(sql);
            if (i > 0){
                System.out.println("插入成功！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn,st,null);
        }

    }
}
