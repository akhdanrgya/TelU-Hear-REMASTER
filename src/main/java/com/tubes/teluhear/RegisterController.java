package com.tubes.teluhear;


import com.tubes.teluhear.database.UserDAO;
import com.tubes.teluhear.database.UserModel;
import com.tubes.teluhear.database.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private Label alertR;

    @FXML
    private PasswordField cpasswordfr;

    @FXML
    private PasswordField passwordfr;

    @FXML
    private TextField usernamefr;

    private UserDAO UserDAO;

    public RegisterController() {
        this.UserDAO = new UserDAO(dbConnection.getConnection());
    }

    @FXML
    void Login(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass(). getResource("/com/tubes/teluhear/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) usernamefr.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Login Telu-Hear");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Register(ActionEvent event) {
        String username = usernamefr.getText();
        String password = passwordfr.getText();
        String confirmPassword = cpasswordfr.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            alertR.setText("Username atau Password tidak boleh kosong");
        } else if (!password.equals(confirmPassword)) {
            alertR.setText("Password dan Konfirmasi Password tidak cocok");
        } else {
            UserModel user = new UserModel(username, password, 0);
            boolean isRegisterSuccess = UserDAO.register(user);

            if (isRegisterSuccess) {
                alertR.setText("Registrasi berhasil! Silakan login.");
            } else {
                alertR.setText("Registrasi gagal. Username mungkin sudah digunakan.");
            }
        }
    }

}
