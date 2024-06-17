/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltc.btl_javafx.DAO;

import com.ltc.btl_javafx.database.ConnectionPool;
import com.ltc.btl_javafx.database.ConnectionPoolImpl;
import com.ltc.btl_javafx.model.HomeTown;
import com.ltc.btl_javafx.model.Member;
import com.ltc.btl_javafx.model.Room;
import com.ltc.btl_javafx.model.Tenant;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author TUF
 */
public class MemberFunctionImpl implements MemberFunction {

    private static ConnectionPool cp;

    //Doi tuong ket noi
    private Connection con;

    private static volatile MemberFunctionImpl instance;

    public static MemberFunctionImpl getInstance() {
        if (instance == null) {
            Class var0 = MemberFunctionImpl.class;
            synchronized (MemberFunctionImpl.class) {
                if (instance == null) {
                    instance = new MemberFunctionImpl(cp);
                }
            }
        }

        return instance;
    }

    public MemberFunctionImpl(ConnectionPool cp) {
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
    public boolean addMember(Member item) {
        if (this.isExisting(item)) {
            return false;
        }

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO thanhvien(");
        sql.append("MaTV, MaK, TenTV, GioiTinh, ");
        sql.append("NgaySinh, CCCD, SDT, Que");
        sql.append(")");
        sql.append("VALUES(?,?,?,?,?,?,?,?)");

        try {
            PreparedStatement pre = this.con.prepareStatement(sql.toString());
            pre.setString(1, item.getMemberID());
            pre.setString(2, item.getTenant().getTenantID());
            pre.setString(3, item.getName());
            pre.setString(4, item.getSex());
            pre.setDate(5, Date.valueOf(item.getBirthdate()));
            pre.setString(6, item.getCitizenID());
            pre.setString(7, item.getPhoneNum());
            pre.setString(8, item.getPlaceOrigin());

            return this.exe(pre);
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

        return false;
    }

    private boolean isExisting(Member item) {
        boolean flag = false;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT MaK FROM khachthue WHERE CCCD=?;");
        sql.append("SELECT MaK FROM khachthue WHERE SDT=?;");
        sql.append("SELECT MaTV FROM thanhvien WHERE CCCD = ?;");
        sql.append("SELECT MaTV FROM thanhvien WHERE (SDT=?) AND (SDT <> ?);");
        try {
            PreparedStatement pre = this.con.prepareStatement(sql.toString());

            pre.setString(1, item.getCitizenID());
            pre.setString(2, item.getPhoneNum());
            pre.setString(3, item.getCitizenID());
            pre.setString(4, item.getPhoneNum());
            pre.setString(5, "");

            boolean results = pre.execute();
            do {
                if (results) {
                    ResultSet rs = pre.getResultSet();
                    if ((rs != null) && (rs.next())) {
                        flag = true;
                        break;
                    }

                    results = pre.getMoreResults(PreparedStatement.KEEP_CURRENT_RESULT);
                }
            } while (results);
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

    @Override
    public boolean editMember(Member item) {
        if (this.isExisting2(item)) {
            return false;
        }

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE thanhvien SET ");
        sql.append("TenTV=?, GioiTinh=?, ");
        sql.append("NgaySinh=?, CCCD=?, SDT=?, Que=?");
        sql.append(" WHERE MaTV = ?");
        sql.append("");

        try {
            PreparedStatement pre = this.con.prepareStatement(sql.toString());
            pre.setString(1, item.getName());
            pre.setString(2, item.getSex());
            pre.setDate(3, Date.valueOf(item.getBirthdate()));
            pre.setString(4, item.getCitizenID());
            pre.setString(5, item.getPhoneNum());
            pre.setString(6, item.getPlaceOrigin());
            pre.setString(7, item.getMemberID());

            return this.exe(pre);
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

        return false;
    }

    private boolean isExisting2(Member item) {
        boolean flag = false;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT MaK FROM khachthue WHERE CCCD=?;");
        sql.append("SELECT MaK FROM khachthue WHERE SDT=?;");
        sql.append("SELECT MaTV FROM thanhvien WHERE (MaTV != ?) AND (CCCD = ?);");
        sql.append("SELECT MaTV FROM thanhvien WHERE ((MaTV != ?) AND (SDT = ?)) AND (SDT <> ?);");
        try {
            PreparedStatement pre = this.con.prepareStatement(sql.toString());

            pre.setString(1, item.getCitizenID());
            pre.setString(2, item.getPhoneNum());
            pre.setString(3, item.getMemberID());
            pre.setString(4, item.getCitizenID());
            pre.setString(5, item.getMemberID());
            pre.setString(6, item.getPhoneNum());
            pre.setString(7, "");

            boolean results = pre.execute();
            do {
                if (results) {
                    ResultSet rs = pre.getResultSet();
                    if ((rs != null) && (rs.next())) {
                        flag = true;
                        break;
                    }

                    results = pre.getMoreResults(PreparedStatement.KEEP_CURRENT_RESULT);
                }
            } while (results);
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

    @Override
    public boolean delMember(Member item) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM thanhvien "); 
        sql.append(" WHERE MaTV = ?");
        sql.append("");

        try {
            PreparedStatement pre = this.con.prepareStatement(sql.toString());
            pre.setString(1, item.getMemberID());

            return this.exe(pre);
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

        return false;
    }
    
    @Override
    public ObservableList<Member> selectAllMember() {
        ObservableList<Member> list = FXCollections.observableArrayList();
        String sql = "SELECT MaTV, khachthue.MaP, khachthue.MaT, TenTV, thanhvien.GioiTinh, thanhvien.NgaySinh, thanhvien.CCCD, thanhvien.SDT, thanhvien.Que FROM khachthue INNER JOIN thanhvien ON thanhvien.MaK = khachthue.MaK ORDER BY khachthue.MaT, khachthue.MaP";

        Member item = null;
        Tenant tenant = null;
        Room room = null;
        HomeTown homeTown = null;
        LocalDate localDate = null;
        try {
            PreparedStatement pre = this.con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    item = new Member();
                    item.setMemberID(rs.getString("MaTV"));
                    item.setName(rs.getString("TenTV"));
                    item.setSex(rs.getString("thanhvien.GioiTinh"));
                    localDate = rs.getDate("thanhvien.NgaySinh").toLocalDate();
                    item.setBirthdate(localDate);
                    item.setCitizenID(rs.getString("thanhvien.CCCD"));
                    item.setPhoneNum(rs.getString("thanhvien.SDT"));
                    item.setPlaceOrigin(rs.getString("thanhvien.Que"));
                    tenant = new Tenant();
                    room = new Room();
                    room.setRoomID(rs.getString("khachthue.MaP"));
                    tenant.setRoom(room);
                    homeTown = new HomeTown();
                    homeTown.setTownID(rs.getString("khachthue.MaT"));
                    tenant.setHomeTown(homeTown);
                    item.setTenant(tenant);
                    list.add(item);
                }
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            //Quay tro lai trang thai an toan
            try {
                this.con.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public ObservableList<Member> selectMemberByCondition(String cond) {
        ObservableList<Member> list = FXCollections.observableArrayList();
        String sql = "SELECT MaTV, khachthue.MaP, khachthue.MaT, TenTV, thanhvien.GioiTinh, thanhvien.NgaySinh, thanhvien.CCCD, thanhvien.SDT, thanhvien.Que FROM khachthue INNER JOIN thanhvien ON thanhvien.MaK = khachthue.MaK WHERE (MaTV LIKE ?) OR (khachthue.MaP LIKE ?) OR (khachthue.MaT LIKE ?) OR (TenTV LIKE ?) OR (thanhvien.GioiTinh LIKE ?) OR (thanhvien.NgaySinh LIKE ?) OR (thanhvien.CCCD LIKE ?) OR (thanhvien.SDT LIKE ?) OR (thanhvien.Que LIKE ?)";

        Member item = null;
        Tenant tenant = null;
        Room room = null;
        HomeTown homeTown = null;
        LocalDate localDate = null;
        try {
            PreparedStatement pre = this.con.prepareStatement(sql);
            pre.setString(1, "%" + cond + "%");
            pre.setString(2, "%" + cond + "%");
            pre.setString(3, "%" + cond + "%");
            pre.setString(4, "%" + cond + "%");
            pre.setString(5, "%" + cond + "%");
            pre.setString(6, "%" + cond + "%");
            pre.setString(7, "%" + cond + "%");
            pre.setString(8, "%" + cond + "%");
            pre.setString(9, "%" + cond + "%");

            ResultSet rs = pre.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    item = new Member();
                    item.setMemberID(rs.getString("MaTV"));
                    item.setName(rs.getString("TenTV"));
                    item.setSex(rs.getString("thanhvien.GioiTinh"));
                    localDate = rs.getDate("thanhvien.NgaySinh").toLocalDate();
                    item.setBirthdate(localDate);
                    item.setCitizenID(rs.getString("thanhvien.CCCD"));
                    item.setPhoneNum(rs.getString("thanhvien.SDT"));
                    item.setPlaceOrigin(rs.getString("thanhvien.Que"));
                    tenant = new Tenant();
                    room = new Room();
                    room.setRoomID(rs.getString("khachthue.MaP"));
                    tenant.setRoom(room);
                    homeTown = new HomeTown();
                    homeTown.setTownID(rs.getString("khachthue.MaT"));
                    tenant.setHomeTown(homeTown);
                    item.setTenant(tenant);
                    list.add(item);
                }
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            //Quay tro lai trang thai an toan
            try {
                this.con.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public ObservableList<Member> selectMemberByMaK(String cond) {
        ObservableList<Member> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM thanhvien WHERE MaK = ?";

        Member item = null;
        LocalDate localDate;
        try {
            PreparedStatement pre = this.con.prepareStatement(sql);
            pre.setString(1, cond);
            
            ResultSet rs = pre.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    item = new Member();
                    item.setMemberID(rs.getString("MaTV"));
                    item.setName(rs.getString("TenTV"));
                    item.setSex(rs.getString("thanhvien.GioiTinh"));
                    localDate = rs.getDate("thanhvien.NgaySinh").toLocalDate();
                    item.setBirthdate(localDate);
                    item.setCitizenID(rs.getString("thanhvien.CCCD"));
                    item.setPhoneNum(rs.getString("thanhvien.SDT"));
                    item.setPlaceOrigin(rs.getString("thanhvien.Que"));
                    list.add(item);
                }
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

            //Quay tro lai trang thai an toan
            try {
                this.con.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public boolean deleteAllData(String cond) {
        String sql = "DELETE FROM thanhvien WHERE MaK = ?";
        try {
            PreparedStatement pre = this.con.prepareStatement(sql.toString());

            pre.setString(1, cond);

            return this.exe(pre);
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

        return false;
    }

}
