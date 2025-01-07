package com.tubes.teluhear;

import com.tubes.teluhear.database.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.tubes.teluhear.database.SubsDAO;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PremiumController implements Initializable {

    private int idUser;
    private String role;
    private SubsDAO subsDAO;

    @FXML
    private Button premiumButtonAction;

    @FXML
    private Label premiumLabel1;

    @FXML
    private Label premiumLabel2;

    public PremiumController() {
        this.subsDAO = new SubsDAO(dbConnection.getConnection());
        this.premiumButtonAction = new Button();
    }

    public void initialize(URL url, ResourceBundle rb) {
        role = SessionManager.getInstance().getRole();
        if (role.equals("premium")) {
            premiumLabel1.setVisible(false);
            premiumLabel2.setText("Akun anda sudah Premium");
            premiumButtonAction.setVisible(false);
        }
    }

    @FXML
    void premiumButton(ActionEvent event) {
        idUser = SessionManager.getInstance().getId();

        // Membuka payment.fxml jika tombol Premium ditekan
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tubes/teluhear/payment.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(loader.load());

            // Mengirim idUser ke PaymentController
            PaymentController paymentController = loader.getController();
            paymentController.setIdUser(idUser);

            stage.setScene(scene);
            stage.setTitle("Payment");
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading payment.fxml: " + e.getMessage());
        }

        try {
            subsDAO.addSubs(idUser);
            premiumLabel1.setVisible(false);
            premiumLabel2.setText("Akun anda sudah Premium");
            premiumButtonAction.setVisible(false);
            SessionManager.getInstance().setRole("premium");
        } catch (Exception e) {
            System.out.println("Error adding subscription" + e.getMessage());
        }
    }

}
