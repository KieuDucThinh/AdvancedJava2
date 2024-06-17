package com.ltc.btl_javafx.controller;


//import com.ltc.btl_javafx.DAO.DAO_Room;

import com.ltc.btl_javafx.DAO.DAO_Room;
import com.ltc.btl_javafx.DAO.MemberFunctionImpl;
import com.ltc.btl_javafx.DAO.TenantFunctionImpl;
import com.ltc.btl_javafx.application.CitizenIdentification;
import com.ltc.btl_javafx.application.LocalDateMonthY;
import com.ltc.btl_javafx.application.Support;
import com.ltc.btl_javafx.application.TitleForms;
import com.ltc.btl_javafx.model.HomeTown;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.ltc.btl_javafx.model.Member;
import com.ltc.btl_javafx.model.Room;
import com.ltc.btl_javafx.model.Tenant;
import javafx.application.Platform;
import javafx.collections.ObservableList;

public class addTenantController implements Initializable {

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
    private Button backButton;
    @FXML
    private Button saveButton;
    @FXML
    private Label getTownIDLabel;
    @FXML
    private Label getRoomIDLabel;
    @FXML
    private DatePicker BirthTenantDatePicker;
    @FXML
    private RadioButton FemaleTenantRadioButton;
    @FXML
    private TextField IDCitizenMemberTextField;
    @FXML
    private TextField IDCitizenTenantTextField;
    @FXML
    private RadioButton MaleTenantRadioButton;
    @FXML
    private RadioButton MemberFemaleRadionButton;
    @FXML
    private RadioButton MemberMaleRadioButton;
    @FXML
    private TextField MemberNameTextField;
    @FXML
    private TextField NameTenantTextField;
    @FXML
    private TextField TenantIDTextField;
    @FXML
    private DatePicker birthMemberDatePicker;
    @FXML
    private TextField phoneNumMemberTextField;
    @FXML
    private TextField phoneNumTenantTextField;
    @FXML
    private TextField placeMemberTextField;
    @FXML
    private TextField placeTenantTextField;
    @FXML
    private DatePicker rentDatePicker;
    @FXML
    private Tab TenantTab;
    @FXML
    private Tab MemberTab;
    @FXML
    private TableView<Member> MemberTableView;
    @FXML
    private TableColumn<Member, Integer> order;
    @FXML
    private TableColumn<Member, String> CitizenIDMember_col;
    @FXML
    private TableColumn<Member, String> MemberSex_col;
    @FXML
    private TableColumn<Member, String> NameMember_col;
    @FXML
    private TableColumn<Member, LocalDate> birthMember_col;
    @FXML
    private TableColumn<Member, String> phoneNumMember_col;
    @FXML
    private TableColumn<Member, String> placeMember_col;
    private boolean checkAddingCompleted;

    private ObservableList<Tenant> tenantList;
    private CitizenIdentification citizenIdentification = new CitizenIdentification();
    private double currentWidth;
    private double currentHeight;
    private double currentX;
    private double currentY;

    public addTenantController() {
    }

    public void lockInfor() {
        this.TenantIDTextField.setEditable(false);
        this.NameTenantTextField.setEditable(false);
        this.MaleTenantRadioButton.setDisable(true);
        this.FemaleTenantRadioButton.setDisable(true);
        this.BirthTenantDatePicker.setDisable(true);
        this.IDCitizenTenantTextField.setEditable(false);
        this.phoneNumTenantTextField.setEditable(false);
        this.placeTenantTextField.setEditable(false);
        this.rentDatePicker.setDisable(true);
    }

