package com.ltc.btl_javafx.DAO;



import com.ltc.btl_javafx.database.ConnectionToDatabase;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.ltc.btl_javafx.model.AccountLogin;

public class DAO_Login implements DAOInterface<AccountLogin> {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    private static volatile DAO_Login instance;

    private DAO_Login() {
    }

    public static DAO_Login getInstance() {
        if (instance == null) {
            Class var0 = DAO_Login.class;
            synchronized(DAO_Login.class) {
                if (instance == null) {
                    instance = new DAO_Login();
                }
            }
        }

        return instance;
    }

    public void insertData(AccountLogin account, String s, String s1) {
    }

    public void deleteData(AccountLogin account, String s) {
    }

    public void updateData(AccountLogin account, String s, String s1) {
    }

    public ObservableList<AccountLogin> selectALL() {
        return null;
    }

    public ObservableList<AccountLogin> selectByCondition(String condition1, String condition2, String condition3) {
        ObservableList<AccountLogin> list = FXCollections.observableArrayList();

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "SELECT * FROM dangnhap WHERE ID = ? AND MatKhau = ?";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.setString(1, condition1);
                this.preparedStatement.setString(2, condition2);
                this.resultSet = this.preparedStatement.executeQuery();

                while(this.resultSet.next()) {
                    AccountLogin x = new AccountLogin(this.resultSet.getString("ID"), this.resultSet.getString("MatKhau"), this.resultSet.getString("TenNguoiDung"), this.resultSet.getInt("CapBac"));
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

    public ObservableList<AccountLogin> searching(String text, String condition1, String condition2) {
        return null;
    }

    public void deleteAllData(String s1) {
    }

    public AccountLogin selectObject(String s, String s1) {
        AccountLogin x = new AccountLogin();

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "SELECT * FROM dangnhap WHERE ID = ?";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.setString(1, s);
                this.resultSet = this.preparedStatement.executeQuery();
                if (this.resultSet.next()) {
                    x = new AccountLogin(this.resultSet.getString("ID"), this.resultSet.getString("MatKhau"), this.resultSet.getString("TenNguoiDung"), this.resultSet.getInt("CapBac"));
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

    public String sumPrice(String condition1, String condition2) {
        return null;
    }

    public String selectID(String s1, String s2) {
        return null;
    }

    public void insertDataToTempTable(AccountLogin t) {
    }

    public void deleteDataFromTempTable(AccountLogin t) {
    }

    public InputStream getReport(String column_name) {
        return null;
    }

    public String selectTurnover(int month, int year) {
        return null;
    }
}

