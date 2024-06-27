/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ltc.btl_javafx.DAO;

import com.ltc.btl_javafx.database.ConnectionPool;
import com.ltc.btl_javafx.database.ConnectionPoolImpl;
import com.ltc.btl_javafx.model.HomeTown;
import com.ltc.btl_javafx.model.Room;
import com.ltc.btl_javafx.model.Tenant;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author TUF
 */
public class TenantFunctionImpl implements TenantFunction {

    private static ConnectionPool cp;

    //Doi tuong ket noi
    private Connection con;

    private static volatile TenantFunctionImpl instance;

    public static TenantFunctionImpl getInstance() {
        if (instance == null) {
            Class var0 = TenantFunctionImpl.class;
            synchronized (TenantFunctionImpl.class) {
                if (instance == null) {
                    instance = new TenantFunctionImpl(cp);
                }
            }
        }

        return instance;
    }

    public TenantFunctionImpl(ConnectionPool cp) {
        // TODO Auto-generated constructor stub
        //Xac dinh bo quan ly ket noi de lam viec
        if (cp == null) {
            cp = new ConnectionPoolImpl();
        }

        try {
            //Xin ket noi de lam viec
            this.con = cp.getConnection("TenantFunctionImpl");

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
    public boolean addTenant(Tenant item) {
        if (this.isExisting(item)) {
            return false;
        }

        boolean flag1 = false;
        boolean flag2 = false;

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO khachthue(");
        sql.append("MaK, MaP, MaT, TenK, GioiTinh, ");
        sql.append("NgaySinh, CCCD, SDT, Que, NgayThue");
        sql.append(")");
        sql.append("VALUES(?,?,?,?,?,?,?,?,?,?)");

        try {
            PreparedStatement pre = this.con.prepareStatement(sql.toString());
            pre.setString(1, item.getTenantID());
            pre.setString(2, item.getRoom().getRoomID());
            pre.setString(3, item.getHomeTown().getTownID());
            pre.setString(4, item.getName());
            pre.setString(5, item.getSex());
            pre.setDate(6, Date.valueOf(item.getBirthdate()));
            pre.setString(7, item.getCitizenID());
            pre.setString(8, item.getPhoneNum());
            pre.setString(9, item.getPlaceOrigin());
            pre.setDate(10, Date.valueOf(item.getRentDate()));

            flag1 = this.exe(pre);

            // Cập nhật trạng thái phòng
            String updatePhongQuery = "UPDATE phong SET TrangThai = 1 WHERE MaP = ?";
            PreparedStatement preUpdatePhong = this.con.prepareStatement(updatePhongQuery);
            preUpdatePhong.setString(1, item.getRoom().getRoomID());

            flag2 = this.exe(preUpdatePhong);
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

        return flag1 && flag2;
    }

    @Override
    public boolean editTenant(Tenant item) {
        if (this.isExisting2(item)) {
            return false;
        }

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE khachthue SET ");
        sql.append("TenK=?, GioiTinh=?, NgaySinh=?, CCCD=?, ");
        sql.append("SDT=?, Que=?, NgayThue=? ");
        sql.append("WHERE MaK=?");

        try {
            PreparedStatement pre = this.con.prepareStatement(sql.toString());

            pre.setString(1, item.getName());
            pre.setString(2, item.getSex());
            pre.setDate(3, Date.valueOf(item.getBirthdate()));
            pre.setString(4, item.getCitizenID());
            pre.setString(5, item.getPhoneNum());
            pre.setString(6, item.getPlaceOrigin());
            pre.setDate(7, Date.valueOf(item.getRentDate()));
            pre.setString(8, item.getTenantID());

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

    private boolean isExisting(Tenant item) {
        boolean flag = false;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT MaTV FROM thanhvien WHERE CCCD=?;");
        sql.append("SELECT MaTV FROM thanhvien WHERE SDT=?;");
        sql.append("SELECT MaK FROM khachthue WHERE (CCCD=?);");
        sql.append("SELECT MaK FROM khachthue WHERE (SDT=?);");
        try {
            PreparedStatement pre = this.con.prepareStatement(sql.toString());

            pre.setString(1, item.getCitizenID());
            pre.setString(2, item.getPhoneNum());
            pre.setString(3, item.getCitizenID());
            pre.setString(4, item.getPhoneNum());

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

    private boolean isExisting2(Tenant item) {
        boolean flag = false;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT MaTV FROM thanhvien WHERE CCCD=?;");
        sql.append("SELECT MaTV FROM thanhvien WHERE SDT=?;");
        sql.append("SELECT MaK FROM khachthue WHERE (CCCD=?) && (MaK != ?);");
        sql.append("SELECT MaK FROM khachthue WHERE (SDT=?) && (MaK != ?);");
        try {
            PreparedStatement pre = this.con.prepareStatement(sql.toString());

            pre.setString(1, item.getCitizenID());
            pre.setString(2, item.getPhoneNum());
            pre.setString(3, item.getCitizenID());
            pre.setString(4, item.getTenantID());
            pre.setString(5, item.getPhoneNum());
            pre.setString(6, item.getTenantID());

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
    public ObservableList<Tenant> selectAllTenant() {
        ObservableList<Tenant> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM khachthue ORDER BY MaT, MaP";
        Tenant item = null;
        Room room = null;
        HomeTown homeTown = null;
        LocalDate localDate = null;
        try {
            PreparedStatement pre = this.con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    item = new Tenant();
                    item.setTenantID(rs.getString("MaK"));
                    item.setName(rs.getString("TenK"));
                    item.setSex(rs.getString("GioiTinh"));
                    localDate = rs.getDate("NgaySinh").toLocalDate();
                    item.setBirthdate(localDate);
                    item.setCitizenID(rs.getString("CCCD"));
                    item.setPhoneNum(rs.getString("SDT"));
                    item.setPlaceOrigin(rs.getString("Que"));
                    localDate = rs.getDate("NgayThue").toLocalDate();
                    item.setRentDate(localDate);
                    room = new Room();
                    room.setRoomID(rs.getString("MaP"));
                    item.setRoom(room);
                    homeTown = new HomeTown();
                    homeTown.setTownID(rs.getString("MaT"));
                    item.setHomeTown(homeTown);
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

    //Lay toan bo cac phong len man hinh
    @Override
    public ObservableList<Tenant> selectAllTenant2() {
        ObservableList<Tenant> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM khachthue RIGHT JOIN phong ON khachthue.MaT = phong.MaT GROUP BY phong.MaP";
        Tenant item = null;
        Room room = null;
        HomeTown homeTown = null;
        LocalDate localDate = null;
        try {
            PreparedStatement pre = this.con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    item = new Tenant();
//                    item.setTenantID(rs.getString("MaK"));
//                    item.setName(rs.getString("TenK"));
//                    item.setSex(rs.getString("GioiTinh"));
//                    localDate = rs.getDate("NgaySinh").toLocalDate();
//                    item.setBirthdate(localDate);
//                    item.setCitizenID(rs.getString("CCCD"));
//                    item.setPhoneNum(rs.getString("SDT"));
//                    item.setPlaceOrigin(rs.getString("Que"));
//                    localDate = rs.getDate("NgayThue").toLocalDate();
//                    item.setRentDate(localDate);
                    room = new Room();
                    room.setRoomID(rs.getString("phong.MaP"));
                    room.setPriceroom(rs.getString("phong.GiaP"));
                    room.setStateroom(rs.getString("phong.TrangThai"));
                    room.setTyperoom(rs.getString("phong.LoaiP"));
                    item.setRoom(room);
                    homeTown = new HomeTown();
                    homeTown.setTownID(rs.getString("phong.MaT"));
                    item.setHomeTown(homeTown);
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
    
    //Lay toan bo cac toa nha
    @Override
    public ObservableList<Tenant> selectHomeTownOfTenant() {
        ObservableList<Tenant> list = FXCollections.observableArrayList();
        String sql = "SELECT DISTINCT toanha.MaT FROM khachthue RIGHT JOIN toanha ON khachthue.MaT = toanha.MaT";
        Tenant item;
        //Room room = null;
        HomeTown homeTown;
        //LocalDate localDate = null;
        try {
            PreparedStatement pre = this.con.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    item = new Tenant();
                    homeTown = new HomeTown();
                    homeTown.setTownID(rs.getString("MaT"));
                    item.setHomeTown(homeTown);
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

    //Lay ra tat ca cac phong thuoc 1 toa
    @Override
    public ObservableList<Tenant> selectTenantByMaT(String maT) {
        ObservableList<Tenant> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM khachthue RIGHT JOIN phong ON khachthue.MaT = phong.MaT WHERE phong.MaT = ? GROUP BY phong.MaP";
        Tenant item = null;
        Room room = null;
        HomeTown homeTown = null;
        LocalDate localDate = null;
        try {
            PreparedStatement pre = this.con.prepareStatement(sql);
            pre.setString(1, maT);
            ResultSet rs = pre.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    item = new Tenant();
//                    item.setTenantID(rs.getString("MaK"));
//                    item.setName(rs.getString("TenK"));
//                    item.setSex(rs.getString("GioiTinh"));
//                    localDate = rs.getDate("NgaySinh").toLocalDate();
//                    item.setBirthdate(localDate);
//                    item.setCitizenID(rs.getString("CCCD"));
//                    item.setPhoneNum(rs.getString("SDT"));
//                    item.setPlaceOrigin(rs.getString("Que"));
//                    localDate = rs.getDate("NgayThue").toLocalDate();
//                    item.setRentDate(localDate);
                    room = new Room();
                    room.setRoomID(rs.getString("phong.MaP"));
                    room.setPriceroom(rs.getString("phong.GiaP"));
                    room.setStateroom(rs.getString("phong.TrangThai"));
                    room.setTyperoom(rs.getString("phong.LoaiP"));
                    item.setRoom(room);
                    homeTown = new HomeTown();
                    homeTown.setTownID(rs.getString("phong.MaT"));
                    item.setHomeTown(homeTown);
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
    public ObservableList<Tenant> selectTenantByMaP(String maP) {
        ObservableList<Tenant> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM khachthue RIGHT JOIN phong ON khachthue.MaP = phong.MaP WHERE (phong.MaP = ?) OR (phong.LoaiP = ?) OR (phong.TrangThai = ?) OR (phong.GiaP = ?) GROUP BY phong.MaP";
        Tenant item = null;
        Room room = null;
        HomeTown homeTown = null;
        LocalDate localDate = null;
        String check = "5";
        try {
            if(maP.equalsIgnoreCase("Trống")){
                check = "0";
            } else if(maP.equalsIgnoreCase("Đã thuê")){
                check = "1";
            }
                
            PreparedStatement pre = this.con.prepareStatement(sql);
            pre.setString(1, maP);
            pre.setString(2, maP);
            pre.setString(3, check);
            pre.setString(4, maP);
            
            ResultSet rs = pre.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    item = new Tenant();
                    item.setTenantID(rs.getString("MaK"));
//                    item.setName(rs.getString("TenK"));
//                    item.setSex(rs.getString("GioiTinh"));
//                    localDate = rs.getDate("NgaySinh").toLocalDate();
//                    item.setBirthdate(localDate);
//                    item.setCitizenID(rs.getString("CCCD"));
//                    item.setPhoneNum(rs.getString("SDT"));
//                    item.setPlaceOrigin(rs.getString("Que"));
//                    localDate = rs.getDate("NgayThue").toLocalDate();
//                    item.setRentDate(localDate);
                    room = new Room();
                    room.setRoomID(rs.getString("phong.MaP"));
                    room.setPriceroom(rs.getString("phong.GiaP"));
                    room.setStateroom(rs.getString("phong.TrangThai"));
                    room.setTyperoom(rs.getString("phong.LoaiP"));
                    item.setRoom(room);
                    homeTown = new HomeTown();
                    homeTown.setTownID(rs.getString("phong.MaT"));
                    item.setHomeTown(homeTown);
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
    public ObservableList<Tenant> selectTenantByCondition(String cond) {
        ObservableList<Tenant> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM khachthue WHERE (MaK LIKE ?) OR (MaP LIKE ?) OR (NgaySinh LIKE ?) OR (MaT LIKE ?) OR (TenK LIKE ?) OR (GioiTinh LIKE ?) OR (CCCD LIKE ?) OR (SDT LIKE ?) OR (Que LIKE ?) OR (NgayThue LIKE ?)";
        Tenant item = null;
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
            pre.setString(10, "%" + cond + "%");

            ResultSet rs = pre.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    item = new Tenant();
                    item.setTenantID(rs.getString("MaK"));
                    item.setName(rs.getString("TenK"));
                    item.setSex(rs.getString("GioiTinh"));
                    localDate = rs.getDate("NgaySinh").toLocalDate();
                    item.setBirthdate(localDate);
                    item.setCitizenID(rs.getString("CCCD"));
                    item.setPhoneNum(rs.getString("SDT"));
                    item.setPlaceOrigin(rs.getString("Que"));
                    localDate = rs.getDate("NgayThue").toLocalDate();
                    item.setRentDate(localDate);
                    room = new Room();
                    room.setRoomID(rs.getString("MaP"));
                    item.setRoom(room);
                    homeTown = new HomeTown();
                    homeTown.setTownID(rs.getString("MaT"));
                    item.setHomeTown(homeTown);
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

    /*
    @Override
    public boolean deleteData(String MaK, String MaP) {

        boolean flag = false;

        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM khachthue WHERE MaK = ?;");
        sql.append("UPDATE phong SET TrangThai = 0 WHERE MaP = ?;");
        try {
            PreparedStatement pre = this.con.prepareStatement(sql.toString());

            pre.setString(1, MaK);
            pre.setString(2, MaP);

            boolean results = pre.execute();
            do {
                if (results) {
                    ResultSet rs = pre.getResultSet();
                    if ((rs != null) && (rs.next())) {
                        flag = true;
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
     */
    @Override
    public boolean deleteData(String MaK, String MaP) {
        boolean flag1 = false;
        boolean flag2 = false;
        try {
            // Xóa khách thuê
            String deleteKhachThueQuery = "DELETE FROM khachthue WHERE MaK = ?";
            PreparedStatement preDeleteKhachThue = this.con.prepareStatement(deleteKhachThueQuery);
            preDeleteKhachThue.setString(1, MaK);
            flag1 = this.exe(preDeleteKhachThue); // Trả về true nếu xóa thành công

            // Cập nhật trạng thái phòng
            String updatePhongQuery = "UPDATE phong SET TrangThai = 0 WHERE MaP = ?";
            PreparedStatement preUpdatePhong = this.con.prepareStatement(updatePhongQuery);
            preUpdatePhong.setString(1, MaP);
            flag2 = this.exe(preUpdatePhong); // Không cần kiểm tra kết quả ở đây
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                this.con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return flag1 && flag2;
    }

    @Override
    public int selectCount(int year, int month) {
        String sql = "SELECT COUNT(DISTINCT thanhvien.MaTV)+COUNT(DISTINCT khachthue.MaK) FROM khachthue INNER JOIN thanhvien ON khachthue.MaK = thanhvien.MaK WHERE (YEAR(khachthue.NgayThue) < ?) OR ((YEAR(khachthue.NgayThue) = ? AND MONTH(khachthue.NgayThue) <= ?))";
        int num = 0;
        try {
            PreparedStatement pre = this.con.prepareStatement(sql);
            pre.setInt(1,year);
            pre.setInt(2,year);
            pre.setInt(3,month);
            ResultSet rs = pre.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    num = rs.getInt(1);
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
        return num;
    }
//
//        int num = 0;
//
//        String sql = "SELECT COUNT(DISTINCT thanhvien.MaTV)+COUNT(DISTINCT khachthue.MaK) AS \"ThanhVienMoi\" FROM khachthue INNER JOIN thanhvien ON khachthue.MaK = thanhvien.MaK WHERE (YEAR(khachthue.NgayThue) = ? AND MONTH(khachthue.NgayThue) = ?)";
//
//        this.con = this.cp.getConnection("newCustomerChart");
//
//        LocalDateMonthY time = new LocalDateMonthY();
//
//        XYChart.Series<String, Integer> chart0 = new XYChart.Series<>();
//        String dateString;
//        for (int i = 9; i >= 0; i--) {
//            this.pre = this.con.prepareStatement(sql);
//            if (time.getMonthNow() < time.previousMonth(i)) {
//                pre.setInt(1, time.previousYear(1));
//
//                dateString = "-" + time.previousYear(1);
//            } else {
//                pre.setInt(1, time.getYearNow());
//
//                dateString = "-" + time.getYearNow();
//            }
//            pre.setInt(2, time.previousMonth(i));
//            dateString = time.previousMonth(i) + dateString;
//            this.results = pre.executeQuery();
//
//            while (results.next()) {
//                chart0.getData().add(new XYChart.Data<>(dateString, results.getInt(1)));
//            }
//
//        }

//            newCustomerChart.getData().add(chart0);
//            
//        try {
//            this.connection = ConnectionToDatabase.getConnectionDB();
//            String query = "SELECT COUNT(*) AS Totals FROM (SELECT MaK FROM khachthue UNION ALL SELECT MaK FROM thanhvien) as CM ";
//
//            try {
//                this.preparedStatement = this.connection.prepareStatement(query);
//                this.resultSet = this.preparedStatement.executeQuery();
//                if (this.resultSet.next()) {
//                    num = this.resultSet.getInt("Totals");
//                }
//
//                ConnectionToDatabase.closeConnection(this.connection);
//            } catch (SQLException var18) {
//                var18.printStackTrace();
//            }
//        } catch (ClassNotFoundException var19) {
//            var19.printStackTrace();
//        } finally {
//            if (this.resultSet != null) {
//                try {
//                    this.resultSet.close();
//                } catch (SQLException var17) {
//                    var17.printStackTrace();
//                }
//            }
//
//            if (this.preparedStatement != null) {
//                try {
//                    this.preparedStatement.close();
//                } catch (SQLException var16) {
//                    var16.printStackTrace();
//                }
//            }
//
//        }
//
//        return num;
//    }
    
    @Override
    public int selectCount2(int year, int month) {
        String sql = "SELECT COUNT(DISTINCT thanhvien.MaTV)+COUNT(DISTINCT khachthue.MaK) FROM khachthue INNER JOIN thanhvien ON khachthue.MaK = thanhvien.MaK WHERE (YEAR(khachthue.NgayThue) = ? AND MONTH(khachthue.NgayThue) = ?)";
        int num = 0;
        try {
            PreparedStatement pre = this.con.prepareStatement(sql);
            pre.setInt(1,year);
//            pre.setInt(2,year);
            pre.setInt(2,month);
            ResultSet rs = pre.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    num = rs.getInt(1);
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
        return num;
    }
    
     @Override
    public ObservableList<Tenant> selectTenantByMaP2(String maP) {
        ObservableList<Tenant> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM khachthue WHERE MaP LIKE ?";
        Tenant item = null;
        Room room = null;
        HomeTown homeTown = null;
        LocalDate localDate = null;
        try {
            PreparedStatement pre = this.con.prepareStatement(sql);
            pre.setString(1, "%" + maP + "%");
            ResultSet rs = pre.executeQuery();

            if (rs != null) {
                while (rs.next()) {
                    item = new Tenant();
                    item.setTenantID(rs.getString("MaK"));
                    item.setName(rs.getString("TenK"));
                    item.setSex(rs.getString("GioiTinh"));
                    localDate = rs.getDate("NgaySinh").toLocalDate();
                    item.setBirthdate(localDate);
                    item.setCitizenID(rs.getString("CCCD"));
                    item.setPhoneNum(rs.getString("SDT"));
                    item.setPlaceOrigin(rs.getString("Que"));
                    localDate = rs.getDate("NgayThue").toLocalDate();
                    item.setRentDate(localDate);
                    room = new Room();
                    room.setRoomID(rs.getString("MaP"));
                    item.setRoom(room);
                    homeTown = new HomeTown();
                    homeTown.setTownID(rs.getString("MaT"));
                    item.setHomeTown(homeTown);
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
}
