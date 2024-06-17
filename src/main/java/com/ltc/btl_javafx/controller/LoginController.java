package com.ltc.btl_javafx.controller;



import com.ltc.btl_javafx.DAO.DAO_Login;
import com.ltc.btl_javafx.application.Support;
import com.ltc.btl_javafx.application.TitleForms;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.ltc.btl_javafx.model.AccountLogin;

public class LoginController implements Initializable {
    @FXML
    private FontAwesomeIconView openEye;
    @FXML
    private FontAwesomeIconView closeEye;
    @FXML
    private Hyperlink forgotPasswordHyperLink;
    @FXML
    private Label labelpasswordLabel;
    @FXML
    private Label showpasswordLabel;
    @FXML
    private Button loginButton;
    @FXML
    private Button closeSlideButton;
    @FXML
    private AnchorPane loginForm;
    @FXML
    private AnchorPane slideForm;
    @FXML
    private AnchorPane noticeForm;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private TextField userIDTextField;
    private ObservableList<AccountLogin> listAccount;

    public LoginController() {
    }

    @FXML
    public void switchForm(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition();
        if (event.getSource() == this.forgotPasswordHyperLink) {
            slider.setNode(this.slideForm);
            slider.setToX(407.0);
            slider.setDuration(Duration.seconds(0.5));
            this.loginForm.setVisible(false);
            slider.setOnFinished((e) -> {
                this.closeSlideButton.setVisible(true);
                this.noticeForm.setVisible(true);
            });
            slider.play();
        } else if (event.getSource() == this.closeSlideButton) {
            slider.setNode(this.slideForm);
            slider.setToX(0.0);
            slider.setDuration(Duration.seconds(0.5));
            this.noticeForm.setVisible(false);
            slider.setOnFinished((e) -> {
                this.closeSlideButton.setVisible(false);
                this.loginForm.setVisible(true);
            });
            slider.play();
        }

    }

    @FXML
    public void passwordDisplay(KeyEvent event) {
        this.showpasswordLabel.textProperty().bind(Bindings.concat(new Object[]{this.passwordPasswordField.getText()}));
    }

    @FXML
    public void showPassword(MouseEvent event) {
        this.closeEye.setVisible(true);
        this.openEye.setVisible(false);
        this.labelpasswordLabel.setVisible(true);
        this.showpasswordLabel.setVisible(true);
        this.showpasswordLabel.textProperty().bind(Bindings.concat(new Object[]{this.passwordPasswordField.getText()}));
    }

    @FXML
    public void hidePassword(MouseEvent event) {
        this.closeEye.setVisible(false);
        this.openEye.setVisible(true);
        this.showpasswordLabel.setVisible(false);
        this.labelpasswordLabel.setVisible(false);
    }

    @FXML
    public void LoginByEnterKey() {
        this.userIDTextField.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER && this.userIDTextField.isFocused()) {
                try {
                    this.LoginByButton();
                } catch (ClassNotFoundException var3) {
                    var3.printStackTrace();
                }
            }

        });
        this.passwordPasswordField.setOnKeyPressed((event) -> {
            if (event.getCode().getName().equals("Enter") && event.getCode() == KeyCode.ENTER && this.passwordPasswordField.isFocused()) {
                try {
                    this.LoginByButton();
                } catch (ClassNotFoundException var3) {
                    var3.printStackTrace();
                }
            }

        });
    }

    @FXML
    public void LoginByButton() throws ClassNotFoundException {
        if (this.userIDTextField.getText().isBlank() && this.passwordPasswordField.getText().isBlank()) {
            Support.NoticeAlert(AlertType.ERROR, "Thông báo", (String)null, "Tài khoản/Mật khẩu không được để trống !");
            this.userIDTextField.setText("");
            this.passwordPasswordField.setText("");
            this.userIDTextField.requestFocus();
        } else if (this.userIDTextField.getText().isBlank() && this.passwordPasswordField.getText() != null) {
            Support.NoticeAlert(AlertType.ERROR, "Thông báo", (String)null, "Tài khoản không được để trống !");
            this.userIDTextField.requestFocus();
        } else if (this.userIDTextField.getText() != null && this.passwordPasswordField.getText().isBlank()) {
            Support.NoticeAlert(AlertType.ERROR, "Thông báo", (String)null, "Mật khẩu không được để trống !");
            this.passwordPasswordField.requestFocus();
        } else {
            this.listAccount.addAll(DAO_Login.getInstance().selectByCondition(this.userIDTextField.getText(), this.passwordPasswordField.getText(), ""));
            if (this.listAccount.size() == 0) {
                Support.NoticeAlert(AlertType.ERROR, "Thông báo", (String)null, "Tài khoản/Mật khẩu không hợp lệ !");
                this.userIDTextField.requestFocus();
            } else {
                AccountLogin x = (AccountLogin)this.listAccount.get(0);
                Support.IDAccount = x.getUserID();
                Support.pointRank = x.getRank();
                Support.NameAccount = x.getAccountname();
                Support.rankAccount = x.checkRank(x.getRank());
                this.loginButton.getScene().getWindow().hide();

                try {
                    Support.root = (Parent)FXMLLoader.load(this.getClass().getResource("/com/ltc/btl_javafx/designFXML/HomePageDesign.fxml"));
                    Support.stage = new Stage();
                    Support.scene = new Scene(Support.root);
                    Support.stage.setScene(Support.scene);
                    Support.stage.getIcons().add(Support.icon);
                    Support.stage.setTitle(TitleForms.TitleHomePageForm);
                    Support.stage.show();
                } catch (IOException var3) {
                    var3.printStackTrace();
                }
            }
        }

    }

    public void initialize(URL arg0, ResourceBundle arg1) {
        this.listAccount = FXCollections.observableArrayList();
    }
}

