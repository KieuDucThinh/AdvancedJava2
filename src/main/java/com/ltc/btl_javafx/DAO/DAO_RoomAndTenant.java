package com.ltc.btl_javafx.DAO;


import com.ltc.btl_javafx.database.ConnectionToDatabase;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.ltc.btl_javafx.model.HomeTown;
import com.ltc.btl_javafx.model.Room;
import com.ltc.btl_javafx.model.RoomAndTenant;
import com.ltc.btl_javafx.model.Tenant;

public class DAO_RoomAndTenant implements DAOInterface<RoomAndTenant> {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    private static volatile DAO_RoomAndTenant instance;

    private DAO_RoomAndTenant() {
    }

    public static DAO_RoomAndTenant getInstance() {
        if (instance == null) {
            Class var0 = DAO_RoomAndTenant.class;
            synchronized(DAO_RoomAndTenant.class) {
                if (instance == null) {
                    instance = new DAO_RoomAndTenant();
                }
            }
        }

        return instance;
    }

    public void insertData(RoomAndTenant t, String s, String s1) {
    }

    public void deleteData(RoomAndTenant t, String s) {
    }

    public void updateData(RoomAndTenant t, String s, String s1) {
    }

    public ObservableList<RoomAndTenant> selectALL() {
        ObservableList<RoomAndTenant> list = FXCollections.observableArrayList();

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "SELECT * FROM khachthue";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.resultSet = this.preparedStatement.executeQuery();

                while(this.resultSet.next()) {
                    RoomAndTenant x = new RoomAndTenant(new HomeTown(this.resultSet.getString("MaT"), ""), new Room(this.resultSet.getString("MaP"), "", "", ""), new Tenant(this.resultSet.getString("MaK"), this.resultSet.getString("TenK"), this.resultSet.getString("GioiTinh"), this.resultSet.getDate("NgaySinh").toLocalDate(), this.resultSet.getString("CCCD"), this.resultSet.getString("SDT"), this.resultSet.getString("Que"), this.resultSet.getDate("NgayThue").toLocalDate()));
                    list.add(x);
                }

                ConnectionToDatabase.closeConnection(this.connection);
            } catch (SQLException var18) {
                var18.printStackTrace();
            }
        } catch (ClassNotFoundException var19) {
            var19.printStackTrace();
        } finally {
            if (this.resultSet != null) {
                try {
                    this.resultSet.close();
                } catch (SQLException var17) {
                    var17.printStackTrace();
                }
            }

            if (this.preparedStatement != null) {
                try {
                    this.preparedStatement.close();
                } catch (SQLException var16) {
                    var16.printStackTrace();
                }
            }

        }

        return list;
    }

    public ObservableList<RoomAndTenant> selectByCondition(String condition1, String condition2, String condition3) {
        ObservableList<RoomAndTenant> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM khachthue WHERE MaT = ?";

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.setString(1, condition1);
                this.resultSet = this.preparedStatement.executeQuery();

                while(this.resultSet.next()) {
                    RoomAndTenant x = new RoomAndTenant(new HomeTown(this.resultSet.getString("MaT"), ""), new Room(this.resultSet.getString("MaP"), "", "", ""), new Tenant(this.resultSet.getString("MaK"), this.resultSet.getString("TenK"), this.resultSet.getString("GioiTinh"), this.resultSet.getDate("NgaySinh").toLocalDate(), this.resultSet.getString("CCCD"), this.resultSet.getString("SDT"), this.resultSet.getString("Que"), this.resultSet.getDate("NgayThue").toLocalDate()));
                    list.add(x);
                }

                ConnectionToDatabase.closeConnection(this.connection);
            } catch (SQLException var21) {
                var21.printStackTrace();
            }
        } catch (ClassNotFoundException var22) {
            var22.printStackTrace();
        } finally {
            if (this.resultSet != null) {
                try {
                    this.resultSet.close();
                } catch (SQLException var20) {
                    var20.printStackTrace();
                }
            }

            if (this.preparedStatement != null) {
                try {
                    this.preparedStatement.close();
                } catch (SQLException var19) {
                    var19.printStackTrace();
                }
            }

        }

        return list;
    }

    public ObservableList<RoomAndTenant> searching(String text, String condition1, String condition2) {
        ObservableList<RoomAndTenant> list = FXCollections.observableArrayList();
        String query = "";
        if ("".equals(condition1)) {
            query = "SELECT * FROM khachthue WHERE MaK LIKE ? OR MaP LIKE ? OR MaT LIKE ? OR TenK LIKE ? OR GioiTinh LIKE ? OR NgaySinh LIKE ? OR CCCD LIKE ? OR SDT LIKE ? OR Que LIKE ? OR NgayThue LIKE ?";
        } else {
            query = "SELECT * FROM khachthue WHERE (MaT = ?) AND (MaK LIKE ? OR MaP LIKE ? OR TenK LIKE ? OR GioiTinh LIKE ? OR NgaySinh LIKE ? OR CCCD LIKE ? OR SDT LIKE ? OR Que LIKE ? OR NgayThue LIKE ?)";
        }

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                if ("".equals(condition1)) {
                    this.preparedStatement.setString(1, "%" + text + "%");
                    this.preparedStatement.setString(2, "%" + text + "%");
                    this.preparedStatement.setString(3, "%" + text + "%");
                    this.preparedStatement.setString(4, "%" + text + "%");
                    this.preparedStatement.setString(5, "%" + text + "%");
                    this.preparedStatement.setString(6, "%" + text + "%");
                    this.preparedStatement.setString(7, "%" + text + "%");
                    this.preparedStatement.setString(8, "%" + text + "%");
                    this.preparedStatement.setString(9, "%" + text + "%");
                    this.preparedStatement.setString(10, "%" + text + "%");
                } else {
                    this.preparedStatement.setString(1, condition1);
                    this.preparedStatement.setString(2, "%" + text + "%");
                    this.preparedStatement.setString(3, "%" + text + "%");
                    this.preparedStatement.setString(4, "%" + text + "%");
                    this.preparedStatement.setString(5, "%" + text + "%");
                    this.preparedStatement.setString(6, "%" + text + "%");
                    this.preparedStatement.setString(7, "%" + text + "%");
                    this.preparedStatement.setString(8, "%" + text + "%");
                    this.preparedStatement.setString(9, "%" + text + "%");
                    this.preparedStatement.setString(10, "%" + text + "%");
                }

                this.resultSet = this.preparedStatement.executeQuery();

                while(this.resultSet.next()) {
                    RoomAndTenant x = new RoomAndTenant(new HomeTown(this.resultSet.getString("MaT"), ""), new Room(this.resultSet.getString("MaP"), "", "", ""), new Tenant(this.resultSet.getString("MaK"), this.resultSet.getString("TenK"), this.resultSet.getString("GioiTinh"), this.resultSet.getDate("NgaySinh").toLocalDate(), this.resultSet.getString("CCCD"), this.resultSet.getString("SDT"), this.resultSet.getString("Que"), this.resultSet.getDate("NgayThue").toLocalDate()));
                    list.add(x);
                }

                ConnectionToDatabase.closeConnection(this.connection);
            } catch (SQLException var21) {
                var21.printStackTrace();
            }
        } catch (ClassNotFoundException var22) {
            var22.printStackTrace();
        } finally {
            if (this.resultSet != null) {
                try {
                    this.resultSet.close();
                } catch (SQLException var20) {
                    var20.printStackTrace();
                }
            }

            if (this.preparedStatement != null) {
                try {
                    this.preparedStatement.close();
                } catch (SQLException var19) {
                    var19.printStackTrace();
                }
            }

        }

        return list;
    }

    public int selectCount(String condition1) {
        return 0;
    }

    public String selectID(String s1, String s2) {
        return null;
    }

    public void deleteAllData(String s1) {
    }

    public String sumPrice(String condition1, String condition2) {
        return null;
    }

    public RoomAndTenant selectObject(String s, String s1) {
        return null;
    }

    public void insertDataToTempTable(RoomAndTenant t) {
    }

    public void deleteDataFromTempTable(RoomAndTenant t) {
    }

    public InputStream getReport(String column_name) {
        return null;
    }

    public String selectTurnover(int month, int year) {
        return null;
    }
}
