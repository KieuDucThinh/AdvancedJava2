/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.ltc.btl_javafx.database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author TUF
 */
public interface ConnectionPool {
    public Connection getConnection(String objectName) throws SQLException;
    public void releaseConnection(Connection con, String objectName) throws SQLException;
}
