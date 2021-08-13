package com.whoops.jdbc6;

import java.sql.Connection;
import java.util.List;

/**
 * @author: whoops
 * @date: 2021/8/13 - 10:21
 * @description:
 */
public interface CustomerDao {
    /**
     * 插入一条数据
     * @param conn
     * @param cust
     */
   public void insert(Connection conn, Customer cust);

    /**
     * 删除一条数据
     * @param conn
     * @param id
     */
   public void delete(Connection conn, int id);

    /**
     * 修改一条数据
     * @param conn
     * @param cust
     */
   public void update(Connection conn, Customer cust);

    /**
     * 查询一条数据
     * @param conn
     * @param id
     * @return
     */
   public Customer findOne(Connection conn, int id);

    /**
     * 查询所有的数据
     * @param conn
     * @return
     */
   public List<Customer> findAll(Connection conn);
}
