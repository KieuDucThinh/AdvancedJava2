/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltc.btl_javafx.DAO;

import com.ltc.btl_javafx.database.ConnectionPool;
import com.ltc.btl_javafx.database.ConnectionPoolImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author TUF
 */
public class BillFunctionImpl implements BillFunction {

    private static ConnectionPool cp;

    //Doi tuong ket noi
    private Connection con;

    private static volatile BillFunctionImpl instance;

    public static BillFunctionImpl getInstance() {
        if (instance == null) {
            Class var0 = BillFunctionImpl.class;
            synchronized (BillFunctionImpl.class) {
                if (instance == null) {
                    instance = new BillFunctionImpl(cp);
                }
            }
        }

        return instance;
    }

    public BillFunctionImpl(ConnectionPool cp) {
        // TODO Auto-generated constructor stub
        //Xac dinh bo quan ly ket noi de lam viec
        if (cp == null) {
            cp = new ConnectionPoolImpl();
        }

        try {
            //Xin ket noi de lam viec
            this.con = cp.getConnection("User");

            //Kiem tra che do thuc thi tu dong
            if (this.con.getAutoCommit()) {
                //Cham dut che do thuc thi tu dong

                this.con.setAutoCommit(false);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean exe(PreparedStatement pre) {
        //pre đã được buiên dịch và truyền giá trị đầy đủ cho các tham số
        if (pre != null) {
            try {
                int results = pre.executeUpdate();
                if (results == 0) {
                    this.con.rollback();
                    return false;
                }

                //Xac nhan thuc thi sau cung
                this.con.commit();

                return true;

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                try {
                    this.con.rollback();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } finally {
                pre = null;
            }
        }

        return false;
    }
    
    @Override
    public boolean isExistingByTenant(String cond) {
        boolean flag = false;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT MaHD FROM hoadon WHERE MaK=?;");
        try {
            PreparedStatement pre = this.con.prepareStatement(sql.toString());

            pre.setString(1, cond);

            ResultSet rs = pre.executeQuery();
            return ((rs!=null) && rs.next());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                this.con.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        return flag;
    }
    
}
