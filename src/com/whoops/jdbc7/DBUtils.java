package com.whoops.jdbc7;

import com.whoops.jdbc5.Customer;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: whoops
 * @date: 2021/8/13 - 15:52
 * @description:
 *
 * commons-dbutils 是 Apache 组织提供的一个开源 JDBC工具类库，它是对JDBC的简单封装，
 * 学习成本极低，并且使用dbutils能极大简化jdbc编码的工作量，同时也不会影响程序的性能。
 *
 * - org.apache.commons.dbutils.QueryRunner
 * - org.apache.commons.dbutils.ResultSetHandler
 * - 工具类：org.apache.commons.dbutils.DbUtils
 */
public class DBUtils {


    /**
     * 测试插入
     */
    @Test
    public void test() {

        Connection conn = null;

        try {
            QueryRunner runner = new QueryRunner();

            conn = DruidConnTest.getConnection();
            String sql = "insert into customers(name,email,birth)values(?,?,?)";

            int count = runner.update(conn, sql, "古力娜扎", "gulinazha@qq.com", "2021-08-10");
            if (count > 0){
                System.out.println("插入成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DruidConnTest.release(conn,null,null);
        }
    }


    /**
     * 测试查询一条数据
     */
    @Test
    public void test2(){
        Connection conn = null;
        try {
            QueryRunner queryRunner = new QueryRunner();

            conn = DruidConnTest.getConnection();
            String sql = "select id,name,email,birth from customers where id = ?";

            //处理结果集
            BeanHandler<Customer> handler = new BeanHandler<Customer>(Customer.class);
            Customer customer = queryRunner.query(conn, sql, handler, 23);
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DruidConnTest.release(conn,null,null);
        }

    }

    /**
     * 测试查询多条数据
     */
    @Test
    public void test3(){

        Connection conn = null;

        try {
            QueryRunner queryRunner = new QueryRunner();

            conn = DruidConnTest.getConnection();
            String sql = "select id,name,email,birth from customers";

            BeanListHandler<Customer> handler = new BeanListHandler<>(Customer.class);

            List<Customer> list = queryRunner.query(conn, sql, handler);
            list.forEach(System.out::println);//t -> System.out.println(t)
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DruidConnTest.release(conn,null,null);
        }
    }


    /**
     * 使用map测试一条查询数据
     */
    @Test
    public void test4(){

        Connection conn = null;

        try {
            QueryRunner queryRunner = new QueryRunner();

            conn = DruidConnTest.getConnection();
            String sql = "select id,name,email,birth from customers where id = ?";

            MapHandler handler = new MapHandler();

            Map<String, Object> map = queryRunner.query(conn, sql, handler,23);
            System.out.println(map);
//            for (Map.Entry<String, Object> entry : map.entrySet()) {
//                System.out.println(entry);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DruidConnTest.release(conn,null,null);
        }
    }

    /**
     * 使用map测试多条查询数据
     */
    @Test
    public void test5(){

        Connection conn = null;

        try {
            QueryRunner queryRunner = new QueryRunner();

            conn = DruidConnTest.getConnection();
            String sql = "select id,name,email,birth from customers";

            MapListHandler handler = new MapListHandler();

            List<Map<String, Object>> mapList = queryRunner.query(conn, sql, handler);
            mapList.forEach(System.out::println);
//            for (Map<String, Object> stringObjectMap : mapList) {
//                System.out.println(stringObjectMap);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DruidConnTest.release(conn,null,null);
        }
    }


    /**
     * 处理函数型sql语句的查询
     */
    @Test
    public void test6(){

        Connection conn = null;

        try {
            QueryRunner queryRunner = new QueryRunner();

            conn = DruidConnTest.getConnection();
            String sql = "select count(*) from customers";

            ScalarHandler handler = new ScalarHandler();

            long count = (long) queryRunner.query(conn, sql, handler);
            System.out.println(count);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DruidConnTest.release(conn,null,null);
        }
    }

    /**
     * 处理函数型sql语句的查询
     */
    @Test
    public void test7(){

        Connection conn = null;

        try {
            QueryRunner queryRunner = new QueryRunner();

            conn = DruidConnTest.getConnection();
            String sql = "select max(birth) from customers";

            ScalarHandler handler = new ScalarHandler();

            Date maxBirth = (Date) queryRunner.query(conn, sql, handler);
            System.out.println(maxBirth);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DruidConnTest.release(conn,null,null);
        }
    }
}


