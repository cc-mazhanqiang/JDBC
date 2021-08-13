package com.whoops.jdbc2;

import cnn.whoops.utils.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: whoops
 * @date: 2021/8/12 - 14:14
 * @description: 适用于所有的表的查询操作
 */
public class AllQuery {

    @Test
    public void test2(){
        String sql = "select id,name,email,birth from customers where id >= ?";
        List<Customer> customerList = queryList(Customer.class, sql, 1);
        customerList.forEach(s -> System.out.println(s));//System.out::println

        String sql1 = "select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id >= ?";
        List<Order> orderList = queryList(Order.class, sql1, 1);
        orderList.forEach(System.out::println);
    }

    /**
     * 针对不同表进行查询的通用操作，返回的是集合
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> List<T> queryList(Class<T> clazz, String sql, Object... args) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();

            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();

            //获取结果集的元数据
            ResultSetMetaData metaData = rs.getMetaData();
            //获取元数据中的列数
            int columnCount = metaData.getColumnCount();

            ArrayList<T> list = new ArrayList<>();

            while (rs.next()) {

                T t = clazz.newInstance();

                for (int i = 0; i < columnCount; i++) {
                    //获取每列的值
                    Object value = rs.getObject(i + 1);

                    //获取元数据每列的列名
                    String columnName = metaData.getColumnLabel(i + 1);

                    //使用反射，给 t 对象的属性赋值value
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, value);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn, ps, rs);
        }
        return null;
    }


    @Test
    public void test() {

        String sql = "select id,name,email,birth from customers where id = ?";
        Customer customer = query(Customer.class, sql, 12);
        System.out.println(customer);

        String sql1 = "select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id = ?";
        Order order = query(Order.class, sql1, 2);
        System.out.println(order);

    }

    /**
     * 针对不同表的通用查询一条数据
     *
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> T query(Class<T> clazz, String sql, Object... args) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtils.getConnection();

            ps = conn.prepareStatement(sql);

            //给sql语句的占位符设置数据
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();

            //获取结果集的元数据
            ResultSetMetaData metaData = rs.getMetaData();
            //获取元数据的列数
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {

                T t = clazz.newInstance();

                for (int i = 0; i < columnCount; i++) {
                    Object value = rs.getObject(i + 1);
                    //获取每列的列名
                    String columnLabel = metaData.getColumnLabel(i + 1);

                    //通过反射，给 t 对象的属性赋值为value
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, value);
                }

                return t;

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            JDBCUtils.release(conn, ps, rs);
        }

        return null;
    }
}
