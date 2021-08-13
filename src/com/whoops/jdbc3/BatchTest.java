package com.whoops.jdbc3;

import cnn.whoops.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author: whoops
 * @date: 2021/8/12 - 17:04
 * @description:
 */
public class BatchTest {


    //方式一：
    @Test
    public void test(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {

            long start = System.currentTimeMillis();

            conn = JDBCUtils.getConnection();

            String sql = "insert into goods(name) values(?)";
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < 20000; i++) {
                ps.setString(1,"name_" + i);
                ps.executeUpdate();
            }

            long end = System.currentTimeMillis();
            System.out.println("所花费的时间为：" +(end - start));//673034

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn,ps,null);
        }

    }


    /**
     * 方式二：
     * - addBatch(String)：添加需要批量处理的SQL语句或是参数；
     * - executeBatch()：执行批量处理语句；
     * - clearBatch():清空缓存的数据
     *
     *  修改2：mysql服务器默认是关闭批处理的，我们需要通过一个参数，让mysql开启批处理的支持。
     *  		 ?rewriteBatchedStatements=true 写在配置文件的url后面
     */
    @Test
    public void test2(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {

            long start = System.currentTimeMillis();

            conn = JDBCUtils.getConnection();


            String sql = "insert into goods(name) values(?)";
            ps = conn.prepareStatement(sql);
            for (int i = 1; i <= 20000; i++) {
                ps.setString(1,"name_" + i);

                //攒数据
                ps.addBatch();
                if (i % 500 == 0){
                    //执行batch
                    ps.executeBatch();
                    //清空batch
                    ps.clearBatch();
                }

            }


            long end = System.currentTimeMillis();
            System.out.println("所花费的时间为：" +(end - start));//4113

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn,ps,null);
        }

    }

    //方式三：
    @Test
    public void test3(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {

            long start = System.currentTimeMillis();

            conn = JDBCUtils.getConnection();

            conn.setAutoCommit(false);

            String sql = "insert into goods(name) values(?)";
            ps = conn.prepareStatement(sql);
            for (int i = 1; i <= 20000; i++) {
                ps.setString(1,"name_" + i);

                //攒数据
                ps.addBatch();
                if (i % 500 == 0){
                    //执行batch
                    ps.executeBatch();
                    //清空batch
                    ps.clearBatch();
                }

            }

            conn.commit();

            long end = System.currentTimeMillis();
            System.out.println("所花费的时间为：" +(end - start));//3123

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn,ps,null);
        }

    }

}
