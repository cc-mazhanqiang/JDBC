package com.whoops.jdbc6;

import cnn.whoops.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author: whoops
 * @date: 2021/8/13 - 10:53
 * @description:
 */
public class CustomerDaoImplTest {

    private CustomerDaoImpl dao = new CustomerDaoImpl();

    @Test
    public void insert() throws ParseException, SQLException {

        Connection conn = JDBCUtils.getConnection();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = "1996-08-10";
        Date parse = format.parse(date);
        java.sql.Date date1 = new java.sql.Date(parse.getTime());

        Customer cust = new Customer(24,"莘莘", "shenshen@qq.com", date1);

        dao.insert(conn, cust);


    }

    @Test
    public void delete() throws SQLException {
        Connection conn = JDBCUtils.getConnection();

        dao.delete(conn,12);
    }

    @Test
    public void update() throws SQLException, ParseException {
        Connection conn = JDBCUtils.getConnection();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = "1996-08-10";
        Date parse = format.parse(date);
        java.sql.Date date1 = new java.sql.Date(parse.getTime());
        Customer cust = new Customer(20, "允儿", "yuner@qq.com", date1);
        dao.update(conn,cust);
        System.out.println("修改成功");

    }

    @Test
    public void findOne() throws SQLException {

        Connection conn = JDBCUtils.getConnection();
        Customer customer = dao.findOne(conn, 20);
        System.out.println(customer);
    }

    @Test
    public void findAll() throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        List<Customer> list = dao.findAll(conn);
        list.forEach(s -> System.out.println(s));
    }
}