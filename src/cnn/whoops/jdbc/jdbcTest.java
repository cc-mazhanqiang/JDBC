package cnn.whoops.jdbc;

import java.sql.*;

/**
 * @author: whoops
 * @date: 2021/8/10 - 15:20
 * @description:
 */
public class jdbcTest {


    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            //1、加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2、用户信息和url
            String url = "jdbc:mysql://localhost:3306/jdbcstudy?serverTimezone=UTC&characterEncoding=utf8";
            String username = "root";
            String password = "root";
            //3、获取连接
            connection = DriverManager.getConnection(url, username, password);
            //4、获取连接对象
            statement = connection.createStatement();
            //5、执行sql,返回结果集
            String sql = "select * from users";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                System.out.print("id = " + resultSet.getString("id") + "，");
                System.out.print("name = " + resultSet.getString("name") + "，");
                System.out.print("password = " + resultSet.getString("password") + "，");
                System.out.print("email = " + resultSet.getString("email") + "，");
                System.out.print("birthday = " + resultSet.getString("birthday"));
                System.out.println();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6、释放资源
            try {
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (statement != null)
                    statement.clearBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
