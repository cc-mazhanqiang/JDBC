package com.whoops.jdbc6;

import java.sql.Connection;
import java.util.List;

/**
 * @author: whoops
 * @date: 2021/8/13 - 10:31
 * @description:
 */
public class CustomerDaoImpl extends BaseDao<Customer> implements CustomerDao {
    @Override
    public void insert(Connection conn, Customer cust) {
        String sql = "insert into customers(id,name,email,birth)values(?,?,?,?)";
        update(conn,sql,cust.getId(),cust.getName(),cust.getEmail(),cust.getBirth());
    }

    @Override
    public void delete(Connection conn, int id) {
        String sql = "delete from customers where id = ?";
        update(conn,sql,id);
    }

    @Override
    public void update(Connection conn, Customer cust) {
        String sql = "update customers set name = ?,email = ?,birth = ? where id = ?";
        update(conn,sql,cust.getName(),cust.getEmail(),cust.getBirth(),cust.getId());
    }

    @Override
    public Customer findOne(Connection conn, int id) {

        String sql = "select id,name,email,birth from customers where id = ?";
        Customer customer = queryOne(conn,sql,id);
        return  customer;
    }

    @Override
    public List<Customer> findAll(Connection conn) {
        String sql = "select id,name,email,birth from customers";
        List<Customer> list = queryAll(conn,sql);
        return list;
    }
}
