package com.ltc.btl_javafx.controller;


import com.ltc.btl_javafx.DAO.DAO_Bill;
import com.ltc.btl_javafx.DAO.DAO_Room;
import com.ltc.btl_javafx.DAO.DAO_Service;
import com.ltc.btl_javafx.DAO.TenantFunctionImpl;
//import com.ltc.btl_javafx.DAO.DAO_Tenant;
import com.ltc.btl_javafx.application.Support;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import com.ltc.btl_javafx.model.Bill;
import com.ltc.btl_javafx.model.HomeTown;
import com.ltc.btl_javafx.model.Room;

public class calculateBillController implements Initializable {
    @FXML
    private TextField electricNumberTextField;
    @FXML
    private TextField waterNumberTextField;
    @FXML
    private ChoiceBox<String> HomeTownChoiceBox;
    @FXML
    private ChoiceBox<String> roomChoiceBox;
    @FXML
    private DatePicker invoiceDatePicker;
    @FXML
    private Label IDAccountLabel;
    private ObservableList<Room> rentedRoomList;
    private Bill bill;
    private String price;
    ArrayList<String> arrRoomList;
    public static boolean checkaddBill;

    public calculateBillController() {
    }

    public boolean isValidPrice(String price) {
        if (price.matches("\\d+")) {
            int temp = Integer.parseInt(price);
            if (temp >= 0) {
                return true;
            }
        }

        return false;
    }

    public void setRoomId(ActionEvent event) {
        this.arrRoomList.clear();
        this.rentedRoomList.clear();
        this.roomChoiceBox.getItems().clear();
        this.rentedRoomList.addAll(DAO_Room.getInstance().selectByCondition((String)this.HomeTownChoiceBox.getValue(), "1", (String)null));
        Iterator var3 = this.rentedRoomList.iterator();

        while(var3.hasNext()) {
            Room x = (Room)var3.next();
            this.arrRoomList.add(x.getRoomID());
        }

        var3 = Support.billList.iterator();

        while(var3.hasNext()) {
            Bill x = (Bill)var3.next();
            if (this.arrRoomList.contains(x.getRoomID())) {
                this.arrRoomList.remove(x.getRoomID());
            }
        }

        var3 = this.arrRoomList.iterator();

        while(var3.hasNext()) {
            String id = (String)var3.next();
            this.roomChoiceBox.getItems().add(id);
        }

    }

    public String getAllPrice() {
        if (this.HomeTownChoiceBox.getValue() != null && this.roomChoiceBox.getValue() != null && !this.electricNumberTextField.getText().isBlank()) {
            if (this.HomeTownChoiceBox.getValue() != null && this.roomChoiceBox.getValue() != null && !this.electricNumberTextField.getText().isBlank()) {
                if (!this.isValidPrice(this.electricNumberTextField.getText())) {
                    return "-1";
                } else {
                    String anotherPriceService = DAO_Service.getInstance().sumPrice((String)this.HomeTownChoiceBox.getValue(), "KHÁC");
                    BigDecimal electricPriceService = (new BigDecimal(DAO_Service.getInstance().sumPrice((String)this.HomeTownChoiceBox.getValue(), "ĐIỆN"))).multiply(new BigDecimal(this.electricNumberTextField.getText()));
                    BigDecimal waterPriceService = (new BigDecimal(DAO_Service.getInstance().sumPrice((String)this.HomeTownChoiceBox.getValue(), "NƯỚC"))).multiply(new BigDecimal(this.waterNumberTextField.getText()));
                    BigDecimal priceRoom = new BigDecimal(DAO_Room.getInstance().sumPrice((String)this.HomeTownChoiceBox.getValue(), (String)this.roomChoiceBox.getValue()));
                    new BigDecimal(0);
                    BigDecimal result = priceRoom.add(electricPriceService).add(new BigDecimal(anotherPriceService)).add(waterPriceService);
                    return result.toString();
                }
            } else {
                return "0";
            }
        } else {
            return "0";
        }
    }

    @FXML
    public void calculate(ActionEvent event) {
        this.price = this.getAllPrice();
        if ("-1".equals(this.price)) {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", (String)null, "Vui lòng nhập đúng số điện !");
        } else if ("0".equals(this.price)) {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", (String)null, "Vui lòng chọn đầy đủ thông tin !");
        } else {
            String tenantID = TenantFunctionImpl.getInstance().selectTenantByMaP(/*(String)this.HomeTownChoiceBox.getValue(),*/ (String)this.roomChoiceBox.getValue()).get(0).getTenantID();
            this.bill = new Bill(Support.IDAccount, (String)this.HomeTownChoiceBox.getValue(), (String)this.roomChoiceBox.getValue(), tenantID, Integer.parseInt(this.electricNumberTextField.getText()), (LocalDate)this.invoiceDatePicker.getValue(), this.price);
            Support.billList.add(this.bill);
            DAO_Bill.getInstance().insertData(this.bill, "", "");
            checkaddBill = true;
            BillController.secondStage.close();
        }

    }

    @FXML
    public void CloseStage(ActionEvent event) {
        BillController.secondStage.close();
    }

    public void addPane(GridPane billGridPane) throws IOException {
        if (this.bill != null) {
            int row = Support.billList.size();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/cardBill.fxml"));
            AnchorPane pane = (AnchorPane)loader.load();
            cardBillController calculateForm = (cardBillController)loader.getController();
            calculateForm.setData(this.bill);
            billGridPane.add(pane, 0, row++);
        }

    }

    public void initialize(URL arg0, ResourceBundle arg1) {
        checkaddBill = false;

        this.arrRoomList = new ArrayList();
        this.bill = null;
        this.invoiceDatePicker.setValue(LocalDate.now());
        this.rentedRoomList = FXCollections.observableArrayList();
        Iterator var4 = Support.homeList.iterator();

        while(var4.hasNext()) {
            HomeTown x = (HomeTown)var4.next();
            this.HomeTownChoiceBox.getItems().addAll(new String[]{x.getTownID()});
        }

        this.HomeTownChoiceBox.setOnAction(this::setRoomId);
    }
}

