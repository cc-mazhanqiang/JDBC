package com.whoops.jdbc2;

import cnn.whoops.utils.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @author: whoops
 * @date: 2021/8/11 - 22:30
 * @description:
 */
public class QueryTest2 {

    @Test
    public void test(){
        // 报 NoSuchFieldException: order_id 的异常
//        String sql = "select order_id,order_name,order_date from `order` where order_id = ?";
//        Order order = query(sql, 1);
//        System.out.println(order);

        // 需要给字段设置别名，对应 Order类中的属性名
        String sql = "select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id = ?";
        Order order = query(sql, 1);
        System.out.println(order);
    }


    public Order query(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1,args[i]);
            }

            rs = ps.executeQuery();

            //获取结果集的元数据
            ResultSetMetaData metaData = rs.getMetaData();
            //获取元数据的列数
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                Order order = new Order();
                for (int i = 0; i < columnCount; i++) {
                    //获取每列的值
                    Object columnValue = rs.getObject(i + 1);
                    //通过元数据获取每列的列名(表的列名)
                    //需要获取表的别名
//                    String columnName = metaData.getColumnName(i + 1); // 获取列的列名
                    String columnName = metaData.getColumnLabel(i + 1); // 获取列的别名，建议使用

                    //通过反射，给order对象的属性赋值为columnValue
                    Field field = Order.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(order, columnValue);
                }
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            JDBCUtils.release(conn,ps,rs);
        }

        return null;
    }

}
