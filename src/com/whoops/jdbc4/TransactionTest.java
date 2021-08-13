package com.whoops.jdbc4;

import cnn.whoops.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author: whoops
 * @date: 2021/8/12 - 21:21
 * @description:
 */
public class TransactionTest {

    @Test
    public void test(){
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            conn.setAutoCommit(false);

            String sql1 = "update user_table set balance = balance - 100 where user = ?";
            update(conn,sql1,"AA");

//            System.out.println( 10 / 0 );

            String sql2 = "update user_table set balance = balance + 100 where user = ?";
            update(conn,sql2,"BB");

            conn.commit();

            System.out.println("转账成功！");

        } catch (SQLException e) {

            //如果出现异常则回滚数据
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace();
        } finally {

            //主要针对数据库连接池
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            JDBCUtils.release(conn,null,null);
        }
    }

    public void update(Connection conn,String sql,Object ...args){
        PreparedStatement ps = null;
        try {

            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1,args[i]);
            }

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

            JDBCUtils.release(null,ps,null);
        }

    }

}
