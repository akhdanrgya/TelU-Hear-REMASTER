package com.tubes.teluhear;

import com.tubes.teluhear.database.UserDAO;
import com.tubes.teluhear.database.SubsDAO;
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

public class LoginController {

    @FXML
    private PasswordField passwordF;

    @FXML
    private TextField usernameF;

    @FXML
    private Label alertLabel;

    private UserDAO UserDAO;
    private SubsDAO SubsDAO;

    public LoginController() {
        this.UserDAO = new UserDAO(dbConnection.getConnection());
        this.SubsDAO = new SubsDAO(dbConnection.getConnection());
    }

    @FXML
    void Login(ActionEvent event) {
        String username = usernameF.getText();
        String password = passwordF.getText();
        int id;
        boolean isPremium;

        if (username.isEmpty() || password.isEmpty()) {
            alertLabel.setText("Username atau Password tidak boleh kosong");
        } else {
            boolean isLogin = UserDAO.login(username, password);
            if (isLogin) {
                id = UserDAO.getUserId(username);
                SessionManager.getInstance().setUsername(username);
                SessionManager.getInstance().setId(id);

                isPremium = SubsDAO.checkSubs(id);

                if(isPremium){
                    SessionManager.getInstance().setRole("premium");
                } else {
                    SessionManager.getInstance().setRole("free");
                }

                goToHome();
            } else {
                alertLabel.setText("Username atau Password salah");
            }
        }
    }


    @FXML
    void Register(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass(). getResource("/com/tubes/teluhear/register.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) usernameF.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Register Telu-Hear");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToHome() {
     try{
         FXMLLoader loader = new FXMLLoader(getClass(). getResource("/com/tubes/teluhear/layout.fxml"));
         Parent root = loader.load();

         Stage stage = (Stage) usernameF.getScene().getWindow();

         Scene scene = new Scene(root);
         stage.setScene(scene);
         stage.setTitle("Telu-Hear");
         stage.show();
     } catch (Exception e) {
         e.printStackTrace();
     }
    }

}
