package com.ltc.btl_javafx.controller;



import com.ltc.btl_javafx.DAO.DAO_Bill;
import com.ltc.btl_javafx.application.Support;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StatisticsController implements Initializable {
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
    private LineChart<String, Number> monthlyRevenueLineChart;
    @FXML
    private BarChart<String, Number> yearlyRevenueBarChart;
    private XYChart.Series<String, Number> monthChart;
    private XYChart.Series<String, Number> yearChart;

    public StatisticsController() {
    }

    public int getYear() {
        LocalDate year = LocalDate.now();
        return year.getYear();
    }

    public void setTurnoverMonthToLineChart() {
        this.monthChart = new XYChart.Series();
        XYChart.Data<String, Number> jan = new XYChart.Data("Tháng 1", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(1, this.getYear())));
        XYChart.Data<String, Number> feb = new XYChart.Data("Tháng 2", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(2, this.getYear())));
        XYChart.Data<String, Number> mar = new XYChart.Data("Tháng 3", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(3, this.getYear())));
        XYChart.Data<String, Number> apr = new XYChart.Data("Tháng 4", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(4, this.getYear())));
        XYChart.Data<String, Number> may = new XYChart.Data("Tháng 5", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(5, this.getYear())));
        XYChart.Data<String, Number> jun = new XYChart.Data("Tháng 6", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(6, this.getYear())));
        XYChart.Data<String, Number> jul = new XYChart.Data("Tháng 7", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(7, this.getYear())));
        XYChart.Data<String, Number> aug = new XYChart.Data("Tháng 8", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(8, this.getYear())));
        XYChart.Data<String, Number> sep = new XYChart.Data("Tháng 9", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(8, this.getYear())));
        XYChart.Data<String, Number> oct = new XYChart.Data("Tháng 10", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(10, this.getYear())));
        XYChart.Data<String, Number> nov = new XYChart.Data("Tháng 11", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(11, this.getYear())));
        XYChart.Data<String, Number> dec = new XYChart.Data("Tháng 12", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(12, this.getYear())));
        this.monthChart.getData().addAll(new XYChart.Data[]{jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec});
        this.monthlyRevenueLineChart.getData().add(this.monthChart);
        if (this.monthlyRevenueLineChart.lookup(".chart-series-line") != null) {
            Platform.runLater(() -> {
                this.monthlyRevenueLineChart.lookup(".chart-series-line").setStyle("-fx-stroke: #056960;");
            });
        }

    }

    public void setTurnoverYearToBarChart() {
        this.yearChart = new XYChart.Series();
        this.yearChart.getData().add(new XYChart.Data("2024", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(-1, this.getYear()))));
        this.yearChart.getData().add(new XYChart.Data("2025", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(-1, this.getYear() + 1))));
        this.yearChart.getData().add(new XYChart.Data("2026", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(-1, this.getYear() + 2))));
        this.yearChart.getData().add(new XYChart.Data("2027", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(-1, this.getYear() + 3))));
        this.yearChart.getData().add(new XYChart.Data("2028", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(-1, this.getYear() + 4))));
        this.yearChart.getData().add(new XYChart.Data("2029", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(-1, this.getYear() + 5))));
        this.yearChart.getData().add(new XYChart.Data("2030", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(-1, this.getYear() + 6))));
        this.yearChart.getData().add(new XYChart.Data("2031", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(-1, this.getYear() + 7))));
        this.yearChart.getData().add(new XYChart.Data("2032", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(-1, this.getYear() + 8))));
        this.yearChart.getData().add(new XYChart.Data("2033", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(-1, this.getYear() + 9))));
        this.yearChart.getData().add(new XYChart.Data("2034", Double.parseDouble(DAO_Bill.getInstance().selectTurnover(-1, this.getYear() + 10))));
        this.yearlyRevenueBarChart.getData().addAll(new XYChart.Series[]{this.yearChart});
    }

    @FXML
    public void OpenHomePageForm(ActionEvent event) {
        try {
            Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/HomePageDesign.fxml"));
            Support.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Support.scene = new Scene(Support.root);
            Support.stage.setScene(Support.scene);
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
                Image icon = new Image("/com/ltc/btl_javafx/imageIcon/icon_miniapartment.png");
                stage.getIcons().add(icon);
                stage.setTitle("Phần Mềm Quản Lý Chung Cư Mini - Đăng nhập");
                stage.show();
            } catch (IOException var8) {
                var8.printStackTrace();
            }
        }

    }

    public void initialize(URL arg0, ResourceBundle arg1) {
        this.setTurnoverYearToBarChart();
        this.setTurnoverMonthToLineChart();
        this.StatisticsButton.setStyle(Support.colorButton);
        this.accountNameLabel.setText(Support.NameAccount);
    }
}