    public void addTenant() {
        if (this.checkAddingCompleted) {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", (String) null, "Bạn đã thêm chủ phòng rồi !\nHay sang phần Thành Viên để sử dụng chức năng này !");
        } else if (this.checkTenantInvalid()) {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI THÊM THÔNG TIN", "Hãy kiểm tra lại thông tin!");
        } else {
            String sex = "";
            if (this.MaleTenantRadioButton.isSelected()) {
                sex = this.MaleTenantRadioButton.getText();
            } else {
                sex = this.FemaleTenantRadioButton.getText();
            }

            Tenant tenant = new Tenant(this.TenantIDTextField.getText(), this.NameTenantTextField.getText(), sex, (LocalDate) this.BirthTenantDatePicker.getValue(), this.IDCitizenTenantTextField.getText(), this.phoneNumTenantTextField.getText(), this.placeTenantTextField.getText(), (LocalDate) this.rentDatePicker.getValue());
            this.tenantList.add(tenant);
            Room room = new Room();
            room.setRoomID(this.getRoomIDLabel.getText());
            HomeTown homeTown = new HomeTown();
            homeTown.setTownID(this.getTownIDLabel.getText());
            tenant.setRoom(room);
            tenant.setHomeTown(homeTown);
            if (TenantFunctionImpl.getInstance().addTenant(tenant)) {
                this.lockInfor();
                Support.NoticeAlert(AlertType.CONFIRMATION, "THÔNG BÁO", (String) null, "Thêm thông tin thành công !");
                this.checkAddingCompleted = true;
            } else {
                Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI THÊM THÔNG TIN", "Kiểm tra lại thông tin !");
            }
        }

    }

    @FXML
    public void addMember() {
        if (this.MemberTab.isSelected()) {
            if (this.checkMemberInvalid()) {
                Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI THÊM THÔNG TIN", "Vui lòng kiểm tra lại thông tin!");
            } else {
                int index;
                String sex;

                if (this.MemberMaleRadioButton.isSelected()) {
                    sex = this.MemberMaleRadioButton.getText();
                } else {
                    sex = this.MemberFemaleRadionButton.getText();
                }

                String idMember = "TV";
                for (int i = 1; i <= 6; i++) {
                    index = (int) (Math.random() * 10);
                    idMember+=index;
                }

                Member member = new Member(idMember, this.MemberNameTextField.getText(), sex, (LocalDate) this.birthMemberDatePicker.getValue(), this.IDCitizenMemberTextField.getText(), this.phoneNumMemberTextField.getText(), this.placeMemberTextField.getText());
                Tenant tenant = new Tenant();
                tenant.setTenantID(this.TenantIDTextField.getText());
                member.setTenant(tenant);
//                if(this.phoneNumMemberTextField.getText().equals(""))
//                    member.setPhoneNum(null);
//                if (member.getPhoneNum().equals("")) {
//                    member.setPhoneNum(null);
//                }

                if (MemberFunctionImpl.getInstance().addMember(member)) {
                    Support.currMemberList.add(member);
                    this.setMemberToTable(Support.currMemberList);
                    Support.NoticeAlert(AlertType.INFORMATION, "THÔNG BÁO", (String) null, "Thêm thông tin thành công !");

                } else {
                    Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI THÊM THÔNG TIN", "Hãy kiểm tra lại thông tin của mình !");
                }
            }
        } else {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI CHỨC NĂNG", "Chức năng chỉ dành cho thành viên phòng !");
        }

    }

