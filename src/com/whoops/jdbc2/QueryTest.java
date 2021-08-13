package com.whoops.jdbc2;

import cnn.whoops.utils.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @author: whoops
 * @date: 2021/8/11 - 21:51
 * @description:
 *
 * 某张表通用的查询操作
 *
 */
public class QueryTest {

    @Test
    public void test(){
        String sql = "select id,name,email,birth from customers where id  = ?";
        Customer customer = testQuery(sql, 13);
        System.out.println(customer);
    }


    public Customer testQuery(String sql,Object...args){

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            conn = JDBCUtils.getConnection();

            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1,args[i]);
            }

            resultSet = ps.executeQuery();

            //获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();

            //通过元数据获取结果集中的列数
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()){
                Customer cust = new Customer();
                for (int i = 0; i < columnCount; i++) {
                    //获取每列的值
                    Object columnValue = resultSet.getObject(i + 1);

                    //通过元数据获取每列的列名
//                    String columnName = metaData.getColumnName(i + 1);
                    String columnName = metaData.getColumnLabel(i + 1);

                    //通过反射，给cust对象的属性赋值为columnValue
                    Field field = Customer.class.getDeclaredField(columnName);//getDeclaredField:获取Customer类中声明的属性
                    field.setAccessible(true);//设置私有的属性也可以访问
                    field.set(cust,columnValue);
                }
                return cust;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.release(conn,ps,resultSet);
        }

        return null;
    }

}
