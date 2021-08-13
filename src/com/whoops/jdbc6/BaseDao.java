package com.whoops.jdbc6;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: whoops
 * @date: 2021/8/13 - 9:46
 * @description:
 */
public class BaseDao<T> {

    private QueryRunner runner = new QueryRunner();
    private Class<T> clazz = null;

    {
        //获取当前BaseDao的子类继承的父类中的泛型
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) genericSuperclass;
        //获取父类的泛型参数
        Type[] types = paramType.getActualTypeArguments();
        clazz = (Class<T>) types[0];//泛型的第一个参数

    }

    /**
     * 通用的增删改操作
     *
     * @param conn
     * @param sql
     * @param args 可变形参
     */
    public void update(Connection conn, String sql, Object... args) {

//        PreparedStatement ps = null;
//        try {
//            ps = conn.prepareStatement(sql);
//
//            for (int i = 0; i < args.length; i++) {
//                ps.setObject(i + 1,args[i]);
//            }
//
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            JDBCUtils.release(null,ps,null);
//        }

        try {
            runner.update(conn, sql, args);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * 获取一条数据的通用操作
     *
     * @param conn
     * @param sql
     * @param args
     * @return
     */
    public T queryOne(Connection conn, String sql, Object... args) {
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            ps = conn.prepareStatement(sql);
//
//            for (int i = 0; i < args.length; i++) {
//                ps.setObject( i + 1,args[i]);
//            }
//
//            rs = ps.executeQuery();
//            //获取结果集的元数据
//            ResultSetMetaData metaData = rs.getMetaData();
//            //获取元数据中的列数
//            int columnCount = metaData.getColumnCount();
//
//
//            while (rs.next()){
//                T t = clazz.newInstance();
//                for (int i = 0; i < columnCount; i++) {
//                    Object value = rs.getObject(i + 1);
//
//                    //获取每列的列名
//                    String columnName = metaData.getColumnLabel(i + 1);
//
//                    //通过反射，给对象 t 的属性赋值
//                    Field field = clazz.getDeclaredField(columnName);
//                    field.setAccessible(true);
//                    field.set(t,value);
//                }
//                return  t;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            JDBCUtils.release(null,ps,rs);
//        }
//        return  null;

        //使用 dbutils API查询一条数据
        T t = null;
        try {
            t = runner.query(conn, sql, new BeanHandler<T>(clazz), args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }


    /**
     * 查询所有的数据，返回一个集合
     *
     * @param conn
     * @param sql
     * @param args
     * @return
     */
    public List<T> queryAll(Connection conn, String sql, Object... args) {

//        PreparedStatement ps = null;
//        ResultSet resultSet = null;
//
//        try {
//            ps = conn.prepareStatement(sql);
//
//            for (int i = 0; i < args.length; i++) {
//                ps.setObject(i + 1,args[i]);
//            }
//
//            resultSet = ps.executeQuery();
//
//            //获取结果集中的元数据
//            ResultSetMetaData metaData = resultSet.getMetaData();
//            //获取元数据中的列数
//            int columnCount = metaData.getColumnCount();
//
//            ArrayList<T> list = new ArrayList<>();
//
//            while (resultSet.next()){
//                T t = clazz.newInstance();
//                for (int i = 0; i < columnCount; i++) {
//                    Object value = resultSet.getObject(i + 1);
//
//                    //获取元数据中每列的列名
//                    String columnName = metaData.getColumnLabel(i + 1);
//                    //通过反射，给对象 t 的属性赋值
//                    Field field = clazz.getDeclaredField(columnName);
//                    field.setAccessible(true);
//                    field.set(t, value);
//                }
//                list.add(t);
//            }
//            return list;
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            JDBCUtils.release(null,ps,resultSet);
//        }
//        return null;
//    }

        //使用 dbutils API查询多条数据
        List<T> list = null;
        try {
            list = runner.query(conn, sql, new BeanListHandler<T>(clazz), args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

