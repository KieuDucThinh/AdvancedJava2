package com.ltc.btl_javafx.controller;



import com.ltc.btl_javafx.DAO.DAO_HomeTown;
import com.ltc.btl_javafx.DAO.DAO_Room;
import com.ltc.btl_javafx.DAO.DAO_Service;
import com.ltc.btl_javafx.application.Support;
import com.ltc.btl_javafx.application.TitleForms;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.ltc.btl_javafx.model.HomeTown;
import com.ltc.btl_javafx.model.Room;
import com.ltc.btl_javafx.model.Service;

public class RoomController implements Initializable {
    @FXML
    private Button HomeButton;
    @FXML
    private Button RoomButton;
    @FXML
    private Button TenantButton;
    @FXML
    private Button ServiceButton;
    @FXML
    private Button BillButton;
    @FXML
    private Button StatisticsButton;
    @FXML
    private Label accountNameLabel;
    @FXML
    private Button LogoutButton;
    @FXML
    private TextField HomeTownIDTextField;
    @FXML
    private TextField HomeTownAddressTextField;
    @FXML
    private TableView<HomeTown> HomeTownTableView;
    @FXML
    private TableColumn<HomeTown, Integer> ordinalHomeTown;
    @FXML
    private TableColumn<HomeTown, String> HomeTownID_col;
    @FXML
    private TableColumn<HomeTown, String> HomeTownAddress_col;
    @FXML
    private Tab RoomTab;
    @FXML
    private ComboBox<String> chooseTownComboBox;
    @FXML
    private ComboBox<String> chooseStateComboBox;
    @FXML
    private ComboBox<String> chooseTownComboBox2;
    @FXML
    private TextField searchRoomTextField;
    @FXML
    private TextField RoomIDTextField;
    @FXML
    private TextField RoomTypeTextField;
    @FXML
    private TextField RoomPriceTextField;
    @FXML
    private TableView<Room> RoomTableView;
    @FXML
    private TableColumn<Room, Integer> ordinalRoom;
    @FXML
    private TableColumn<Room, String> RoomID_col;
    @FXML
    private TableColumn<Room, String> TypeRoom_col;
    @FXML
    private TableColumn<Room, String> PriceRoom_col;
    @FXML
    private TableColumn<Room, String> StateRoom;
    private ObservableList<Room> checkListRoom;
    private ObservableList<Room> listRoomState;
    private ObservableList<Service> checkListService;
    private int rankAccount;

    public RoomController() {
    }

    public boolean checkRank(ObservableList<Room> list) {
        if (this.rankAccount == 0 && list.size() == 5) {
            return false;
        } else if (this.rankAccount == 1 && list.size() == 10) {
            return false;
        } else if (this.rankAccount == 2 && list.size() == 20) {
            return false;
        } else if (this.rankAccount == 3 && list.size() == 50) {
            return false;
        } else {
            return this.rankAccount != 4 || list.size() != 100;
        }
    }

    public void validRoomTab(ObservableList<HomeTown> list) {
        if (list.size() > 0) {
            this.RoomTab.setDisable(false);
        } else {
            this.RoomTab.setDisable(true);
        }

    }

    public boolean checkHomeTownEmpty() {
        return this.HomeTownIDTextField.getText().isEmpty() || this.HomeTownAddressTextField.getText().isEmpty();
    }

    public void setHomeTownToTable(ObservableList<HomeTown> list) {
        this.ordinalHomeTown.setCellValueFactory((cellData) -> {
            return (new SimpleIntegerProperty(list.indexOf(cellData.getValue()) + 1)).asObject();
        });
        this.HomeTownID_col.setCellValueFactory(new PropertyValueFactory("townID"));
        this.HomeTownAddress_col.setCellValueFactory(new PropertyValueFactory("address"));
        this.HomeTownTableView.setItems(list);
    }

