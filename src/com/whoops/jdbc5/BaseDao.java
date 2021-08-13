package com.whoops.jdbc5;

import cnn.whoops.utils.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: whoops
 * @date: 2021/8/13 - 9:46
 * @description:
 */
public class BaseDao {

    /**
     * 通用的增删改操作
     * @param conn
     * @param sql
     * @param args  可变形参
     */
    public void update(Connection conn,String sql,Object...args){

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1,args[i]);
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(null,ps,null);
        }

    }


    /**
     * 获取一条数据的通用操作
     * @param conn
     * @param clazz 类的反射
     * @param sql
     * @param args
     * @param <T> 泛型
     * @return
     */
    public <T> T queryOne(Connection conn,Class<T> clazz,String sql,Object...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject( i + 1,args[i]);
            }

            rs = ps.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData metaData = rs.getMetaData();
            //获取元数据中的列数
            int columnCount = metaData.getColumnCount();


            while (rs.next()){
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object value = rs.getObject(i + 1);

                    //获取每列的列名
                    String columnName = metaData.getColumnLabel(i + 1);

                    //通过反射，给对象 t 的属性赋值
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t,value);
                }
                return  t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(null,ps,rs);
        }
        return  null;
    }


    /**
     * 查询所有的数据，返回一个集合
     * @param conn
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> List<T> queryAll(Connection conn, Class<T> clazz, String sql, Object...args){

        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1,args[i]);
            }

            resultSet = ps.executeQuery();

            //获取结果集中的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //获取元数据中的列数
            int columnCount = metaData.getColumnCount();

            ArrayList<T> list = new ArrayList<>();

            while (resultSet.next()){
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    Object value = resultSet.getObject(i + 1);

                    //获取元数据中每列的列名
                    String columnName = metaData.getColumnLabel(i + 1);
                    //通过反射，给对象 t 的属性赋值
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
            JDBCUtils.release(null,ps,resultSet);
        }
        return null;
    }
}

