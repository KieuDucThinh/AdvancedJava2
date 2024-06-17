/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltc.btl_javafx.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

/**
 *
 * @author TUF
 */
public class ConnectionPoolImpl implements ConnectionPool {
    //Trinh dieu khien lam viec

    private String driver;

    //Tai khoan ket noi
    private String username;
    private String userpass;

    //Duong dan thuc thi
    private String url;

    //Doi tuong luu tru ket noi
    private Stack<Connection> pool;

    public ConnectionPoolImpl() {
        // TODO Auto-generated method stub
        //Xac dinh trinh dieu khien
        this.driver = "com.mysql.cj.jdbc.Driver";

        //Nap trinh dieu khien
        try {
            Class.forName(this.driver);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //Xac dinh tai khoan
        this.username = "root";
        this.userpass = "";

        //Xac dinh duong dan thuc thi
//		this.url = "jdbc:mysql://localhost:3306/newschema";
        this.url = "jdbc:mysql://localhost:3306/quanlychungcumini?allowMultiQueries=true";

        //Khoi tao bo nho doi tuong luu tru
        this.pool = new Stack<>();
    }

    @Override
    public Connection getConnection(String objectName) throws SQLException {
        // TODO Auto-generated method stub

        if (this.pool.isEmpty()) {

            System.out.println(objectName + " da khoi tao 1 ket noi moi");
            return DriverManager.getConnection(this.url, this.username, this.userpass);
        } else {
            System.out.println(objectName + "da lay ra mot ket noi");
            return this.pool.pop();
        }
    }

    @Override
    public void releaseConnection(Connection con, String objectName) throws SQLException {
        // TODO Auto-generated method stub

        System.out.println(objectName + "da tra ve 1 ket noi");
        this.pool.push(con);
    }

    protected void finalize() throws Throwable {
        this.pool.clear();

        this.pool = null;

        System.out.println("CPool is Closed");
    }
}
