package com.ltc.btl_javafx.DAO;



import com.ltc.btl_javafx.database.ConnectionToDatabase;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.ltc.btl_javafx.model.Bill;

public class DAO_Bill implements DAOInterface<Bill> {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    private static volatile DAO_Bill instance;

    private DAO_Bill() {
    }

    public static DAO_Bill getInstance() {
        if (instance == null) {
            Class var0 = DAO_Bill.class;
            synchronized(DAO_Bill.class) {
                if (instance == null) {
                    instance = new DAO_Bill();
                }
            }
        }

        return instance;
    }

    public void insertData(Bill t, String s, String s1) {
        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query1 = "INSERT INTO hoadon (ID, MaT, MaP, MaK, SoDien, NgayXuatHD, TongTien) VALUES(?, ?, ?, ?, ?, ?, ?)";
            String query2 = "INSERT INTO thongkehoadon (NgayXuatHD, TongTien) VALUES(?, ?)";

            try {
                this.preparedStatement = this.connection.prepareStatement(query1);
                this.preparedStatement.setString(1, t.getAccountID());
                this.preparedStatement.setString(2, t.getHomeTownID());
                this.preparedStatement.setString(3, t.getRoomID());
                this.preparedStatement.setString(4, t.getTenantID());
                this.preparedStatement.setInt(5, t.getElectricNumber());
                this.preparedStatement.setDate(6, Date.valueOf(t.getInvoiceDate()));
                this.preparedStatement.setBigDecimal(7, new BigDecimal(t.getBillPrice()));
                this.preparedStatement.execute();
                this.preparedStatement = this.connection.prepareStatement(query2);
                this.preparedStatement.setDate(1, Date.valueOf(t.getInvoiceDate()));
                this.preparedStatement.setBigDecimal(2, new BigDecimal(t.getBillPrice()));
                this.preparedStatement.execute();
                ConnectionToDatabase.closeConnection(this.connection);
            } catch (SQLException var20) {
                var20.printStackTrace();
            }
        } catch (ClassNotFoundException var21) {
            var21.printStackTrace();
        } finally {
            if (this.resultSet != null) {
                try {
                    this.resultSet.close();
                } catch (SQLException var19) {
                    var19.printStackTrace();
                }
            }

            if (this.preparedStatement != null) {
                try {
                    this.preparedStatement.close();
                } catch (SQLException var18) {
                    var18.printStackTrace();
                }
            }

        }

    }

    public void deleteData(Bill t, String s) {
        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "DELETE FROM hoadon WHERE MaHD = ?";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.setInt(1, t.getBillID());
                this.preparedStatement.execute();
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

    }

    public void updateData(Bill t, String s, String s1) {
    }

    public ObservableList<Bill> selectALL() {
        ObservableList<Bill> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM hoadon";

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.resultSet = this.preparedStatement.executeQuery();

                while(this.resultSet.next()) {
                    Bill x = new Bill(this.resultSet.getInt("MaHD"), this.resultSet.getString("ID"), this.resultSet.getString("MaT"), this.resultSet.getString("MaP"), this.resultSet.getString("MaK"), this.resultSet.getInt("SoDien"), this.resultSet.getDate("NgayXuatHD").toLocalDate(), String.valueOf(this.resultSet.getDouble("TongTien")));
                    list.add(x);
                }
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

    public ObservableList<Bill> selectByCondition(String condition1, String condition2, String condition3) {
        ObservableList<Bill> list = FXCollections.observableArrayList();
        String query = "";
        if (condition1 != null && condition2 != null && condition3 != null) {
            query = "SELECT * FROM hoadon WHERE MaT = ? AND MaP = ? AND NgayXuatHD = ?";
        } else if (condition1 != null && condition2 != null && condition3 == null) {
            query = "SELECT * FROM hoadon WHERE MaT = ? AND MaP = ?";
        } else if (condition1 != null && condition2 == null && condition3 != null) {
            query = "SELECT * FROM hoadon WHERE MaT = ? AND NgayXuatHD = ?";
        } else if (condition1 != null && condition2 == null && condition3 == null) {
            query = "SELECT * FROM hoadon WHERE MaT = ?";
        } else if (condition1 == null && condition2 == null && condition3 != null) {
            query = "SELECT * FROM hoadon WHERE NgayXuatHD = ?";
        }

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                if (condition1 != null && condition2 != null && condition3 != null) {
                    this.preparedStatement.setString(1, condition1);
                    this.preparedStatement.setString(2, condition2);
                    this.preparedStatement.setString(3, condition3);
                } else if (condition1 != null && condition2 != null && condition3 == null) {
                    this.preparedStatement.setString(1, condition1);
                    this.preparedStatement.setString(2, condition2);
                } else if (condition1 != null && condition2 == null && condition3 != null) {
                    this.preparedStatement.setString(1, condition1);
                    this.preparedStatement.setString(2, condition3);
                } else if (condition1 != null && condition2 == null && condition3 == null) {
                    this.preparedStatement.setString(1, condition1);
                } else if (condition1 == null && condition2 == null && condition3 != null) {
                    this.preparedStatement.setString(1, condition3);
                }

                this.resultSet = this.preparedStatement.executeQuery();

                while(this.resultSet.next()) {
                    Bill x = new Bill(this.resultSet.getInt("MaHD"), this.resultSet.getString("ID"), this.resultSet.getString("MaT"), this.resultSet.getString("MaP"), this.resultSet.getString("MaK"), this.resultSet.getInt("SoDien"), this.resultSet.getDate("NgayXuatHD").toLocalDate(), String.valueOf(this.resultSet.getDouble("TongTien")));
                    list.add(x);
                }
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

    public ObservableList<Bill> searching(String text, String condition1, String condition2) {
        return null;
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

    public Bill selectObject(String s, String s1) {
        Bill x = new Bill();
        String query = "SELECT * FROM hoadon WHERE MaT = ? AND MaP = ?";

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.setString(1, s);
                this.preparedStatement.setString(2, s1);

                for(this.resultSet = this.preparedStatement.executeQuery(); this.resultSet.next(); x = new Bill(this.resultSet.getInt("MaHD"), this.resultSet.getString("ID"), this.resultSet.getString("MaT"), this.resultSet.getString("MaP"), this.resultSet.getString("MaK"), this.resultSet.getInt("SoDien"), this.resultSet.getDate("NgayXuatHD").toLocalDate(), String.valueOf(this.resultSet.getDouble("TongTien")))) {
                }

                ConnectionToDatabase.closeConnection(this.connection);
            } catch (SQLException var19) {
                var19.printStackTrace();
            }
        } catch (ClassNotFoundException var20) {
            var20.printStackTrace();
        } finally {
            if (this.resultSet != null) {
                try {
                    this.resultSet.close();
                } catch (SQLException var18) {
                    var18.printStackTrace();
                }
            }

            if (this.preparedStatement != null) {
                try {
                    this.preparedStatement.close();
                } catch (SQLException var17) {
                    var17.printStackTrace();
                }
            }

        }

        return x;
    }

    public void insertDataToTempTable(Bill t) {
        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "INSERT INTO inhoadon (ID, MaT, MaP, MaK, SoDien, NgayXuatHD, TongTien) VALUES(?, ?, ?, ?, ?, ?, ?)";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.setString(1, t.getAccountID());
                this.preparedStatement.setString(2, t.getHomeTownID());
                this.preparedStatement.setString(3, t.getRoomID());
                this.preparedStatement.setString(4, t.getTenantID());
                this.preparedStatement.setInt(5, t.getElectricNumber());
                this.preparedStatement.setDate(6, Date.valueOf(t.getInvoiceDate()));
                this.preparedStatement.setBigDecimal(7, new BigDecimal(t.getBillPrice()));
                this.preparedStatement.execute();
                ConnectionToDatabase.closeConnection(this.connection);
            } catch (SQLException var17) {
                var17.printStackTrace();
            }
        } catch (ClassNotFoundException var18) {
            var18.printStackTrace();
        } finally {
            if (this.resultSet != null) {
                try {
                    this.resultSet.close();
                } catch (SQLException var16) {
                    var16.printStackTrace();
                }
            }

            if (this.preparedStatement != null) {
                try {
                    this.preparedStatement.close();
                } catch (SQLException var15) {
                    var15.printStackTrace();
                }
            }

        }

    }

    public void deleteDataFromTempTable(Bill t) {
        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "DELETE FROM inhoadon";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.execute();
                ConnectionToDatabase.closeConnection(this.connection);
            } catch (SQLException var17) {
                var17.printStackTrace();
            }
        } catch (ClassNotFoundException var18) {
            var18.printStackTrace();
        } finally {
            if (this.resultSet != null) {
                try {
                    this.resultSet.close();
                } catch (SQLException var16) {
                    var16.printStackTrace();
                }
            }

            if (this.preparedStatement != null) {
                try {
                    this.preparedStatement.close();
                } catch (SQLException var15) {
                    var15.printStackTrace();
                }
            }

        }

    }

    public InputStream getReport(String report_name) {
        InputStream input = null;
        String query = "SELECT report_jasper FROM reports WHERE report_name = ?";

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.setString(1, report_name);

                for(this.resultSet = this.preparedStatement.executeQuery(); this.resultSet.next(); input = this.resultSet.getBinaryStream(1)) {
                }
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

        return input;
    }

    public String selectTurnover(int month, int year) {
        String sumPrice = "0";
        String query = "";
        if (month != -1 && year != -1) {
            query = "Select Sum(TongTien) as TongDoanhThu From thongkehoadon where Month(NgayXuatHD) = ? and Year(NgayXuatHD) = ? group by Month(NgayXuatHD) ";
        } else if (month == -1 && year != -1) {
            query = "Select Sum(TongTien) as TongDoanhThu From thongkehoadon where Year(NgayXuatHD) = ? group by Year(NgayXuatHD) ";
        }

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                if (month != -1 && year != -1) {
                    this.preparedStatement.setInt(1, month);
                    this.preparedStatement.setInt(2, year);
                } else if (month == -1 && year != -1) {
                    this.preparedStatement.setInt(1, year);
                }

                this.resultSet = this.preparedStatement.executeQuery();

                while(this.resultSet.next()) {
                    sumPrice = String.valueOf(this.resultSet.getBigDecimal("TongDoanhThu"));
                    if (sumPrice.isEmpty()) {
                        sumPrice = "0";
                    }
                }
            } catch (SQLException var19) {
                var19.printStackTrace();
            }
        } catch (ClassNotFoundException var20) {
            var20.printStackTrace();
        } finally {
            if (this.resultSet != null) {
                try {
                    this.resultSet.close();
                } catch (SQLException var18) {
                    var18.printStackTrace();
                }
            }

            if (this.preparedStatement != null) {
                try {
                    this.preparedStatement.close();
                } catch (SQLException var17) {
                    var17.printStackTrace();
                }
            }

        }

        return sumPrice;
    }
}

