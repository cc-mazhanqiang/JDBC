package com.whoops.jdbc2;

import cnn.whoops.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author: whoops
 * @date: 2021/8/11 - 21:07
 * @description:
 *
 * 通用的增删改操作
 *
 */
public class updateTest {

    @Test
    public void testUpdate(){
//        String sql = "delete from customers where id = ?";
//        update(sql,3);
//        System.out.println("删除成功！");

        String sql = "update `order` set order_name = ? where order_id = ?";
        update(sql,"DD","2");
        System.out.println("修改成功");

    }

    public void update(String sql,Object ...args){
        //获取连接
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();

            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1,args[i]);
            }

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {

            JDBCUtils.release(conn,ps,null);
        }

    }

}
