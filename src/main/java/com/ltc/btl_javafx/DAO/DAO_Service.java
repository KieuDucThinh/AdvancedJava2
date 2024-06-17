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
import com.ltc.btl_javafx.model.Service;

public class DAO_Service implements DAOInterface<Service> {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    private static volatile DAO_Service instance;

    private DAO_Service() {
    }

    public static DAO_Service getInstance() {
        if (instance == null) {
            Class var0 = DAO_Service.class;
            synchronized(DAO_Service.class) {
                if (instance == null) {
                    instance = new DAO_Service();
                }
            }
        }

        return instance;
    }

    public void insertData(Service t, String s, String s1) {
        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "INSERT INTO dichvu (MaDV, MaT, TenDV, LoaiDV, GiaDV) VALUES(?, ?, ?, ?, ?)";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.setString(1, t.getIDService());
                this.preparedStatement.setString(2, s);
                this.preparedStatement.setString(3, t.getNameService());
                this.preparedStatement.setString(4, t.getTypeService());
                this.preparedStatement.setDouble(5, Double.parseDouble(t.getPriceService()));
                this.preparedStatement.execute();
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

    }

    public void deleteData(Service t, String s) {
        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "DELETE FROM dichvu WHERE MaDV = ? AND MaT = ?";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.setString(1, t.getIDService());
                this.preparedStatement.setString(2, s);
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

    public void updateData(Service t, String s, String s1) {
        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "UPDATE dichvu SET TenDV = ?, LoaiDV = ?, GiaDV = ? WHERE MaDV = ? AND MaT = ?";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.setString(1, t.getNameService());
                this.preparedStatement.setString(2, t.getTypeService());
                this.preparedStatement.setDouble(3, Double.parseDouble(t.getPriceService()));
                this.preparedStatement.setString(4, s);
                this.preparedStatement.setString(5, s1);
                this.preparedStatement.execute();
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

    }

    public ObservableList<Service> selectALL() {
        ObservableList<Service> list = FXCollections.observableArrayList();

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "SELECT * FROM dichvu";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.resultSet = this.preparedStatement.executeQuery();

                while(this.resultSet.next()) {
                    Service x = new Service(new HomeTown(this.resultSet.getString("MaT"), ""), this.resultSet.getString("MaDV"), this.resultSet.getString("TenDV"), this.resultSet.getString("LoaiDV"), String.valueOf(this.resultSet.getDouble("GiaDV")));
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

    public ObservableList<Service> selectByCondition(String condition1, String condition2, String condition3) {
        ObservableList<Service> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM dichvu WHERE MaT = ?";

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.setString(1, condition1);
                this.resultSet = this.preparedStatement.executeQuery();

                while(this.resultSet.next()) {
                    Service x = new Service(new HomeTown(this.resultSet.getString("MaT"), ""), this.resultSet.getString("MaDV"), this.resultSet.getString("TenDV"), this.resultSet.getString("LoaiDV"), String.valueOf(this.resultSet.getDouble("GiaDV")));
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

    public ObservableList<Service> searching(String text, String condition1, String condition2) {
        return null;
    }

    public int selectCount(String condition1) {
        int num = 0;

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "SELECT COUNT(MaT) AS Num FROM dichvu ";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.resultSet = this.preparedStatement.executeQuery();
                if (this.resultSet.next()) {
                    num = this.resultSet.getInt("Num");
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

        return num;
    }

    public String selectID(String s1, String s2) {
        return null;
    }

    public void deleteAllData(String s1) {
    }

    public String sumPrice(String condition1, String condition2) {
        double sum = 0.0;
        String query = "";
        query = "SELECT Sum(GiaDV) AS SUM FROM dichvu WHERE MaT = ? AND LoaiDV = ?";

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.setString(1, condition1);
                this.preparedStatement.setString(2, condition2);

                for(this.resultSet = this.preparedStatement.executeQuery(); this.resultSet.next(); sum = this.resultSet.getDouble("SUM")) {
                }
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

        return String.valueOf(sum);
    }

    public Service selectObject(String s, String s1) {
        return null;
    }

    public void insertDataToTempTable(Service t) {
    }

    public void deleteDataFromTempTable(Service t) {
    }

    public InputStream getReport(String column_name) {
        return null;
    }

    public String selectTurnover(int month, int year) {
        return null;
    }
}