    public boolean checkMemberInvalid() {
        String CCCD = this.IDCitizenMemberTextField.getText();
        String SDT = this.phoneNumMemberTextField.getText();
        LocalDateMonthY time = new LocalDateMonthY();
        int year = time.getYearNow() - this.BirthTenantDatePicker.getValue().getYear();
        int month = time.getMonthNow() - this.BirthTenantDatePicker.getValue().getMonthValue();
        int day = time.getDayNow() - this.BirthTenantDatePicker.getValue().getDayOfMonth();
        if (this.MemberNameTextField.getText().isEmpty() || (!this.MemberMaleRadioButton.isSelected() && !this.MemberFemaleRadionButton.isSelected()) || this.birthMemberDatePicker.getValue() == null || this.placeMemberTextField.getText().isEmpty()
                || (CCCD.length() != 12) || (!CCCD.matches("\\d+"))
                || (year > 120) || (year < 0) || ((year == 0) && (month > 0)) || ((year == 0) && (month == 0) && (day > 0))) {
            String gender = "";
            if (this.MemberFemaleRadionButton.isSelected()) {
                gender = "Nữ";
            }
            if (this.MemberMaleRadioButton.isSelected()) {
                gender = "Nam";
            }
            if (!citizenIdentification.checkThreeNumber(CCCD, this.placeTenantTextField.getText())
                    || !citizenIdentification.checkBirthOfDateAndGender(CCCD, this.BirthTenantDatePicker.getValue().getYear(), gender)) {
                return true;
            }
            if ((!citizenIdentification.checkPhoneNum(gender)) || (!"".equals(SDT))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTenantInvalid() {
        String CCCD = this.IDCitizenTenantTextField.getText();
        String SDT = this.phoneNumTenantTextField.getText();
        LocalDateMonthY time = new LocalDateMonthY();
        int year = time.getYearNow() - this.BirthTenantDatePicker.getValue().getYear();
        int month = time.getMonthNow() - this.BirthTenantDatePicker.getValue().getMonthValue();
        int day = time.getDayNow() - this.BirthTenantDatePicker.getValue().getDayOfMonth();
        LocalDate rentDate = rentDatePicker.getValue();
        if (this.TenantIDTextField.getText().isEmpty() || this.NameTenantTextField.getText().isEmpty() || !this.MaleTenantRadioButton.isSelected() && !this.FemaleTenantRadioButton.isSelected() || this.BirthTenantDatePicker.getValue() == null || this.IDCitizenTenantTextField.getText().isEmpty() || this.phoneNumTenantTextField.getText().isEmpty() || this.placeTenantTextField.getText().isEmpty() || this.rentDatePicker.getValue() == null
                || (CCCD.length() != 12) || (!CCCD.matches("\\d+"))
                || (SDT.length() != 10) || (!SDT.matches("\\d+")) || (year < 18) || (year > 120) || ((year == 18) && (month > 0)) || ((year == 18) && (month == 0) && (day > 0)) || (rentDate.isBefore(time.getCurrentDate()))) {
            return true;
        }
        String gender = "";
        if (this.FemaleTenantRadioButton.isSelected()) {
            gender = "Nữ";
        }
        if (this.MaleTenantRadioButton.isSelected()) {
            gender = "Nam";
        }
        if (!citizenIdentification.getInstance().checkThreeNumber(CCCD, this.placeTenantTextField.getText())
                || !citizenIdentification.checkPhoneNum(SDT)
                || !citizenIdentification.checkBirthOfDateAndGender(CCCD, this.BirthTenantDatePicker.getValue().getYear(), gender)) {
            return true;
        }
        return false;
    }

    @FXML
    public void Save() {
        if (this.TenantTab.isSelected()) {
            this.addTenant();
            if (this.checkAddingCompleted) {
                this.MemberTab.setDisable(false);
            }
        } else if (this.MemberTab.isSelected()) {
            this.addMember();
        }

    }

    @FXML
    public void Back(ActionEvent event) {
        try {
            Support.root = (Parent) FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/TenantDesign_1.fxml"));
            Support.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Support.scene = new Scene(Support.root);
            Support.stage.setScene(Support.scene);
            Support.stage.setTitle(TitleForms.TitleHomePageForm);
            Support.stage.show();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    @FXML
    public void OpenRoomForm(ActionEvent event) {
        this.currentWidth = Support.stage.getWidth();
        this.currentHeight = Support.stage.getHeight();
        this.currentX = Support.stage.getX();
        this.currentY = Support.stage.getY();
        Platform.runLater(() -> {
            try {
                Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/RoomDesign.fxml"));
                Support.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Support.scene = new Scene(Support.root);
                Support.stage.setScene(Support.scene);
                Support.stage.setTitle(TitleForms.TitleRoomForm);
                Support.stage.getIcons().add(Support.icon);
                Support.stage.setWidth(this.currentWidth);
                Support.stage.setHeight(this.currentHeight);
                Support.stage.setX(this.currentX);
                Support.stage.setY(this.currentY);
                Support.stage.show();
            } catch (IOException var3) {
                var3.printStackTrace();
            }

        });
    }

    @FXML
    public void OpenTenantForm(ActionEvent event) {
        this.currentWidth = Support.stage.getWidth();
        this.currentHeight = Support.stage.getHeight();
        this.currentX = Support.stage.getX();
        this.currentY = Support.stage.getY();
        Platform.runLater(() -> {
            try {
                Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/TenantDesign_1.fxml"));
                Support.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Support.scene = new Scene(Support.root);
                Support.stage.setScene(Support.scene);
                Support.stage.setTitle(TitleForms.TitleTenantForm);
                Support.stage.getIcons().add(Support.icon);
                Support.stage.setWidth(this.currentWidth);
                Support.stage.setHeight(this.currentHeight);
                Support.stage.setX(this.currentX);
                Support.stage.setY(this.currentY);
                Support.stage.show();
            } catch (IOException var3) {
                var3.printStackTrace();
            }

        });
    }

    @FXML
    public void OpenServiceForm(ActionEvent event) {
        this.currentWidth = Support.stage.getWidth();
        this.currentHeight = Support.stage.getHeight();
        this.currentX = Support.stage.getX();
        this.currentY = Support.stage.getY();
        Platform.runLater(() -> {
            try {
                Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/ServiceDesign.fxml"));
                Support.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Support.scene = new Scene(Support.root);
                Support.stage.setScene(Support.scene);
                Support.stage.setTitle(TitleForms.TitleServiceForm);
                Support.stage.getIcons().add(Support.icon);
                Support.stage.setWidth(this.currentWidth);
                Support.stage.setHeight(this.currentHeight);
                Support.stage.setX(this.currentX);
                Support.stage.setY(this.currentY);
                Support.stage.show();
            } catch (IOException var3) {
                var3.printStackTrace();
            }

        });
    }

    @FXML
    public void OpenBillForm(ActionEvent event) {
        this.currentWidth = Support.stage.getWidth();
        this.currentHeight = Support.stage.getHeight();
        this.currentX = Support.stage.getX();
        this.currentY = Support.stage.getY();
        Platform.runLater(() -> {
            try {
                Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/BillDesign.fxml"));
                Support.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Support.scene = new Scene(Support.root);
                Support.stage.setScene(Support.scene);
                Support.stage.setTitle(TitleForms.TitleBillForm);
                Support.stage.getIcons().add(Support.icon);
                Support.stage.setWidth(this.currentWidth);
                Support.stage.setHeight(this.currentHeight);
                Support.stage.setX(this.currentX);
                Support.stage.setY(this.currentY);
                Support.stage.show();
            } catch (IOException var3) {
                var3.printStackTrace();
            }

        });
    }

    @FXML
    public void OpenStatisticsForm(ActionEvent event) {
        this.currentWidth = Support.stage.getWidth();
        this.currentHeight = Support.stage.getHeight();
        this.currentX = Support.stage.getX();
        this.currentY = Support.stage.getY();
        Platform.runLater(() -> {
            try {
                Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/StatisticsDesign.fxml"));
                Support.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Support.scene = new Scene(Support.root);
                Support.stage.setScene(Support.scene);
                Support.stage.setTitle(TitleForms.TitleStatisticsForm);
                Support.stage.getIcons().add(Support.icon);
                Support.stage.setWidth(this.currentWidth);
                Support.stage.setHeight(this.currentHeight);
                Support.stage.setX(this.currentX);
                Support.stage.setY(this.currentY);
                Support.stage.show();
            } catch (IOException var3) {
                var3.printStackTrace();
            }

        });
    }

    @FXML
    public void Logout() {
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
            } catch (IOException var6) {
                var6.printStackTrace();
            }
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

    public void setMemberToTable(ObservableList<Member> list) {
        this.order.setCellValueFactory((cellData) -> {
            return (new SimpleIntegerProperty(list.indexOf(cellData.getValue()) + 1)).asObject();
        });
        this.NameMember_col.setCellValueFactory(new PropertyValueFactory("name"));
        this.MemberSex_col.setCellValueFactory(new PropertyValueFactory("sex"));
        this.birthMember_col.setCellValueFactory(new PropertyValueFactory("birthdate"));
        this.CitizenIDMember_col.setCellValueFactory(new PropertyValueFactory("citizenID"));
        this.phoneNumMember_col.setCellValueFactory(new PropertyValueFactory("phoneNum"));
        this.placeMember_col.setCellValueFactory(new PropertyValueFactory("placeOrigin"));
        this.MemberTableView.setItems(list);
    }

    public void initialize(URL arg0, ResourceBundle arg1) {
        this.TenantButton.setStyle(Support.colorButton);
        this.accountNameLabel.setText(Support.NameAccount);
        this.TenantButton.setStyle(Support.colorButton);
        this.checkAddingCompleted = false;
        this.MemberTab.setDisable(true);
        Support.currMemberList = FXCollections.observableArrayList();
        this.getRoomIDLabel.setText(cardRoomController.idRoom);
        this.getTownIDLabel.setText(DAO_Room.getInstance().selectID(cardRoomController.idRoom, ""));

        tenantList = FXCollections.observableArrayList();
    }
}
