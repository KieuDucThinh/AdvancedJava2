package com.ltc.btl_javafx.controller;


import com.ltc.btl_javafx.application.Support;
import com.ltc.btl_javafx.model.Tenant;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
//import com.ltc.btl_javafx.model.Room;

public class cardRoomController implements Initializable {
    @FXML
    private Button addTenantButton;
    @FXML
    private Button showTenantButton;
    @FXML
    private Label priceroomLable;
    @FXML
    private Label roomIDLabel;
    @FXML
    private Label stateroomLabel;
    @FXML
    private Label typeroomLabel;
    
    private double priceRoom;
    public static String idRoom = "";

    public cardRoomController() {
    }

    public void setRoom(Tenant tenant) {
        this.roomIDLabel.setText(tenant.getRoom().getRoomID());
        this.typeroomLabel.setText(tenant.getRoom().getTyperoom());
        String state;
        state = (tenant.getRoom().getStateroom().compareTo("1") == 0) ? "Đã thuê" : "Trống";
        
        if ("Đã thuê".equals(state)) {
            this.addTenantButton.setVisible(false);
            this.showTenantButton.setVisible(true);
            this.stateroomLabel.setTextFill(Color.RED);
        } else {
            this.stateroomLabel.setTextFill(Color.BLUE);
        }

        this.stateroomLabel.setText(state);
        this.priceRoom = Double.parseDouble(tenant.getRoom().getPriceroom());
        this.priceroomLable.setText(NumberFormat.getNumberInstance(Locale.US).format(this.priceRoom));
    }

//     public void setRoom(Tenant room) {
//        this.roomIDLabel.setText(room.getRoom().getRoomID());
//        //this.typeroomLabel.setText(room.getTyperoom());
//        //String state = room.getStateroom();
//        String state = "Đã thuê";
//        if ("Đã thuê".equals(state)) {
//            this.addTenantButton.setVisible(false);
//            this.showTenantButton.setVisible(true);
//            this.stateroomLabel.setTextFill(Color.RED);
//        } else {
//            this.stateroomLabel.setTextFill(Color.BLUE);
//        }
//
//        this.stateroomLabel.setText(state);
//        //this.priceRoom = Double.parseDouble(room.getPriceroom());
//        //this.priceroomLable.setText(NumberFormat.getNumberInstance(Locale.US).format(this.priceRoom));
//    }
    
    @FXML
    public void addTenant(ActionEvent event) {
        idRoom = this.roomIDLabel.getText();

        try {
            Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/addTenantDesign.fxml"));
            Support.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Support.scene = new Scene(Support.root);
            Support.stage.setScene(Support.scene);
            Support.stage.show();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    @FXML
    public void showTenant(ActionEvent event) {
        idRoom = this.roomIDLabel.getText();

        try {
            Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/cardTenantDesign_1.fxml"));
            Support.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Support.scene = new Scene(Support.root);
            Support.stage.setScene(Support.scene);
            Support.stage.show();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public void initialize(URL arg0, ResourceBundle arg1) {
    }
}

