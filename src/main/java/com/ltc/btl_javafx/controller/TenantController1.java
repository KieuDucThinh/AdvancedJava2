package com.ltc.btl_javafx.controller;

import com.ltc.btl_javafx.DAO.MemberFunctionImpl;
import com.ltc.btl_javafx.DAO.TenantFunctionImpl;
import com.ltc.btl_javafx.application.LocalDateMonthY;
import com.ltc.btl_javafx.application.Support;
import com.ltc.btl_javafx.application.TitleForms;
import com.ltc.btl_javafx.database.ConnectionPool;
import com.ltc.btl_javafx.database.ConnectionPoolImpl;
import com.ltc.btl_javafx.model.Member;
import com.ltc.btl_javafx.model.Tenant;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.application.Platform;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

public class TenantController1 implements Initializable {

    @FXML
    private Button BillButton;

    @FXML
    private AnchorPane GuestStatisticsForm;

    @FXML
    private Button HomeButton;

    @FXML
    private Button LogoutButton;

    @FXML
    private AnchorPane MainRoomForm;

    @FXML
    private Button RoomButton;

    @FXML
    private Button ServiceButton;

    @FXML
    private Button StatisticsButton;

    @FXML
    private Button TenantButton;

    @FXML
    private Label accountNameLabel;

    @FXML
    private ComboBox<String> chooseStateComboBox;

    @FXML
    private ComboBox<String> chooseTownComboBox;

    @FXML
    private TableColumn<Tenant, String> col_CCCD;

    @FXML
    private TableColumn<Tenant, String> col_gioiTinh;

    @FXML
    private TableColumn<Tenant, String> col_maK;

    @FXML
    private TableColumn<Tenant, String> col_maP;

    @FXML
    private TableColumn<Tenant, String> col_maT;

    @FXML
    private TableColumn<Tenant, String> col_ngaySinh;

    @FXML
    private TableColumn<Tenant, LocalDate> col_ngayThue;

    @FXML
    private TableColumn<Tenant, String> col_que;

    @FXML
    private TableColumn<Tenant, String> col_sdt;

    @FXML
    private TableColumn<Tenant, Integer> col_stt;

    @FXML
    private TableColumn<Tenant, String> col_tenK;

    @FXML
    private GridPane menuRoomGridPane;

    @FXML
    private TextField searchRoomTextField;

    @FXML
    private Button showTenantButton;

    @FXML
    private TableView<Tenant> tbl_listTenant;

    @FXML
    private TextField keyListTenant;

    @FXML
    private TextField keyListMember;

    @FXML
    private TableView<Member> tbl_listMember;

    @FXML
    private TableColumn<Member, String> col_CCCD2;

    @FXML
    private TableColumn<Member, String> col_gioiTinh2;

    @FXML
    private TableColumn<Member, String> col_maTV2;

    @FXML
    private TableColumn<Member, String> col_maP2;

    @FXML
    private TableColumn<Member, String> col_maT2;

    @FXML
    private TableColumn<Member, String> col_ngaySinh2;

    @FXML
    private TableColumn<Member, String> col_que2;

    @FXML
    private TableColumn<Member, String> col_SDT2;

    @FXML
    private TableColumn<Member, Integer> col_stt2;

    @FXML
    private TableColumn<Member, String> col_tenTV2;

    @FXML
    private LineChart<String, Integer> newCustomerChart;

    @FXML
    private BarChart<String, Integer> totalCustomerChart;

    private ObservableList<Tenant> listRoomState;
    private ObservableList<Tenant> listTenant;
    private ObservableList<Member> listMember;

    private Connection con;
    private ConnectionPoolImpl cp = new ConnectionPoolImpl();
    private PreparedStatement pre;
    private ResultSet results;
    private int year;
    private int month;
    private double currentWidth;
    private double currentHeight;
    private double currentX;
    private double currentY;

    public TenantController1() {
    }

