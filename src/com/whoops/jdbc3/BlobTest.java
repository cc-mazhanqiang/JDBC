package com.whoops.jdbc3;

import cnn.whoops.utils.JDBCUtils;
import com.whoops.jdbc2.Customer;
import org.junit.Test;

import java.io.*;
import java.sql.*;

/**
 * @author: whoops
 * @date: 2021/8/12 - 16:08
 * @description:
 */
public class BlobTest {
    @Test
    public void test(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "insert into customers(name,email,birth,photo) values(?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,"小白");
            ps.setObject(2,"bai@126.com");
            ps.setObject(3,"1998-08-10");

            FileInputStream fis = new FileInputStream(new File("55.JPG"));
            ps.setBlob(4,fis);

            int i = ps.executeUpdate();
            if (i > 0){
                System.out.println("插入成功");
            }else{
                System.out.println("插入失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(conn,ps,null);
        }

    }


    //将数据库中的Blob文件下载下来，以文件的方式保存在本地
    @Test
    public void test2(){
        Connection conn = null;
        PreparedStatement ps = null;
        InputStream is = null;
        FileOutputStream fos = null;

        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth,photo from customers where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,19);


            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Date birth = rs.getDate("birth");
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);

                //将数据库中的Blob文件下载下来，以文件的方式保存在本地
                Blob photo = rs.getBlob("photo");
                is = photo.getBinaryStream();
                fos = new FileOutputStream(new File("photo.jpg"));
                byte[] bytes = new byte[1024];
                int len;
                while ((len = is.read(bytes)) != -1){
                    fos.write(bytes,0,len);
                }


            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            JDBCUtils.release(conn,ps,null);
        }

    }
}
