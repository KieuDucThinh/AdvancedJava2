package com.ltc.btl_javafx.controller;


import com.ltc.btl_javafx.DAO.DAO_HomeTown;
import com.ltc.btl_javafx.DAO.DAO_Login;
import com.ltc.btl_javafx.DAO.DAO_Room;
import com.ltc.btl_javafx.DAO.DAO_Service;
import com.ltc.btl_javafx.DAO.TenantFunctionImpl;
import com.ltc.btl_javafx.application.LocalDateMonthY;
//import com.ltc.btl_javafx.DAO.DAO_Tenant;
import com.ltc.btl_javafx.application.Support;
import com.ltc.btl_javafx.application.TitleForms;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.ltc.btl_javafx.model.AccountLogin;

public class HomePageController implements Initializable {
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
    private Label numberTownLabel;
    @FXML
    private Label numberTenantLabel;
    @FXML
    private Label numberRoomLabel;
    @FXML
    private Label numberServiceLabel;
    @FXML
    private PieChart RoomPieChart;
    @FXML
    private Label emptyPercentLabel;
    @FXML
    private Label rentedPercentLabel;
    @FXML
    private Label namePackServiceLabel;
    @FXML
    private FontAwesomeIconView iconPack;
    @FXML
    private AnchorPane PackAnchorPane;
    @FXML
    private AnchorPane thanksForm;
    @FXML
    private Text nameAccountText;
    @FXML
    private Button backButton;
    private int NumEmptyRoom;
    private int NumRentedRoom;
    private String namepack;
    private Timeline checkDatabaseTimeline;
    private Timeline checkLabelTimeline;
    private double currentWidth;
    private double currentHeight;
    private double currentX;
    private double currentY;

    public HomePageController() {
    }

    public void showNameAndIconPack(Label namepack, String nameicon, Color color) {
        namepack.setTextFill(color);
        this.iconPack.setGlyphName(nameicon);
        this.iconPack.setFill(color);
    }

    public void showServicePack(String name) {
        if (this.checkLabelTimeline.getStatus() == Status.RUNNING) {
            this.checkLabelTimeline.stop();
        }

        this.namePackServiceLabel.setText(name);
        if ("Gói miễn phí".equals(name)) {
            this.showNameAndIconPack(this.namePackServiceLabel, "PUZZLE_PIECE", Color.web("#7F7F7F"));
        } else if ("Gói cơ bản 1".equals(name)) {
            this.showNameAndIconPack(this.namePackServiceLabel, "CHECK", Color.web("#E28E42"));
        } else if ("Gói cơ bản 2".equals(name)) {
            this.showNameAndIconPack(this.namePackServiceLabel, "PLUS_SQUARE", Color.web("#7030A0"));
        } else if ("Gói cao cấp 1".equals(name)) {
            this.showNameAndIconPack(this.namePackServiceLabel, "STREET_VIEW", Color.web("#2E7C82"));
        } else if ("Gói cao cấp 2".equals(name)) {
            this.showNameAndIconPack(this.namePackServiceLabel, "STAR", Color.web("#FF7979"));
        } else if ("Gói kim cương".equals(name)) {
            this.showNameAndIconPack(this.namePackServiceLabel, "DIAMOND", Color.web("#2E74B5"));
            this.namePackServiceLabel.setStyle("-fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );-fx-foreground-color: linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, lime 25%, yellow 50%, orange 75%, red 100%);");
            this.checkLabelTimeline.getKeyFrames().addAll(new KeyFrame[]{new KeyFrame(Duration.ZERO, new KeyValue[]{new KeyValue(this.namePackServiceLabel.opacityProperty(), 1.0)}), new KeyFrame(Duration.seconds(0.5), new KeyValue[]{new KeyValue(this.namePackServiceLabel.opacityProperty(), 0.0)}), new KeyFrame(Duration.seconds(1.0), new KeyValue[]{new KeyValue(this.namePackServiceLabel.opacityProperty(), 1.0)})});
            this.checkLabelTimeline.setCycleCount(-1);
            this.checkLabelTimeline.play();
        }

    }

    public void createRoomPieChart(int NumEmptyRoom, int NumRentedRoom) {
        ObservableList<PieChart.Data> dataPieChart = FXCollections.observableArrayList();
        PieChart.Data emptyRoomData = new PieChart.Data("Phòng trống", (double)NumEmptyRoom);
        PieChart.Data rentedRoomData = new PieChart.Data("Phòng đã thuê", (double)NumRentedRoom);
        dataPieChart.add(emptyRoomData);
        dataPieChart.add(rentedRoomData);
        this.RoomPieChart.setData(dataPieChart);
        if (this.RoomPieChart.lookup(".chart-pie") != null) {
            Platform.runLater(() -> {
                emptyRoomData.getNode().setStyle("-fx-pie-color: #169e65;");
                rentedRoomData.getNode().setStyle("-fx-pie-color: #056960;");
            });
        }

    }