    @FXML
    public void addHomeTown() {
        try {
            if (this.checkHomeTownEmpty()) {
                Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI THÊM THÔNG TIN", "Vui lòng điền đầy đủ thông tin!");
            } else {
                boolean duplicatedHomeTownID = false;
                Iterator var3 = Support.homeList.iterator();

                HomeTown x;
                while(var3.hasNext()) {
                    x = (HomeTown)var3.next();
                    if (x.getTownID().equalsIgnoreCase(this.HomeTownIDTextField.getText())) {
                        duplicatedHomeTownID = true;
                        break;
                    }
                }

                if (!duplicatedHomeTownID) {
                    x = new HomeTown(this.HomeTownIDTextField.getText(), this.HomeTownAddressTextField.getText());
                    DAO_HomeTown.getInstance().insertData(x, "", "");
                    Support.homeList.add(x);
                    this.validRoomTab(Support.homeList);
                    this.chooseTownComboBox.getItems().add(x.toString());
                    this.chooseTownComboBox2.getItems().add(x.getTownID());
                    Support.NoticeAlert(AlertType.INFORMATION, "THÔNG BÁO", (String)null, "Thêm thông tin thành công !");
                } else {
                    Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI THÊM THÔNG TIN", "Mã tòa bị trùng !\nMã tòa không phân biệt chữ hoa và chữ thường\nVui lòng nhập mã tòa khác !");
                }
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }
    @FXML
    private void handleExportButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Đặt tiêu đề cho hộp thoại lưu file
        fileChooser.setTitle("Save Excel File");

        // Đặt phần mở rộng cho file
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);

        // Mở hộp thoại lưu file và lấy file được chọn
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            DAO_Room daoRoom = DAO_Room.getInstance();
            daoRoom.exportToExcel(file.getAbsolutePath());


            // Hiển thị thông báo sau khi xuất file thành công
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Bạn đã xuất file thành công!");
            alert.showAndWait();
        }
    }


    @FXML
    public void viewHomeTown() {
        int infoData = this.HomeTownTableView.getSelectionModel().getSelectedIndex();
        if (infoData > -1) {
            this.HomeTownIDTextField.setText((String)this.HomeTownID_col.getCellData(infoData));
            this.HomeTownAddressTextField.setText((String)this.HomeTownAddress_col.getCellData(infoData));
        }
    }

    @FXML
    public void updateHomeTown() {
        if (this.checkHomeTownEmpty()) {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI CẬP NHẬT THÔNG TIN", "Vui lòng điền đầy đủ thông tin!");
        } else {
            try {
                boolean compareHomeTownID = false;
                Iterator var3 = Support.homeList.iterator();

                HomeTown x;
                while(var3.hasNext()) {
                    x = (HomeTown)var3.next();
                    if (x.getTownID().equalsIgnoreCase(this.HomeTownIDTextField.getText())) {
                        compareHomeTownID = true;
                        break;
                    }
                }

                if (compareHomeTownID) {
                    x = new HomeTown(this.HomeTownIDTextField.getText(), this.HomeTownAddressTextField.getText());
                    DAO_HomeTown.getInstance().updateData(x, "", "");
                    Support.homeList.clear();
                    Support.homeList.addAll(DAO_HomeTown.getInstance().selectALL());
                    this.HomeTownTableView.setItems(Support.homeList);
                    Support.NoticeAlert(AlertType.INFORMATION, "THÔNG BÁO", (String)null, "Sửa thông tin thành công !");
                } else {
                    Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI SỬA THÔNG TIN", "Không thể sửa mã tòa nhà!");
                }
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

    }

    @FXML
    public void eraseHomeTownInTextField() {
        this.HomeTownIDTextField.setText("");
        this.HomeTownAddressTextField.setText("");
    }

    @FXML
    public void deleteHomeTown() {
        if (this.checkListRoom.size() > 0) {
            this.checkListRoom.clear();
        } else if (this.checkListService.size() > 0) {
            this.checkListService.clear();
        }

        HomeTown selection = (HomeTown)this.HomeTownTableView.getSelectionModel().getSelectedItem();
        if (selection == null) {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI XÓA THÔNG TIN", "Không thể xóa tòa nhà !\nVui lòng chọn tòa nhà cần xóa trên bảng ");
        } else {
            this.checkListRoom.addAll(DAO_Room.getInstance().selectByCondition(selection.getTownID(), (String)null, (String)null));
            this.checkListService.addAll(DAO_Service.getInstance().selectByCondition(selection.getTownID(), (String)null, (String)null));
            if (this.checkListRoom.size() > 0) {
                Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI XÓA THÔNG TIN", "Không thể xóa tòa nhà hiện đang có phòng !\nHãy xóa phòng của tòa cần xóa trước");
            } else if (this.checkListService.size() > 0) {
                Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI XÓA THÔNG TIN", "Không thể xóa tòa nhà hiện đang có dịch vụ !\nHãy xóa dịch vụ của tòa cần xóa trước");
            } else {
                DAO_HomeTown.getInstance().deleteData(selection, "");
                Support.homeList.remove(selection);
                this.chooseTownComboBox.getItems().remove(selection.toString());
                this.chooseTownComboBox2.getItems().remove(selection.getTownID());
                this.HomeTownTableView.setItems(Support.homeList);
                this.validRoomTab(Support.homeList);
                Support.NoticeAlert(AlertType.INFORMATION, "THÔNG BÁO", (String)null, "Xóa thông tin thành công !");
            }
        }

    }

    public boolean checkRoomEmpty() {
        return this.chooseTownComboBox2.getSelectionModel().isEmpty() || this.RoomIDTextField.getText().isEmpty() || this.RoomTypeTextField.getText().isEmpty() || this.RoomPriceTextField.getText().isEmpty();
    }

    public boolean isValidPrice(String price) {
        if (price.matches("\\d*\\.?0*$")) {
            double temp1 = Double.parseDouble(price);
            if (temp1 >= 0.0) {
                return true;
            }
        } else if (price.matches("\\d+")) {
            int temp = Integer.parseInt(price);
            if (temp >= 0) {
                return true;
            }
        }

        return false;
    }

    private String formatPrice(String price) {
        double temp = Double.parseDouble(price);
        NumberFormat formatNum = NumberFormat.getCurrencyInstance(Locale.US);
        String formattedNum = formatNum.format(temp);
        return formattedNum.replace("$", "");
    }

    public String parsePrice(String price) {
        String temp = price.replace(",", "");
        int position = temp.indexOf(".");
        return temp.substring(0, position);
    }

    public void setRoomToTable(ObservableList<Room> list) {
        this.ordinalRoom.setCellValueFactory((cellData) -> {
            return (new SimpleIntegerProperty(list.indexOf(cellData.getValue()) + 1)).asObject();
        });
        this.RoomID_col.setCellValueFactory(new PropertyValueFactory("roomID"));
        this.TypeRoom_col.setCellValueFactory(new PropertyValueFactory("typeroom"));
        this.StateRoom.setCellValueFactory(new PropertyValueFactory("stateroom"));
        this.PriceRoom_col.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty(this.formatPrice(((Room)cellData.getValue()).getPriceroom()));
        });
        this.RoomTableView.setItems(list);
    }

    @FXML
    public void searchRoom() {
        String text = this.searchRoomTextField.getText().trim();
        if (!text.isEmpty()) {
            this.listRoomState.clear();
            if (this.chooseTownComboBox.getValue() == null && this.chooseStateComboBox.getValue() == null) {
                this.listRoomState.addAll(DAO_Room.getInstance().searching(text, "", ""));
            } else if (this.chooseTownComboBox.getValue() == null && this.chooseStateComboBox.getValue() != null) {
                if ("Trống".equals(this.chooseStateComboBox.getValue())) {
                    this.listRoomState.addAll(DAO_Room.getInstance().searching(text, "", "0"));
                } else if ("Đã thuê".equals(this.chooseStateComboBox.getValue())) {
                    this.listRoomState.addAll(DAO_Room.getInstance().searching(text, "", "1"));
                }
            } else {
                String town;
                String[] codeHomeTownID;
                if (this.chooseTownComboBox.getValue() != null && this.chooseStateComboBox.getValue() == null) {
                    town = (String)this.chooseTownComboBox.getValue();
                    codeHomeTownID = town.split("-");
                    this.listRoomState.addAll(DAO_Room.getInstance().searching(text, codeHomeTownID[0], ""));
                } else {
                    town = (String)this.chooseTownComboBox.getValue();
                    codeHomeTownID = town.split("-");
                    if ("Trống".equals(this.chooseStateComboBox.getValue())) {
                        this.listRoomState.addAll(DAO_Room.getInstance().searching(text, codeHomeTownID[0], "0"));
                    } else if ("Đã thuê".equals(this.chooseStateComboBox.getValue())) {
                        this.listRoomState.addAll(DAO_Room.getInstance().searching(text, codeHomeTownID[0], "1"));
                    }
                }
            }

            this.setRoomToTable(this.listRoomState);
        }
    }

    @FXML
    public void refreshRoom() {
        this.listRoomState.clear();
        this.chooseTownComboBox.setValue((String) null);
        this.chooseStateComboBox.setValue((String) null);
        this.setRoomToTable(Support.roomList);
    }

    @FXML
    public void filterMenuRoom() {
        String town;
        String[] codeHomeTownID;
        if (this.chooseStateComboBox.getValue() == null && this.chooseTownComboBox.getValue() != null) {
            town = (String)this.chooseTownComboBox.getValue();
            codeHomeTownID = town.split("-");
            this.listRoomState.clear();
            this.listRoomState.addAll(DAO_Room.getInstance().selectByCondition(codeHomeTownID[0], (String)null, (String)null));
            this.setRoomToTable(this.listRoomState);
        } else if (this.chooseStateComboBox.getValue() == null && this.chooseTownComboBox.getValue() == null) {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", (String)null, "Vui lòng chọn 1 trong 2 mục !");
        } else if (this.chooseStateComboBox.getValue() != null && this.chooseTownComboBox.getValue() == null) {
            this.listRoomState.clear();
            if ("Trống".equals(this.chooseStateComboBox.getValue())) {
                this.listRoomState.addAll(DAO_Room.getInstance().selectByCondition((String)null, "0", (String)null));
            } else if ("Đã thuê".equals(this.chooseStateComboBox.getValue())) {
                this.listRoomState.addAll(DAO_Room.getInstance().selectByCondition((String)null, "1", (String)null));
            }

            this.setRoomToTable(this.listRoomState);
        } else if (this.chooseStateComboBox.getValue() != null && this.chooseTownComboBox.getValue() != null) {
            town = (String)this.chooseTownComboBox.getValue();
            codeHomeTownID = town.split("-");
            this.listRoomState.clear();
            if ("Trống".equals(this.chooseStateComboBox.getValue())) {
                this.listRoomState.addAll(DAO_Room.getInstance().selectByCondition(codeHomeTownID[0], "0", (String)null));
            } else if ("Đã thuê".equals(this.chooseStateComboBox.getValue())) {
                this.listRoomState.addAll(DAO_Room.getInstance().selectByCondition(codeHomeTownID[0], "1", (String)null));
            }

            this.setRoomToTable(this.listRoomState);
        }

    }

    @FXML
    public void addRoom() {
        try {
            if (this.checkRoomEmpty()) {
                Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI THÊM THÔNG TIN", "Vui lòng điền đầy đủ thông tin!");
            } else if (!this.isValidPrice(this.RoomPriceTextField.getText())) {
                Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI THÊM THÔNG TIN", "Vui lòng điền mệnh giá tiền hợp lệ !");
            } else {
                boolean duplicatedRoomID = false;
                Room x;
                if (Support.roomList.size() > 0) {
                    this.listRoomState.clear();
                    this.listRoomState.addAll(DAO_Room.getInstance().selectByCondition((String)this.chooseTownComboBox2.getValue(), (String)null, (String)null));
                    if (this.listRoomState.size() > 0) {
                        Iterator var3 = this.listRoomState.iterator();

                        while(var3.hasNext()) {
                            x = (Room)var3.next();
                            if (x.getRoomID().equalsIgnoreCase(this.RoomIDTextField.getText())) {
                                duplicatedRoomID = true;
                                break;
                            }
                        }
                    }
                }

                if (!duplicatedRoomID) {
                    if (!this.checkRank(Support.roomList)) {
                        Support.NoticeAlert(AlertType.INFORMATION, "THÔNG BÁO", "Số phòng đạt giới hạn", "Vui lòng nâng cấp tài khoản để tiếp tục sử dụng chức năng này !");
                    } else {
                        x = new Room(this.RoomIDTextField.getText(), this.RoomTypeTextField.getText(), "Trống", this.RoomPriceTextField.getText());
                        DAO_Room.getInstance().insertData(x, (String)this.chooseTownComboBox2.getValue(), "");
                        Support.roomList.add(x);
                        Support.NoticeAlert(AlertType.INFORMATION, "THÔNG BÁO", (String)null, "Thêm thông tin thành công !");
                    }
                } else {
                    Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI THÊM THÔNG TIN", "Mã phòng bị trùng !\nMã phòng không phân biệt chữ hoa và chữ thường\nVui lòng nhập mã phòng khác !");
                }
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    @FXML
    public void viewRoom() {
        int infoData = this.RoomTableView.getSelectionModel().getSelectedIndex();
        if (infoData > -1) {
            this.RoomIDTextField.setText((String)this.RoomID_col.getCellData(infoData));
            this.RoomTypeTextField.setText((String)this.TypeRoom_col.getCellData(infoData));
            this.RoomPriceTextField.setText(this.parsePrice((String)this.PriceRoom_col.getCellData(infoData)));
        }
    }

    @FXML
    public void updateRoom() {
        if (this.checkRoomEmpty()) {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI CẬP NHẬT THÔNG TIN", "Vui lòng chọn thông tin cần sửa trên bảng !");
        } else if (!this.isValidPrice(this.RoomPriceTextField.getText())) {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI CẬP NHẬT THÔNG TIN", "Vui lòng điền mệnh giá tiền hợp lệ !");
        } else {
            try {
                boolean compareRoomID = false;
                if (Support.roomList.size() > 0) {
                    Iterator var3 = Support.roomList.iterator();

                    Room x;
                    while(var3.hasNext()) {
                        x = (Room)var3.next();
                        if (x.getRoomID().equals(this.RoomIDTextField.getText())) {
                            compareRoomID = true;
                            break;
                        }
                    }

                    if (compareRoomID) {
                        x = new Room(this.RoomIDTextField.getText(), this.RoomTypeTextField.getText(), "Trống", this.RoomPriceTextField.getText());
                        DAO_Room.getInstance().updateData(x, (String)this.chooseTownComboBox2.getValue(), "");
                        Support.roomList.clear();
                        Support.roomList.addAll(DAO_Room.getInstance().selectALL());
                        this.setRoomToTable(Support.roomList);
                        Support.NoticeAlert(AlertType.INFORMATION, "THÔNG BÁO", (String)null, "Sửa thông tin thành công !");
                    } else {
                        Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI SỬA THÔNG TIN", "Không thể sửa mã phòng!");
                    }
                } else {
                    Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI SỬA THÔNG TIN", "Danh sách phòng trống !\nVui lòng thêm phòng !");
                }
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

    }

    @FXML
    public void eraseRoomInTextField() {
        this.chooseTownComboBox2.setValue((String) null);
        this.RoomIDTextField.setText("");
        this.RoomTypeTextField.setText("");
        this.RoomPriceTextField.setText("");
    }

    @FXML
    public void deleteRoom() {
        Room selection = (Room)this.RoomTableView.getSelectionModel().getSelectedItem();
        if (selection == null) {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI XÓA THÔNG TIN", "Không thể xóa phòng !\nVui lòng chọn phòng cần xóa trên bảng ");
        } else if ("Đã thuê".equals(selection.getStateroom())) {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI XÓA THÔNG TIN", "Không thể xóa tòa phòng đang có khách thuê ! ");
        } else {
            DAO_Room.getInstance().deleteData(selection, "");
            Support.roomList.remove(selection);
            this.RoomTableView.setItems(Support.roomList);
            Support.NoticeAlert(AlertType.INFORMATION, "THÔNG BÁO", (String)null, "Xóa thông tin thành công !");
        }

    }

    @FXML
    public void OpenHomePageForm(ActionEvent event) {
        try {
            Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/HomePageDesign.fxml"));
            Support.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Support.scene = new Scene(Support.root);
            Support.stage.setScene(Support.scene);
            Support.stage.setTitle(TitleForms.TitleHomePageForm);
            Support.stage.show();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    @FXML
    public void OpenTenantForm(ActionEvent event) {
        try {
            Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/TenantDesign_1.fxml"));
            Support.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Support.scene = new Scene(Support.root);
            Support.stage.setScene(Support.scene);
            Support.stage.setTitle(TitleForms.TitleTenantForm);
            Support.stage.show();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    @FXML
    public void OpenServiceForm(ActionEvent event) {
        try {
            Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/ServiceDesign.fxml"));
            Support.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Support.scene = new Scene(Support.root);
            Support.stage.setScene(Support.scene);
            Support.stage.setTitle(TitleForms.TitleServiceForm);
            Support.stage.show();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    @FXML
    public void OpenBillForm(ActionEvent event) {
        try {
            Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/BillDesign.fxml"));
            Support.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Support.scene = new Scene(Support.root);
            Support.stage.setScene(Support.scene);
            Support.stage.setTitle(TitleForms.TitleBillForm);
            Support.stage.show();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    @FXML
    public void OpenStatisticsForm(ActionEvent event) {
        try {
            Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/StatisticsDesign.fxml"));
            Support.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Support.scene = new Scene(Support.root);
            Support.stage.setScene(Support.scene);
            Support.stage.setTitle(TitleForms.TitleStatisticsForm);
            Support.stage.show();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    @FXML
    public void Logout(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("THÔNG BÁO");
        alert.setHeaderText((String)null);
        alert.setContentText("Bạn có muốn đăng xuất ?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            ((Stage)this.LogoutButton.getScene().getWindow()).close();

            try {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/LoginDesign.fxml"));
                Parent root = (Parent)loader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.getIcons().add(Support.icon);
                stage.setTitle(TitleForms.TitleLoginForm);
                stage.show();
            } catch (IOException var7) {
                var7.printStackTrace();
            }
        }

    }

    public void initialize(URL arg0, ResourceBundle arg1) {
        this.rankAccount = Support.pointRank;
        this.validRoomTab(Support.homeList);
        this.RoomButton.setStyle(Support.colorButton);
        this.accountNameLabel.setText(Support.NameAccount);
        this.listRoomState = FXCollections.observableArrayList();
        this.checkListRoom = FXCollections.observableArrayList();
        this.checkListService = FXCollections.observableArrayList();
        this.chooseStateComboBox.getItems().addAll(new String[]{"Trống", "Đã thuê"});
        Iterator var4 = Support.homeList.iterator();

        while(var4.hasNext()) {
            HomeTown x = (HomeTown)var4.next();
            this.chooseTownComboBox.getItems().add(x.toString());
            this.chooseTownComboBox2.getItems().add(x.getTownID());
        }

        this.setHomeTownToTable(Support.homeList);
        Support.roomList.clear();
        Support.roomList.addAll(DAO_Room.getInstance().selectALL());
        this.setRoomToTable(Support.roomList);
    }
}

