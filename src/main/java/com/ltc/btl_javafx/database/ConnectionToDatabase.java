package com.ltc.btl_javafx.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDatabase {
    public ConnectionToDatabase() {
    }

    public static Connection getConnectionDB() throws ClassNotFoundException {
        Connection conn = null;
        String urlMySQL = "jdbc:mySQL://localhost:3306/quanlychungcumini?allowMultiQueries=true";
        String user = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(urlMySQL, user, password);
            return conn;
        } catch (SQLException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

    }
}
