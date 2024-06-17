package com.ltc.btl_javafx.controller;



import com.ltc.btl_javafx.DAO.DAO_Service;
import com.ltc.btl_javafx.application.Support;
import com.ltc.btl_javafx.application.TitleForms;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.ltc.btl_javafx.model.HomeTown;
import com.ltc.btl_javafx.model.Service;

public class ServiceController implements Initializable {
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
    private TextField ServiceIDTextField;
    @FXML
    private TextField ServiceNameTextField;
    @FXML
    private TextField ServicePriceTextField;
    @FXML
    private ComboBox<String> chooseTownComboBox;
    @FXML
    private ComboBox<String> chooseTownComboBox1;
    @FXML
    private ComboBox<String> chooseTypeService;
    @FXML
    private TableView<Service> ServiceTableView;
    @FXML
    private TableColumn<Service, Integer> ordinalService;
    @FXML
    private TableColumn<Service, String> HomeTownID_col;
    @FXML
    private TableColumn<Service, String> NameService_col;
    @FXML
    private TableColumn<Service, String> PriceService_col;
    @FXML
    private TableColumn<Service, String> ServiceID_col;
    @FXML
    private TableColumn<Service, String> TypeService_col;
    private ObservableList<Service> listTownService;

    public ServiceController() {
    }

