package cnn.whoops.jdbc;

import cnn.whoops.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author: whoops
 * @date: 2021/8/10 - 21:27
 * @description:
 */
public class TestTransaction {
    public static void main(String[] args) {

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = JDBCUtils.getConnection();

            // 关闭数据库的自动提交，自动会开启事务
            conn.setAutoCommit(false);//开启事务

            String sql1 = "update account set money = money - 100 where name = 'A'";
            pstm = conn.prepareStatement(sql1);
            pstm.executeUpdate();

            String sql2 = "update account set money = money + 100 where name = 'B'";
            pstm = conn.prepareStatement(sql2);
            pstm.executeUpdate();

            conn.commit();//提交事务
            System.out.println("事务执行成功");

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn,pstm,null);
        }

    }
}
