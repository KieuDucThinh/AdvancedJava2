package com.ltc.btl_javafx.controller;


import com.ltc.btl_javafx.application.Support;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class UpgradePackController implements Initializable {
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
    private Label namePackServiceLabel;
    @FXML
    private FontAwesomeIconView iconPack;
    private String namepack;

    public UpgradePackController() {
    }

    public void showNameAndIconPack(Label namepack, String nameicon, Color color) {
        namepack.setTextFill(color);
        this.iconPack.setGlyphName(nameicon);
        this.iconPack.setFill(color);
    }

    public void showServicePack(String namepack) {
        this.namePackServiceLabel.setText(namepack);
        if ("Gói miễn phí".equals(namepack)) {
            this.showNameAndIconPack(this.namePackServiceLabel, "PUZZLE_PIECE", Color.web("#7F7F7F"));
        } else if ("Gói cơ bản 1".equals(namepack)) {
            this.showNameAndIconPack(this.namePackServiceLabel, "CHECK", Color.web("#E28E42"));
        } else if ("Gói cơ bản 2".equals(namepack)) {
            this.showNameAndIconPack(this.namePackServiceLabel, "PLUS_SQUARE", Color.web("#7030A0"));
        } else if ("Gói cao cấp 1".equals(namepack)) {
            this.showNameAndIconPack(this.namePackServiceLabel, "STREET_VIEW", Color.web("#2E7C82"));
        } else if ("Gói cao cấp 2".equals(namepack)) {
            this.showNameAndIconPack(this.namePackServiceLabel, "STAR", Color.web("#FF7979"));
        }

    }

    @FXML
    public void OpenHomePageForm(ActionEvent event) {
        try {
            Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/HomePageDesign.fxml"));
            Support.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Support.scene = new Scene(Support.root);
            Support.stage.setScene(Support.scene);
            Support.stage.setTitle("Phần Mềm Quản Lý Chung Cư Mini - Trang chủ");
            Support.stage.getIcons().add(Support.icon);
            Support.stage.show();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    @FXML
    public void OpenRoomForm(ActionEvent event) {
        try {
            Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/RoomDesign.fxml"));
            Support.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Support.scene = new Scene(Support.root);
            Support.stage.setScene(Support.scene);
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
            Support.stage.setTitle("Phần Mềm Quản Lý Chung Cư Mini - Quản lý khách thuê phòng");
            Support.stage.getIcons().add(Support.icon);
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
            Support.stage.setTitle("Phần Mềm Quản Lý Chung Cư Mini - Quản lý dịch vụ");
            Support.stage.getIcons().add(Support.icon);
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
            Support.stage.setTitle("Phần Mềm Quản Lý Chung Cư Mini - Quản lý hóa đơn");
            Support.stage.getIcons().add(Support.icon);
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
            Support.stage.setTitle("Phần Mềm Quản Lý Chung Cư Mini - Thống kê");
            Support.stage.getIcons().add(Support.icon);
            Support.stage.show();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

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
                Image icon = new Image("/com/ltc/btl_javafx/imageIcon/icon_miniapartment.png");
                stage.getIcons().add(icon);
                stage.setTitle("Phần Mềm Quản Lý Chung Cư Mini - Đăng nhập");
                stage.show();
            } catch (IOException var7) {
                var7.printStackTrace();
            }
        }

    }

    public void initialize(URL arg0, ResourceBundle arg1) {
        this.HomeButton.setStyle(Support.colorButton);
        this.accountNameLabel.setText(Support.NameAccount);
        this.namepack = Support.rankAccount;
        this.showServicePack(this.namepack);
    }
}