    public boolean checkServiceEmpty() {
        return this.chooseTownComboBox1.getValue() == null || this.ServiceIDTextField.getText().isEmpty() || this.ServiceNameTextField.getText().isEmpty() || this.chooseTypeService.getValue() == null || this.ServicePriceTextField.getText().isEmpty();
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

    public void setServiceToTable(ObservableList<Service> list) {
        this.ordinalService.setCellValueFactory((cellData) -> {
            return (new SimpleIntegerProperty(list.indexOf(cellData.getValue()) + 1)).asObject();
        });
        this.HomeTownID_col.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty(((Service)cellData.getValue()).getHome().getTownID());
        });
        this.ServiceID_col.setCellValueFactory(new PropertyValueFactory("IDService"));
        this.NameService_col.setCellValueFactory(new PropertyValueFactory("nameService"));
        this.TypeService_col.setCellValueFactory(new PropertyValueFactory("typeService"));
        this.PriceService_col.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty(this.formatPrice(((Service)cellData.getValue()).getPriceService()));
        });
        this.ServiceTableView.setItems(list);
    }

    @FXML
    public void showServiceOfTown() {
        if (this.chooseTownComboBox.getValue() == null) {
            Support.NoticeAlert(AlertType.INFORMATION, "Thông báo", (String)null, "Hãy chọn tòa nhà cần xem dịch vụ !");
        } else if ("Tất cả".equals(this.chooseTownComboBox.getValue())) {
            this.setServiceToTable(Support.serviceList);
        } else {
            String town = (String)this.chooseTownComboBox.getValue();
            String[] codeHomeTownID = town.split("-");
            this.listTownService.clear();
            this.listTownService.addAll(DAO_Service.getInstance().selectByCondition(codeHomeTownID[0], "", ""));
            this.ordinalService.setCellValueFactory((cellData) -> {
                return (new SimpleIntegerProperty(this.listTownService.indexOf(cellData.getValue()) + 1)).asObject();
            });
            this.ServiceTableView.setItems(this.listTownService);
        }

    }

    @FXML
    public void addService() {
        if (this.checkServiceEmpty()) {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", (String)null, "Vui lòng điền đầy đủ thông tin !");
        } else if (!this.isValidPrice(this.ServicePriceTextField.getText())) {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", (String)null, "Giá dịch vụ không hợp lệ !");
        } else {
            boolean duplicatedService = false;
            Service x;
            if (Support.serviceList.size() > 0) {
                Iterator var3 = Support.serviceList.iterator();

                while(var3.hasNext()) {
                    x = (Service)var3.next();
                    if (x.getIDService().equalsIgnoreCase(this.ServiceIDTextField.getText()) && x.getHome().getTownID().equals(this.chooseTownComboBox1.getValue())) {
                        duplicatedService = true;
                        break;
                    }
                }
            }

            if (!duplicatedService) {
                x = new Service(new HomeTown((String)this.chooseTownComboBox1.getValue(), (String)null), this.ServiceIDTextField.getText(), this.ServiceNameTextField.getText(), (String)this.chooseTypeService.getValue(), this.ServicePriceTextField.getText());
                Support.serviceList.add(x);
                DAO_Service.getInstance().insertData(x, (String)this.chooseTownComboBox1.getValue(), (String)null);
                this.ServiceTableView.setItems(Support.serviceList);
                Support.NoticeAlert(AlertType.INFORMATION, "THÔNG BÁO", (String)null, "Thêm thông tin thành công !");
            } else {
                Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI THÊM THÔNG TIN", "Mã dịch vụ bị trùng !\nMã dịch vụ không phân biệt chữ hoa và chữ thường\nVui lòng nhập mã dịch vụ khác !");
            }
        }

    }

    @FXML
    public void viewService() throws ParseException {
        int infoData = this.ServiceTableView.getSelectionModel().getSelectedIndex();
        if (infoData > -1) {
            this.chooseTownComboBox1.setValue((String)this.HomeTownID_col.getCellData(infoData));
            this.ServiceIDTextField.setText((String)this.ServiceID_col.getCellData(infoData));
            this.ServiceNameTextField.setText((String)this.NameService_col.getCellData(infoData));
            this.chooseTypeService.setValue((String)this.TypeService_col.getCellData(infoData));
            this.ServicePriceTextField.setText(this.parsePrice((String)this.PriceService_col.getCellData(infoData)));
        }
    }

    @FXML
    public void updateService() {
        if (this.checkServiceEmpty()) {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI CẬP NHẬT THÔNG TIN", "Vui lòng chọn thông tin cần sửa trên bảng !");
        } else if (!this.isValidPrice(this.ServicePriceTextField.getText())) {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI CẬP NHẬT THÔNG TIN", "Vui lòng điền mệnh giá tiền hợp lệ !");
        } else {
            try {
                boolean compareServiceID = false;
                if (Support.serviceList.size() > 0) {
                    Iterator var3 = Support.serviceList.iterator();

                    Service x;
                    while(var3.hasNext()) {
                        x = (Service)var3.next();
                        if (x.getIDService().equals(this.ServiceIDTextField.getText())) {
                            compareServiceID = true;
                            break;
                        }
                    }

                    if (compareServiceID) {
                        x = new Service(new HomeTown((String)this.chooseTownComboBox1.getValue(), (String)null), this.ServiceIDTextField.getText(), this.ServiceNameTextField.getText(), (String)this.chooseTypeService.getValue(), this.ServicePriceTextField.getText());
                        DAO_Service.getInstance().updateData(x, this.ServiceIDTextField.getText(), (String)this.chooseTownComboBox1.getValue());
                        Support.serviceList.clear();
                        Support.serviceList.addAll(DAO_Service.getInstance().selectALL());
                        this.setServiceToTable(Support.serviceList);
                        Support.NoticeAlert(AlertType.INFORMATION, "THÔNG BÁO", (String)null, "Sửa thông tin thành công !");
                    } else {
                        Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI SỬA THÔNG TIN", "Không thể sửa mã dịch vụ!");
                    }
                } else {
                    Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI SỬA THÔNG TIN", "Danh sách dịch vụ trống !\nVui lòng thêm dịch vụ !");
                }
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

    }

    @FXML
    public void eraseServiceInTextField() {
        this.chooseTownComboBox1.setValue((String) null);
        this.chooseTypeService.setValue((String) null);
        this.ServiceIDTextField.setText("");
        this.ServiceNameTextField.setText("");
        this.ServicePriceTextField.setText("");
    }

    @FXML
    public void deleteService() {
        Service selection = (Service)this.ServiceTableView.getSelectionModel().getSelectedItem();
        if (selection == null) {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", "LỖI XÓA THÔNG TIN", "Không thể xóa dịch vụ !\nVui lòng chọn phòng cần xóa trên bảng ");
        } else {
            DAO_Service.getInstance().deleteData(selection, selection.getHome().getTownID());
            Support.serviceList.remove(selection);
            this.ServiceTableView.setItems(Support.serviceList);
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
    public void OpenRoomForm(ActionEvent event) {
        try {
            Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/RoomDesign.fxml"));
            Support.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Support.scene = new Scene(Support.root);
            Support.stage.setScene(Support.scene);
            Support.stage.setTitle(TitleForms.TitleRoomForm);
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
        this.ServiceButton.setStyle(Support.colorButton);
        this.accountNameLabel.setText(Support.NameAccount);
        Support.serviceList = FXCollections.observableArrayList();
        Support.serviceList.addAll(DAO_Service.getInstance().selectALL());
        this.setServiceToTable(Support.serviceList);
        this.listTownService = FXCollections.observableArrayList();
        this.chooseTypeService.getItems().addAll(new String[]{"ĐIỆN", "NƯỚC", "KHÁC"});
        this.chooseTownComboBox.getItems().add("Tất cả");
        Iterator var4 = Support.homeList.iterator();

        while(var4.hasNext()) {
            HomeTown x = (HomeTown)var4.next();
            this.chooseTownComboBox.getItems().add(x.toString());
            this.chooseTownComboBox1.getItems().add(x.getTownID());
        }

    }
}