    public void setRoomMenu(ObservableList<Tenant> list) {
        //Don dep
        this.menuRoomGridPane.getChildren().clear();
        this.menuRoomGridPane.getRowConstraints().clear();
        this.menuRoomGridPane.getColumnConstraints().clear();

        //Khoi tao lai
        int column = 0;
        int row = 0;

        //Chen Room vao Grid
        for (int i = 0; i < list.size(); ++i) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/cardRoom.fxml"));
                AnchorPane pane = (AnchorPane) loader.load();
                cardRoomController cardR = (cardRoomController) loader.getController();
                cardR.setRoom((Tenant) list.get(i));
                if (column == 4) {
                    column = 0;
                    ++row;
                }

                this.menuRoomGridPane.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(11.0));
            } catch (Exception var8) {
                var8.printStackTrace();
            }
        }

    }

    /*
    @FXML
    public void filterMenuRoom() {
        String town;
        String[] codeHomeTownID;
        if (this.chooseStateComboBox.getValue() == null && this.chooseTownComboBox.getValue() != null) {
            town = (String)this.chooseTownComboBox.getValue();
            codeHomeTownID = town.split("-");
            this.listRoomState.clear();
            this.listRoomState.addAll(DAO_Room.getInstance().selectByCondition(codeHomeTownID[0], (String)null, (String)null));
            this.setRoomMenu(this.listRoomState);
        } else if (this.chooseStateComboBox.getValue() == null && this.chooseTownComboBox.getValue() == null) {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", (String)null, "Vui lòng chọn 1 trong 2 mục !");
        } else if (this.chooseStateComboBox.getValue() != null && this.chooseTownComboBox.getValue() == null) {
            this.listRoomState.clear();
            if ("Trống".equals(this.chooseStateComboBox.getValue())) {
                this.listRoomState.addAll(DAO_Room.getInstance().selectByCondition((String)null, "0", (String)null));
            } else if ("Đã thuê".equals(this.chooseStateComboBox.getValue())) {
                this.listRoomState.addAll(DAO_Room.getInstance().selectByCondition((String)null, "1", (String)null));
            }

            this.setRoomMenu(this.listRoomState);
        } else if (this.chooseStateComboBox.getValue() != null && this.chooseTownComboBox.getValue() != null) {
            town = (String)this.chooseTownComboBox.getValue();
            codeHomeTownID = town.split("-");
            this.listRoomState.clear();
            if ("Trống".equals(this.chooseStateComboBox.getValue())) {
                this.listRoomState.addAll(DAO_Room.getInstance().selectByCondition(codeHomeTownID[0], "0", (String)null));
            } else if ("Đã thuê".equals(this.chooseStateComboBox.getValue())) {
                this.listRoomState.addAll(DAO_Room.getInstance().selectByCondition(codeHomeTownID[0], "1", (String)null));
            }

            this.setRoomMenu(this.listRoomState);
        }

    }
     */
    @FXML
    public void filterMenuRoom() {
//        String town;
//        String[] codeHomeTownID;
        if (this.chooseTownComboBox.getValue() == null) {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", (String) null, "Vui lòng chọn toà nhà !");
        } else if (this.chooseTownComboBox.getValue() != null) {
            this.listRoomState.clear();
            this.listRoomState.addAll(TenantFunctionImpl.getInstance().selectTenantByMaT(this.chooseTownComboBox.getValue()));
            this.setRoomMenu(this.listRoomState);
            this.reset((byte) 1);
        }
    }

    /*
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
        
                    this.setRoomMenu(this.listRoomState);
                }
            }
     */
    @FXML
    public void refreshRoom() {
        this.listRoomState.clear();
        this.reset((byte) 0);
        //this.chooseStateComboBox.setValue((String) null);
        this.listRoomState.clear();
        this.listRoomState.addAll(TenantFunctionImpl.getInstance().selectAllTenant2());
        this.setRoomMenu(this.listRoomState);
    }

    //    @FXML
    //    public void showViewTenantForm() {
    //        this.MainRoomForm.setVisible(false);
    //        this.viewTenantForm.setVisible(true);
    //    }
    //
    //    @FXML
    //    public void backMainRoomForm() {
    //        this.MainRoomForm.setVisible(true);
    //        this.viewTenantForm.setVisible(false);
    //    }
    //    @FXML
    //    public void searchListTenant() {
    //        if (this.searchListTenant.size() > 0) {
    //            this.searchListTenant.clear();
    //        }
    //
    //        if (this.chooseTownComboBox1.getValue() == null) {
    //            this.searchListTenant.addAll(DAO_RoomAndTenant.getInstance().searching(this.searchTenantTextField.getText(), "", ""));
    //        } else {
    //            String town = (String)this.chooseTownComboBox1.getValue();
    //            String[] codeHomeTownID = town.split("-");
    //            this.searchListTenant.addAll(DAO_RoomAndTenant.getInstance().searching(this.searchTenantTextField.getText(), codeHomeTownID[0], ""));
    //        }
    //
    //        this.setInforTenantTable(this.searchListTenant);
    //    }
    //    @FXML
    //    public void refreshListTenant() {
    //        this.searchListTenant.clear();
    //        this.chooseTownComboBox1.setValue((String) null);
    //        this.setInforTenantTable(Support.RoomAndTenantList);
    //    }
    //    @FXML
    //    public void showListTenantRoom() {
    //        if (this.chooseTownComboBox1.getValue() == null) {
    //            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", (String)null, "Vui lòng chọn tòa nhà !");
    //        } else {
    //            String town = (String)this.chooseTownComboBox1.getValue();
    //            String[] codeHomeTownID = town.split("-");
    //            Support.RoomAndTenantList.clear();
    //            Support.RoomAndTenantList.addAll(DAO_RoomAndTenant.getInstance().selectByCondition(codeHomeTownID[0], "", ""));
    //            this.setInforTenantTable(Support.RoomAndTenantList);
    //        }
    //
    //    }
    public void saveExcelFile(ObservableList<Tenant> listTenant, File file) {
        //Khai bao
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();

        //Set style
        font.setBold(true);
        headerStyle.setFont(font);
        headerStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //Dong tieu de
        Row headerRow = sheet.createRow(0);
        String[] headerTitle = {"STT", "Mã khách", "Mã phòng", "Mã tòa nhà", "Tên khách", "Giới tính", "Ngày sinh", "CCCD", "Số điện thoại", "Quê quán", "Ngày thuê"};
        for (int i = 0; i <= 10; ++i) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headerTitle[i]);
            cell.setCellStyle(headerStyle);
        }

        //Cac dong du lieu
        Row row;
        for (int i = 0; i < listTenant.size(); i++) {
            Tenant tenant = (Tenant) listTenant.get(i);
            row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue((double) (i + 1));
            row.createCell(1).setCellValue(tenant.getTenantID());
            row.createCell(2).setCellValue(tenant.getRoom().getRoomID());
            row.createCell(3).setCellValue(tenant.getHomeTown().getTownID());
            row.createCell(4).setCellValue(tenant.getName());
            row.createCell(5).setCellValue(tenant.getSex());
            row.createCell(6).setCellValue(tenant.getBirthdate());
            row.createCell(7).setCellValue(tenant.getCitizenID());
            row.createCell(8).setCellValue(tenant.getPhoneNum());
            row.createCell(9).setCellValue(tenant.getPlaceOrigin());
            row.createCell(10).setCellValue(tenant.getRentDate());
        }

        //try-with-resources
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void convertExcel() {
        //Cua so chon file
        FileChooser chooseFile = new FileChooser();

        //Gio hien tai
        LocalDate currentDate = LocalDate.now();

        //Dinh dang
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);

        //Dat ten file
        if (this.keyListTenant.getText() == null) {
            chooseFile.setInitialFileName("Danh sách chủ thuê phòng " + formattedDate);
        } else if (this.keyListTenant.getText() != null) {
            chooseFile.setInitialFileName("Danh sách chủ thuê phòng toà theo " + this.keyListTenant.getText() + "_" + formattedDate);
        }

        //Phan mo rong cua file
        FileChooser.ExtensionFilter excelFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", new String[]{"*.xlsx"});
        chooseFile.getExtensionFilters().add(excelFilter);

        //Cua so hien thi
        Stage showFileChooser = new Stage();
        File file = chooseFile.showSaveDialog(showFileChooser);
        if (file != null) {
            this.saveExcelFile(this.listTenant, file);
        }

    }

    public void setInforTenantTable(ObservableList<Tenant> list) {
        this.col_stt.setCellValueFactory((cellData) -> {
            return (new SimpleIntegerProperty(list.indexOf(cellData.getValue()) + 1)).asObject();
        });

        this.col_maK.setCellValueFactory(new PropertyValueFactory<>("tenantID"));
        this.col_maT.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty(((Tenant) cellData.getValue()).getHomeTown().getTownID());
        });
        this.col_maP.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty(((Tenant) cellData.getValue()).getRoom().getRoomID());
        });
        this.col_tenK.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.col_gioiTinh.setCellValueFactory(new PropertyValueFactory<>("sex"));
        this.col_ngaySinh.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        this.col_CCCD.setCellValueFactory(new PropertyValueFactory<>("citizenID"));
        this.col_sdt.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        this.col_que.setCellValueFactory(new PropertyValueFactory<>("placeOrigin"));
        this.col_ngayThue.setCellValueFactory(new PropertyValueFactory<>("rentDate"));

        this.tbl_listTenant.setItems(list);
    }

    public void searchListTenant() {
        if (this.listTenant.size() > 0) {
            this.listTenant.clear();
        }

        if (this.keyListTenant.getText().equalsIgnoreCase("")) {
            Support.NoticeAlert(AlertType.ERROR, "", "", "Nhập thông tin muốn tìm kiếm");
        } else {
            this.listTenant.addAll(TenantFunctionImpl.getInstance().selectTenantByCondition(this.keyListTenant.getText()));
            this.setInforTenantTable(this.listTenant);
        }

    }

    public void refreshListTenant() {
        this.listTenant.clear();
        this.keyListTenant.setText("");
        //this.chooseStateComboBox.setValue((String) null);

        this.listTenant.addAll(TenantFunctionImpl.getInstance().selectAllTenant());
        this.setInforTenantTable(listTenant);
    }

    //MemberTable
    public void setInforMemberTable(ObservableList<Member> list) {
        this.col_stt2.setCellValueFactory((cellData) -> {
            return (new SimpleIntegerProperty(list.indexOf(cellData.getValue()) + 1)).asObject();
        });

        this.col_maTV2.setCellValueFactory(new PropertyValueFactory<>("memberID"));
        this.col_maT2.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty(((Member) cellData.getValue()).getTenant().getHomeTown().getTownID());
        });
        this.col_maP2.setCellValueFactory((cellData) -> {
            return new SimpleStringProperty(((Member) cellData.getValue()).getTenant().getRoom().getRoomID());
        });
        this.col_tenTV2.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.col_gioiTinh2.setCellValueFactory(new PropertyValueFactory<>("sex"));
        this.col_ngaySinh2.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        this.col_CCCD2.setCellValueFactory(new PropertyValueFactory<>("citizenID"));
        this.col_SDT2.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        this.col_que2.setCellValueFactory(new PropertyValueFactory<>("placeOrigin"));

        this.tbl_listMember.setItems(list);
    }

    public void searchListMember() {
        if (this.listMember.size() > 0) {
            this.listMember.clear();
        }

        if (this.keyListMember.getText().equalsIgnoreCase("")) {
            Support.NoticeAlert(AlertType.ERROR, "", "", "Nhập thông tin muốn tìm kiếm");
        } else {
            this.listMember.addAll(MemberFunctionImpl.getInstance().selectMemberByCondition(this.keyListMember.getText()));
        }

        this.setInforMemberTable(this.listMember);
    }

    public void refreshListMember() {
        this.listMember.clear();
        this.keyListMember.setText("");
        //this.chooseStateComboBox.setValue((String) null);

        this.listMember.addAll(MemberFunctionImpl.getInstance().selectAllMember());
        this.setInforMemberTable(listMember);
    }

    /*
    @FXML
    public void OpenHomePageForm(ActionEvent event) {
        try {
            Support.root = (Parent) FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/HomePageDesign.fxml"));
            Support.stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Support.scene = new Scene(Support.root);
            Support.stage.setScene(Support.scene);
            Support.stage.setTitle(TitleForms.TitleHomePageForm);
            Support.stage.show();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }
     */
    @FXML
    public void displayGuestStatistics() {
        MainRoomForm.setVisible(false);
        GuestStatisticsForm.setVisible(true);
    }

    @FXML
    public void displayMainForm() {
        MainRoomForm.setVisible(true);
        GuestStatisticsForm.setVisible(false);
        this.refreshRoom();
        this.reset((byte) 0);
        this.refreshListTenant();
    }

    @FXML
    public void searchRoom() {
        if (this.searchRoomTextField.getText() == null) {
            Support.NoticeAlert(AlertType.ERROR, "THÔNG BÁO", (String) null, "Hãy nhập mã phòng!");
        } else if (this.searchRoomTextField.getText() != null) {
            this.listRoomState.clear();
            this.listRoomState.addAll(TenantFunctionImpl.getInstance().selectTenantByMaP(this.searchRoomTextField.getText()));
            this.setRoomMenu(this.listRoomState);
            this.reset((byte) 3);

        }
    }

    public void reset(byte check) {
        if (check == 0) {
            this.searchRoomTextField.setText("");
            this.chooseTownComboBox.setValue((String) null);
        } else if (check == 1) {
            this.searchRoomTextField.setText("");
        } else if (check == 3) {
            this.chooseTownComboBox.setValue((String) null);
        }
    }

    public void newCustomerChart() {

        try {
            newCustomerChart.getData().clear();

            //String sql = "SELECT DATE_FORMAT(khachthue.NgayThue, '%m-%Y') AS ThangNam, COUNT(*)+1 AS \"ThanhVienMoi\" FROM khachthue INNER JOIN thanhvien ON khachthue.MaK = thanhvien.MaK GROUP BY DATE_FORMAT(khachthue.NgayThue, '%m-%Y') LIMIT 10";
//            String sql = "SELECT COUNT(DISTINCT thanhvien.MaTV)+COUNT(DISTINCT khachthue.MaK) AS \"ThanhVienMoi\" FROM khachthue INNER JOIN thanhvien ON khachthue.MaK = thanhvien.MaK WHERE (YEAR(khachthue.NgayThue) = ? AND MONTH(khachthue.NgayThue) = ?)";
            this.con = this.cp.getConnection("newCustomerChart");

            LocalDateMonthY time = new LocalDateMonthY();

            XYChart.Series<String, Integer> chart0 = new XYChart.Series<>();
            String dateString;
            for (int i = 9; i >= 0; i--) {
//                this.pre = this.con.prepareStatement(sql);
                if (time.getMonthNow() < time.previousMonth(i)) {
//                    pre.setInt(1, time.previousYear(1));
                    year = time.previousYear(1);
                    dateString = "-" + time.previousYear(1);
                } else {
//                    pre.setInt(1, time.getYearNow());
                    year = time.getYearNow();
                    dateString = "-" + time.getYearNow();
                }
                month = time.previousMonth(i);
//                pre.setInt(2, time.previousMonth(i));
                dateString = time.previousMonth(i) + dateString;
//                this.results = pre.executeQuery();
//
//                while (results.next()) {
                chart0.getData().add(new XYChart.Data<>(dateString, TenantFunctionImpl.getInstance().selectCount2(year, month)));
//                }

            }

            newCustomerChart.getData().add(chart0);

        } catch (SQLException ex) {
            Logger.getLogger(TenantController1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void totalCustomerChart() {

//        try {
            totalCustomerChart.getData().clear();

//            String sql = "SELECT COUNT(DISTINCT thanhvien.MaTV)+COUNT(DISTINCT khachthue.MaK) FROM khachthue INNER JOIN thanhvien ON khachthue.MaK = thanhvien.MaK WHERE YEAR(khachthue.NgayThue) < ? OR (YEAR(khachthue.NgayThue) = ? AND MONTH(khachthue.NgayThue) <= ?)";
//
//            this.con = this.cp.getConnection("totalCustomerChart");
            XYChart.Series chart = new XYChart.Series();

            LocalDateMonthY time = new LocalDateMonthY();

            XYChart.Series<String, Integer> chart0 = new XYChart.Series<>();
            String dateString;
            for (int i = 9; i >= 0; i--) {
//                this.pre = this.con.prepareStatement(sql);
                if (time.getMonthNow() < time.previousMonth(i)) {
                    year = time.previousYear(1);
//                    pre.setInt(1, time.previousYear(1));
//                    pre.setInt(2, time.previousYear(1));
                    dateString = "-" + time.previousYear(1);
                } else {
                    year = time.getYearNow();
//                    pre.setInt(1, time.getYearNow());
//                    pre.setInt(2, time.getYearNow());
                    dateString = "-" + time.getYearNow();
                }
                month = time.previousMonth(i);
//                pre.setInt(3, time.previousMonth(i));
                dateString = time.previousMonth(i) + dateString;
//                this.results = pre.executeQuery();

                chart0.getData().add(new XYChart.Data<>(dateString, TenantFunctionImpl.getInstance().selectCount(year, month)/*results.getInt(1))*/));

            }

            totalCustomerChart.getData().add(chart0);

//        } catch (SQLException ex) {
//            Logger.getLogger(TenantController1.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    public void saveExcelFile2(ObservableList<Member> listMember, File file) {
        //Khai bao
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();

        //Set style
        font.setBold(true);
        headerStyle.setFont(font);
        headerStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //Dong tieu de
        Row headerRow = sheet.createRow(0);
        String[] headerTitle = {"STT", "Mã thành viên", "Mã phòng", "Mã tòa nhà", "Tên thành viên", "Giới tính", "Ngày sinh", "CCCD", "Số điện thoại", "Quê quán"};
        for (int i = 0; i <= 9; ++i) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headerTitle[i]);
            cell.setCellStyle(headerStyle);
        }

        //Cac dong du lieu
        Row row;
        for (int i = 0; i < listMember.size(); i++) {
            Member member = (Member) listMember.get(i);
            row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue((double) (i + 1));
            row.createCell(1).setCellValue(member.getMemberID());
            row.createCell(2).setCellValue(member.getTenant().getRoom().getRoomID());
            row.createCell(3).setCellValue(member.getTenant().getHomeTown().getTownID());
            row.createCell(4).setCellValue(member.getName());
            row.createCell(5).setCellValue(member.getSex());
            row.createCell(6).setCellValue(member.getBirthdate());
            row.createCell(7).setCellValue(member.getCitizenID());
            row.createCell(8).setCellValue(member.getPhoneNum());
            row.createCell(9).setCellValue(member.getPlaceOrigin());
        }

        //try-with-resources
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void convertExcel2() {
        //Cua so chon file
        FileChooser chooseFile = new FileChooser();

        //Gio hien tai
        LocalDate currentDate = LocalDate.now();

        //Dinh dang
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);

        //Dat ten file
        if (this.keyListMember.getText() == null) {
            chooseFile.setInitialFileName("Danh sách thành viên " + formattedDate);
        } else if (this.keyListMember.getText() != null) {
            chooseFile.setInitialFileName("Danh sách thành viên theo " + this.keyListMember.getText() + "_" + formattedDate);
        }

        //Phan mo rong cua file
        FileChooser.ExtensionFilter excelFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", new String[]{"*.xlsx"});
        chooseFile.getExtensionFilters().add(excelFilter);

        //Cua so hien thi
        Stage showFileChooser = new Stage();
        File file = chooseFile.showSaveDialog(showFileChooser);
        if (file != null) {
            this.saveExcelFile2(this.listMember, file);
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

//    @FXML
//    public void OpenTenantForm(ActionEvent event) {
//        this.currentWidth = Support.stage.getWidth();
//        this.currentHeight = Support.stage.getHeight();
//        this.currentX = Support.stage.getX();
//        this.currentY = Support.stage.getY();
//        Platform.runLater(() -> {
//            try {
//                Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/TenantDesign_1.fxml"));
//                Support.stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//                Support.scene = new Scene(Support.root);
//                Support.stage.setScene(Support.scene);
//                Support.stage.setTitle(TitleForms.TitleTenantForm);
//                Support.stage.getIcons().add(Support.icon);
//                Support.stage.setWidth(this.currentWidth);
//                Support.stage.setHeight(this.currentHeight);
//                Support.stage.setX(this.currentX);
//                Support.stage.setY(this.currentY);
//                Support.stage.show();
//            } catch (IOException var3) {
//                var3.printStackTrace();
//            }
//
//        });
//    }

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
    
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.TenantButton.setStyle(Support.colorButton);
        this.accountNameLabel.setText(Support.NameAccount);
        //
        this.listRoomState = FXCollections.observableArrayList();
        this.listTenant = FXCollections.observableArrayList();
        this.listMember = FXCollections.observableArrayList();
        /*
        //Bộ quan ly ket noi
        ConnectionPool cp = new ConnectionPoolImpl();

        //Doi tuong thu thi ket noi
        TenantFunctionImpl tenantFunctionImpl = new TenantFunctionImpl(cp);

        //Khoi tao
        ObservableList<Tenant> ot = FXCollections.observableArrayList();
         */
        //Set comboBox toa nha
        this.listRoomState.addAll(TenantFunctionImpl.getInstance().selectHomeTownOfTenant());

        for (Tenant tenant : this.listRoomState) {
            chooseTownComboBox.getItems().add(tenant.getHomeTown().getTownID());
//            System.out.println(tenant);
        }

        //Set danh sach phong
        this.listRoomState.clear();
        this.listRoomState.addAll(TenantFunctionImpl.getInstance().selectAllTenant2());
        this.setRoomMenu(this.listRoomState);
//        for (Tenant tenant : listRoomState) {
//            System.out.println(tenant);
//        }
        //Khoi tao danh sach khach thue
        this.listRoomState.clear();
        this.listTenant.addAll(TenantFunctionImpl.getInstance().selectAllTenant());
        this.setInforTenantTable(this.listTenant);

        //Khoi tao danh sach thanh vien
        this.listMember.addAll(MemberFunctionImpl.getInstance().selectAllMember());
//        if(this.listMember != null){
//            System.exit(0);
//        }
//        for (Member member : listMember) {
//            System.out.println(member.getMemberID());
//        }
//        if (this.listMember != null) {
//            System.out.println("No null ----------------------------------->");
//            System.out.println(listMember.size());
//        }
        this.setInforMemberTable(this.listMember);

        newCustomerChart();
        totalCustomerChart();
    }
}
