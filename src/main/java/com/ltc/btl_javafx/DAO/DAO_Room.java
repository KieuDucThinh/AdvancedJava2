package com.ltc.btl_javafx.DAO;



import com.ltc.btl_javafx.database.ConnectionToDatabase;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.ltc.btl_javafx.model.Room;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class DAO_Room implements DAOInterface<Room> {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    private static volatile DAO_Room instance;

    private DAO_Room() {
    }

    public static DAO_Room getInstance() {
        if (instance == null) {
            Class var0 = DAO_Room.class;
            synchronized(DAO_Room.class) {
                if (instance == null) {
                    instance = new DAO_Room();
                }
            }
        }

        return instance;
    }


        //Xuất file
        public void exportToExcel(String filePath) {
            ObservableList<Room> roomList = selectALL();
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Rooms");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Room ID");
            headerRow.createCell(1).setCellValue("Type Room");
            headerRow.createCell(2).setCellValue("State Room");
            headerRow.createCell(3).setCellValue("Price Room");

            int rowNum = 1;
            for (Room room : roomList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(room.getRoomID());
                row.createCell(1).setCellValue(room.getTyperoom());
                row.createCell(2).setCellValue(room.getStateroom());
                row.createCell(3).setCellValue(room.getPriceroom());
            }

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    public void insertData(Room t, String s, String s1) {
        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "INSERT INTO phong (MaP, MaT, LoaiP, TrangThai, GiaP) VALUES(?, ?, ?, 0, ?)";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.setString(1, t.getRoomID());
                this.preparedStatement.setString(2, s);
                this.preparedStatement.setString(3, t.getTyperoom());
                this.preparedStatement.setString(4, t.getPriceroom());
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

    public void deleteData(Room t, String s) {
        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "DELETE FROM phong WHERE MaP = ?";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.setString(1, t.getRoomID());
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

    public void updateData(Room t, String s, String s1) {
        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "UPDATE phong SET MaP = ?, MaT = ?, LoaiP = ?, GiaP = ? WHERE MaP = ?";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.setString(1, t.getRoomID());
                this.preparedStatement.setString(2, s);
                this.preparedStatement.setString(3, t.getTyperoom());
                this.preparedStatement.setString(4, t.getPriceroom());
                this.preparedStatement.setString(5, t.getRoomID());
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
//        Lấy tất cả các bản ghi
    public ObservableList<Room> selectALL() {
        ObservableList<Room> list = FXCollections.observableArrayList();

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "SELECT * FROM phong";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.resultSet = this.preparedStatement.executeQuery();

                while(this.resultSet.next()) {
                    String statement;
                    if (this.resultSet.getByte("TrangThai") == 0) {
                        statement = "Trống";
                    } else {
                        statement = "Đã thuê";
                    }

                    Room x = new Room(this.resultSet.getString("MaP"), this.resultSet.getString("LoaiP"), statement, String.valueOf(this.resultSet.getDouble("GiaP")));
                    list.add(x);
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

        return list;
    }


    //Chọnphongfg dựa theo trang thái và mã tòa

    public ObservableList<Room> selectByCondition(String condition1, String condition2, String condition3) {
        ObservableList<Room> listroom = FXCollections.observableArrayList();
        String query = "";

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            if (condition1 != null && condition2 == null) {
                query = "SELECT * FROM phong WHERE MaT = ?";
            } else if (condition1 == null && condition2 != null) {
                query = "SELECT * FROM phong WHERE TrangThai = ?";
            } else {
                query = "SELECT * FROM phong WHERE MaT = ? AND TrangThai = ?";
            }

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                if (condition1 != null && condition2 == null) {
                    this.preparedStatement.setString(1, condition1);
                } else if (condition1 == null && condition2 != null) {
                    this.preparedStatement.setString(1, condition2);
                } else {
                    this.preparedStatement.setString(1, condition1);
                    this.preparedStatement.setString(2, condition2);
                }

                this.resultSet = this.preparedStatement.executeQuery();

                while(this.resultSet.next()) {
                    String statement;
                    if (this.resultSet.getByte("TrangThai") == 0) {
                        statement = "Trống";
                    } else {
                        statement = "Đã thuê";
                    }

                    Room x = new Room(this.resultSet.getString("MaP"), this.resultSet.getString("LoaiP"), statement, String.valueOf(this.resultSet.getDouble("GiaP")));
                    listroom.add(x);
                }

                ConnectionToDatabase.closeConnection(this.connection);
            } catch (SQLException var22) {
                var22.printStackTrace();
            }
        } catch (ClassNotFoundException var23) {
            var23.printStackTrace();
        } finally {
            if (this.resultSet != null) {
                try {
                    this.resultSet.close();
                } catch (SQLException var21) {
                    var21.printStackTrace();
                }
            }

            if (this.preparedStatement != null) {
                try {
                    this.preparedStatement.close();
                } catch (SQLException var20) {
                    var20.printStackTrace();
                }
            }

        }

        return listroom;
    }


    //Hàm đếm số lượng phòng dựa trên trạng thái

    public int selectCount(String condition1) {
        int num = 0;

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "SELECT COUNT(MaP) AS Num FROM phong";
            if (condition1 != null) {
                query = query + " WHERE TrangThai = ?";
            }

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                if (condition1 != null) {
                    this.preparedStatement.setString(1, condition1);
                }

                this.resultSet = this.preparedStatement.executeQuery();
                if (this.resultSet.next()) {
                    num = this.resultSet.getInt("Num");
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

        return num;
    }


    // Hàm lấy mã tòa từ mã phòng

    public String selectID(String s1, String s2) {
        String ID = "";

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();
            String query = "SELECT MaT FROM phong WHERE MaP = ?";

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                this.preparedStatement.setString(1, s1);
                this.resultSet = this.preparedStatement.executeQuery();
                if (this.resultSet.next()) {
                    ID = this.resultSet.getString("MaT");
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

        return ID;
    }


    //Hàm  tìm kiêm

    public ObservableList<Room> searching(String text, String condition1, String condition2) {
        ObservableList<Room> list = FXCollections.observableArrayList();
        String query = "";
        String temp1 = "Trống";
        String temp2 = "Đã thuê";
        String temp3 = "Thuê";
        String temp4 = "Thue";
        String temp5 = "Da thue";
        String temp6 = "Trong";
        String temp;
        if (!temp1.equalsIgnoreCase(text) && !temp6.equalsIgnoreCase(text)) {
            if (!temp2.equalsIgnoreCase(text) && !temp3.equalsIgnoreCase(text) && !temp4.equalsIgnoreCase(text) && !temp5.equalsIgnoreCase(text)) {
                temp = text;
            } else {
                temp = "1";
            }
        } else {
            temp = "0";
        }

        if ("".equals(condition1) && "".equals(condition2)) {
            query = "SELECT * FROM phong WHERE MaP LIKE ? OR LoaiP LIKE ? OR TrangThai LIKE ? OR GiaP LIKE ?";
        } else if ("".equals(condition1) && !"".equals(condition2)) {
            query = "SELECT * FROM phong WHERE (TrangThai = ?) AND (MaP LIKE ? OR LoaiP LIKE ? OR GiaP LIKE ?)";
        } else if (!"".equals(condition1) && "".equals(condition2)) {
            query = "SELECT * FROM phong WHERE (MaT = ?) AND (MaP LIKE ? OR LoaiP LIKE ? OR TrangThai LIKE ? OR GiaP LIKE ?)";
        } else if (!"".equals(condition1) && !"".equals(condition2)) {
            query = "SELECT * FROM phong WHERE (MaT = ? AND TrangThai = ?) AND (MaP LIKE ? OR LoaiP LIKE ? OR GiaP LIKE ?)";
        }

        try {
            this.connection = ConnectionToDatabase.getConnectionDB();

            try {
                this.preparedStatement = this.connection.prepareStatement(query);
                if ("".equals(condition1) && "".equals(condition2)) {
                    this.preparedStatement.setString(1, "%" + text + "%");
                    this.preparedStatement.setString(2, "%" + text + "%");
                    this.preparedStatement.setString(3, "%" + temp + "%");
                    this.preparedStatement.setString(4, "%" + text + "%");
                } else if ("".equals(condition1) && !"".equals(condition2)) {
                    this.preparedStatement.setString(1, condition2);
                    this.preparedStatement.setString(2, "%" + text + "%");
                    this.preparedStatement.setString(3, "%" + text + "%");
                    this.preparedStatement.setString(4, "%" + text + "%");
                } else if (!"".equals(condition1) && "".equals(condition2)) {
                    this.preparedStatement.setString(1, condition1);
                    this.preparedStatement.setString(2, "%" + text + "%");
                    this.preparedStatement.setString(3, "%" + text + "%");
                    this.preparedStatement.setString(4, "%" + text + "%");
                    this.preparedStatement.setString(5, "%" + text + "%");
                } else if (!"".equals(condition1) && !"".equals(condition2)) {
                    this.preparedStatement.setString(1, condition1);
                    this.preparedStatement.setString(2, condition2);
                    this.preparedStatement.setString(3, "%" + text + "%");
                    this.preparedStatement.setString(4, "%" + text + "%");
                    this.preparedStatement.setString(5, "%" + text + "%");
                }

                this.resultSet = this.preparedStatement.executeQuery();

                while(this.resultSet.next()) {
                    String statement;
                    if (this.resultSet.getByte("TrangThai") == 0) {
                        statement = "Trống";
                    } else {
                        statement = "Đã thuê";
                    }

                    Room x = new Room(this.resultSet.getString("MaP"), this.resultSet.getString("LoaiP"), statement, String.valueOf(this.resultSet.getDouble("GiaP")));
                    list.add(x);
                }
            } catch (SQLException var29) {
                var29.printStackTrace();
            }
        } catch (ClassNotFoundException var30) {
            var30.printStackTrace();
        } finally {
            if (this.resultSet != null) {
                try {
                    this.resultSet.close();
                } catch (SQLException var28) {
                    var28.printStackTrace();
                }
            }

            if (this.preparedStatement != null) {
                try {
                    this.preparedStatement.close();
                } catch (SQLException var27) {
                    var27.printStackTrace();
                }
            }

        }

        return list;
    }

    public void deleteAllData(String s1) {
    }

    public String sumPrice(String condition1, String condition2) {
        double sum = 0.0;
        String query = "SELECT Sum(GiaP) AS SUM FROM phong WHERE MaT = ? AND MaP = ?";

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

    public Room selectObject(String s, String s1) {
        return null;
    }

    public void insertDataToTempTable(Room t) {
    }

    public void deleteDataFromTempTable(Room t) {
    }

    public InputStream getReport(String column_name) {
        return null;
    }

    public String selectTurnover(int month, int year) {
        return null;
    }
}

