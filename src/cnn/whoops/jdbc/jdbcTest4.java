package cnn.whoops.jdbc;

import cnn.whoops.utils.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author: whoops
 * @date: 2021/8/10 - 20:43
 * @description:
 */
public class jdbcTest4 {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = DruidUtils.getConnection();

            String sql = "insert into users(id,name,password,email,birthday) values(?,?,?,?,?)";

            //预编译SQL
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1,7);
            pstm.setString(2,"夏");
            pstm.setString(3,"123");
            pstm.setString(4,"5454354354@qq.com");
            pstm.setDate(5,new java.sql.Date(new Date().getTime()));

            int i = pstm.executeUpdate();
            if (i > 0){
                System.out.println("插入成功！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtils.release(conn,pstm,null);
        }

    }
}
