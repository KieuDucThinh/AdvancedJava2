package com.ltc.btl_javafx.application;

import com.ltc.btl_javafx.model.HomeTown;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.ltc.btl_javafx.model.Bill;

import com.ltc.btl_javafx.model.Member;
import com.ltc.btl_javafx.model.Room;
import com.ltc.btl_javafx.model.RoomAndTenant;
import com.ltc.btl_javafx.model.Service;
import com.ltc.btl_javafx.model.Tenant;

public class Support {
    public static int pointRank;
    public static String IDAccount;
    public static String NameAccount;
    public static String rankAccount;
    public static ObservableList<HomeTown> homeList;
    public static ObservableList<Room> roomList;
    public static ObservableList<Tenant> tenantList;
    public static ObservableList<Member> currMemberList;
    public static ObservableList<RoomAndTenant> RoomAndTenantList;
    public static ObservableList<Service> serviceList;
    public static ObservableList<Bill> billList;
    public static Parent root;
    public static Stage stage;
    public static Scene scene;
    public static Image icon;
    public static String colorButton = "-fx-background-color:#137b9e";
    public static Alert alert;

    public Support() {
    }

    public static void NoticeAlert(Alert.AlertType type, String title, String header, String content) {
        alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
