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

public class DAO_HomeTown implements DAOInterface<HomeTown> {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    private static volatile DAO_HomeTown instance;

    private DAO_HomeTown() {
    }

    public static DAO_HomeTown getInstance() {
        if (instance == null) {
            Class var0 = DAO_HomeTown.class;
            synchronized(DAO_HomeTown.class) {
                if (instance == null) {
                    instance = new DAO_HomeTown();
                }
            }
        }

        return instance;
    }

    public void insertData(HomeTown t, String s, String s1) {
        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "INSERT INTO toanha (MaT, DiaChi) VALUES(?, ?)";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.setString(1, t.getTownID());
                this.preparedStatement.setString(2, t.getAddress());
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

    public void deleteData(HomeTown t, String s) {
        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "DELETE FROM toanha WHERE MaT = ?";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.setString(1, t.getTownID());
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

    public void updateData(HomeTown t, String s, String s1) {
        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "UPDATE toanha SET MaT = ?, DiaChi = ? WHERE MaT = ?";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.setString(1, t.getTownID());
                this.preparedStatement.setString(2, t.getAddress());
                this.preparedStatement.setString(3, t.getTownID());
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

    public ObservableList<HomeTown> selectALL() {
        ObservableList<HomeTown> list = FXCollections.observableArrayList();

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "SELECT * FROM toanha";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.resultSet = this.preparedStatement.executeQuery();

                while(this.resultSet.next()) {
                    HomeTown x = new HomeTown(this.resultSet.getString("MaT"), this.resultSet.getString("DiaChi"));
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

    public ObservableList<HomeTown> selectByCondition(String condition1, String condition2, String condition3) {
        return null;
    }

    public int selectCount(String condition1) {
        int num = 0;

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "SELECT COUNT(MaT) AS Num FROM toanha ";

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

    public ObservableList<HomeTown> searching(String text, String condition1, String condition2) {
        return null;
    }

    public void deleteAllData(String s1) {
    }

    public String sumPrice(String condition1, String condition2) {
        return null;
    }

    public String selectID(String s1, String s2) {
        return null;
    }

    public HomeTown selectObject(String s, String s1) {
        return null;
    }

    public void insertDataToTempTable(HomeTown t) {
    }

    public void deleteDataFromTempTable(HomeTown t) {
    }

    public InputStream getReport(String column_name) {
        return null;
    }

    public String selectTurnover(int month, int year) {
        return null;
    }
}

