package com.whoops.jdbc;

import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author: whoops
 * @date: 2021/8/11 - 15:36
 * @description:
 */
public class jdbcConnectionTest {

    //方式一
    @Test
    public void testConnection() throws SQLException {
        Driver driver  = new com.mysql.cj.jdbc.Driver();

        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&characterEncoding=utf-8";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","root");

        Connection connect = driver.connect(url, info);
        System.out.println(connect);

    }

    //方式二：使用反射
    @Test
    public void testConnection2() throws Exception {
        //反射的源头
        Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&characterEncoding=utf-8";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","root");

        Connection conn = driver.connect(url, info);
        System.out.println(conn);

    }

    //方式三：
    @Test
    public void testConnection3() throws Exception {
        Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&characterEncoding=utf-8";
        String user = "root";
        String password = "root";

        DriverManager.registerDriver(driver);
        Connection conn = DriverManager.getConnection(url, user, password);

        System.out.println(conn);
    }

    //方式四：
    @Test
    public void testConnection4() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&characterEncoding=utf-8";
        String user = "root";
        String password = "root";

        Connection conn = DriverManager.getConnection(url, user, password);

        System.out.println(conn);
    }

    //方式五：使用匹配文件
    @Test
    public void testConnection5() throws Exception {

        InputStream is = jdbcConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();

        properties.load(is);//加载配置文件

        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        Class.forName(driver);//加载驱动

        //获取连接
        Connection conn = DriverManager.getConnection(url, username, password);
        System.out.println(conn);

    }

}
