package com.ltc.btl_javafx.controller;


import com.ltc.btl_javafx.DAO.DAO_Bill;
import com.ltc.btl_javafx.DAO.DAO_Room;
import com.ltc.btl_javafx.application.Support;
import com.ltc.btl_javafx.application.readPrice;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import com.ltc.btl_javafx.model.Bill;
import com.ltc.btl_javafx.model.HomeTown;
import com.ltc.btl_javafx.model.Room;

public class BillController implements Initializable {
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
    private GridPane billGridPane;
    @FXML
    private ChoiceBox<String> HomeTownChoiceBox;
    @FXML
    private DatePicker invoiceDatePicker;
    @FXML
    private ChoiceBox<String> roomChoiceBox;
    @FXML
    private Label NumbersToWordsLabel;
    @FXML
    private Label sumPriceLabel;
    private ObservableList<Room> listrentedRoom;
    private ObservableList<Bill> listStateBill;
    public static ObservableList<Node> lst;
    public static Stage secondStage;

    public BillController() {
    }

    public void addMenuPane(ObservableList<Bill> list) {
        try {
            this.billGridPane.getChildren().clear();
            this.billGridPane.getRowConstraints().clear();
            this.billGridPane.getColumnConstraints().clear();
            int row = 0;

            for(int q = 0; q < list.size(); ++q) {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/cardBill.fxml"));
                AnchorPane pane = (AnchorPane)load.load();
                cardBillController calculateForm = (cardBillController)load.getController();
                calculateForm.setData((Bill)list.get(q));
                this.billGridPane.add(pane, 0, row++);
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    public void sumAllBillPrice() {
        BigDecimal sum = new BigDecimal("0");
        Bill x;
        if (Support.billList.size() > 0) {
            for(Iterator var3 = Support.billList.iterator(); var3.hasNext(); sum = sum.add(new BigDecimal(x.getBillPrice()))) {
                x = (Bill)var3.next();
            }
        }

        String words = readPrice.readNumberInWords(sum.longValue());
        String var10000 = words.substring(0, 1).toUpperCase();
        words = var10000 + words.substring(1);
        this.NumbersToWordsLabel.setText(words.replace("  ", " ") + " nghìn đồng");
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        String formattedPrice = nf.format(sum);
        this.sumPriceLabel.setText(formattedPrice.replace("$", "") + " VNĐ");
    }

    public void setRoomId(ActionEvent event) {
        this.listrentedRoom.clear();
        this.roomChoiceBox.getItems().clear();
        this.listrentedRoom.addAll(DAO_Room.getInstance().selectByCondition((String)this.HomeTownChoiceBox.getValue(), "1", ""));
        Iterator var3 = this.listrentedRoom.iterator();

        while(var3.hasNext()) {
            Room x = (Room)var3.next();
            this.roomChoiceBox.getItems().add(x.getRoomID());
        }

    }

    @FXML
    public void filter() {
        if (this.listStateBill.size() > 0) {
            this.listStateBill.clear();
        }

        if (this.HomeTownChoiceBox.getValue() != null && this.roomChoiceBox.getValue() != null && this.invoiceDatePicker.getValue() != null) {
            this.listStateBill.addAll(DAO_Bill.getInstance().selectByCondition((String)this.HomeTownChoiceBox.getValue(), (String)this.roomChoiceBox.getValue(), String.valueOf(this.invoiceDatePicker.getValue())));
        } else if (this.HomeTownChoiceBox.getValue() != null && this.roomChoiceBox.getValue() != null) {
            this.listStateBill.addAll(DAO_Bill.getInstance().selectByCondition((String)this.HomeTownChoiceBox.getValue(), (String)this.roomChoiceBox.getValue(), (String)null));
        } else if (this.HomeTownChoiceBox.getValue() != null && this.invoiceDatePicker.getValue() != null) {
            this.listStateBill.addAll(DAO_Bill.getInstance().selectByCondition((String)this.HomeTownChoiceBox.getValue(), (String)null, String.valueOf(this.invoiceDatePicker.getValue())));
        } else if (this.HomeTownChoiceBox.getValue() != null) {
            this.listStateBill.addAll(DAO_Bill.getInstance().selectByCondition((String)this.HomeTownChoiceBox.getValue(), (String)null, (String)null));
        } else if (this.invoiceDatePicker.getValue() != null) {
            this.listStateBill.addAll(DAO_Bill.getInstance().selectByCondition((String)null, (String)null, String.valueOf(this.invoiceDatePicker.getValue())));
        }

        Platform.runLater(() -> {
            this.addMenuPane(this.listStateBill);
        });
    }

    @FXML
    public void refreshBill() {
        this.addMenuPane(Support.billList);
    }

    @FXML
    public void calculate(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/calculateBillDesign.fxml"));
            Parent root = (Parent)loader.load();
            calculateBillController ctrl = (calculateBillController)loader.getController();
            secondStage.setScene(new Scene(root));
            secondStage.setResizable(false);
            secondStage.setTitle("Tính tiền phòng");
            secondStage.showAndWait();
            ctrl.addPane(this.billGridPane);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

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
    public void OpenStatisticsForm(ActionEvent event) {
        try {
            Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/StatisticsDesign.fxml"));
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
        this.accountNameLabel.setText(Support.NameAccount);
        this.BillButton.setStyle(Support.colorButton);
        secondStage = new Stage();
        secondStage.initStyle(StageStyle.UTILITY);
        this.listrentedRoom = FXCollections.observableArrayList();
        this.listStateBill = FXCollections.observableArrayList();
        Support.billList = FXCollections.observableArrayList();
        Support.billList.addAll(DAO_Bill.getInstance().selectALL());
        this.sumAllBillPrice();
        this.addMenuPane(Support.billList);
        lst = this.billGridPane.getChildren();
        Iterator var4 = Support.homeList.iterator();

        while(var4.hasNext()) {
            HomeTown x = (HomeTown)var4.next();
            this.HomeTownChoiceBox.getItems().addAll(new String[]{x.getTownID()});
        }

        this.HomeTownChoiceBox.setOnAction(this::setRoomId);
        Timeline checkBill = new Timeline(new KeyFrame[]{new KeyFrame(Duration.seconds(0.1), (event) -> {
            if (calculateBillController.checkaddBill) {
                this.sumAllBillPrice();
            } else if (cardBillController.checkdeleteBill) {
                this.sumAllBillPrice();
            }

        }, new KeyValue[0])});
        checkBill.setCycleCount(-1);
        checkBill.play();
    }
}