    public void calculatePercent(int NumEmptyRoom, int NumRentedRoom) {
        double PercentEmptyRoom = (double)NumEmptyRoom / (double)Support.roomList.size() * 100.0;
        double PercentRentedRoom = (double)NumRentedRoom / (double)Support.roomList.size() * 100.0;
        DecimalFormat df = new DecimalFormat("#.0");
        Label var10000;
        String var10001;
        if (PercentEmptyRoom == 0.0) {
            this.emptyPercentLabel.setText("0%");
        } else {
            var10000 = this.emptyPercentLabel;
            var10001 = df.format(PercentEmptyRoom);
            var10000.setText(var10001 + "%");
        }

        if (PercentRentedRoom == 0.0) {
            this.rentedPercentLabel.setText("0%");
        } else {
            var10000 = this.rentedPercentLabel;
            var10001 = df.format(PercentRentedRoom);
            var10000.setText(var10001 + "%");
        }

    }

    public void getInformation() {
        LocalDateMonthY time = new LocalDateMonthY();
        this.numberTownLabel.setText(String.valueOf(DAO_HomeTown.getInstance().selectCount("")));
        this.numberTenantLabel.setText(String.valueOf(TenantFunctionImpl.getInstance().selectCount(time.getYearNow(), time.getMonthNow())));
        this.numberRoomLabel.setText(String.valueOf(DAO_Room.getInstance().selectCount((String)null)));
        this.numberServiceLabel.setText(String.valueOf(DAO_Service.getInstance().selectCount("")));
    }

    public void checkForChangesInDatabase() {
        AccountLogin x = DAO_Login.getInstance().selectObject(Support.IDAccount, "");
        this.namepack = x.checkRank(x.getRank());
        if (!this.namepack.equals(Support.rankAccount)) {
            Support.rankAccount = this.namepack;
            Support.pointRank = x.getRank();
            this.checkDatabaseTimeline.stop();
            Platform.runLater(() -> {
                this.showServicePack(this.namepack);
                Support.NoticeAlert(AlertType.INFORMATION, "CHÚC MỪNG", "Nâng cấp gói thành công", "Gói tài khoản của bạn hiện tại: " + this.namepack);
            });
        }

    }

    @FXML
    public void showPack() {
        this.thanksForm.setVisible(false);
        this.PackAnchorPane.setVisible(true);
        this.backButton.setVisible(true);
    }

    @FXML
    public void Back() {
        this.thanksForm.setVisible(true);
        this.PackAnchorPane.setVisible(false);
        this.backButton.setVisible(false);
    }

    @FXML
    public void upgradePack(ActionEvent event) {
        if ("Gói kim cương".equals(this.namepack)) {
            Support.NoticeAlert(AlertType.INFORMATION, "THÔNG BÁO", (String)null, "Gói hiện tại của bạn đã là cao nhất !\nXin cảm ơn đã sử dụng dịch vụ của chúng tôi !");
        } else {
            this.currentWidth = Support.stage.getWidth();
            this.currentHeight = Support.stage.getHeight();
            this.currentX = Support.stage.getX();
            this.currentY = Support.stage.getY();
            Platform.runLater(() -> {
                try {
                    Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/upgradePackDesign.fxml"));
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

    public void initialize(URL arg0, ResourceBundle arg1) {
        this.HomeButton.setStyle(Support.colorButton);
        this.accountNameLabel.setText(Support.NameAccount);
        this.nameAccountText.setText(Support.NameAccount);
        Support.homeList = FXCollections.observableArrayList();
        Support.homeList.addAll(DAO_HomeTown.getInstance().selectALL());
        Support.roomList = FXCollections.observableArrayList();
        Support.roomList.addAll(DAO_Room.getInstance().selectALL());
        this.getInformation();
        this.NumEmptyRoom = DAO_Room.getInstance().selectCount("0");
        this.NumRentedRoom = DAO_Room.getInstance().selectCount("1");
        this.createRoomPieChart(this.NumEmptyRoom, this.NumRentedRoom);
        this.calculatePercent(this.NumEmptyRoom, this.NumRentedRoom);
        this.checkLabelTimeline = new Timeline();
        this.namepack = Support.rankAccount;
        this.showServicePack(this.namepack);
        this.checkDatabaseTimeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.seconds(10.0), (event) -> {
            this.checkForChangesInDatabase();
        }, new KeyValue[0])});
        this.checkDatabaseTimeline.setCycleCount(-1);
        this.checkDatabaseTimeline.play();
    }
}

