package com.tubes.teluhear;

import com.tubes.teluhear.database.dbConnection;
import com.tubes.teluhear.database.SubsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class PaymentController {

    private int idUser;
    private SubsDAO subsDAO;

    @FXML
    private TextField paymentField;

    @FXML
    private Button bayarButton;

    @FXML
    private Button cancelButton;

    public PaymentController() {
        this.subsDAO = new SubsDAO(dbConnection.getConnection());
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }


    @FXML
    private void handlePaymentAction(ActionEvent event) {
        String paymentText = paymentField.getText();

        if (paymentText.isEmpty()) {
            showAlert(AlertType.ERROR, "Input Error", "Please enter the payment amount.");
        } else {
            try {
                double amount = Double.parseDouble(paymentText);

                if (amount >= 1000000) {
                    subsDAO.addSubs(idUser);

                    showAlert(AlertType.INFORMATION, "Payment Successful", "Payment of " + amount + " was successful!");
                    SessionManager.getInstance().setRole("premium");

                    closeWindow(event);
                } else {
                    showAlert(AlertType.WARNING, "Payment Error", "Insufficient amount. Please enter at least 1.000.000");
                }
            } catch (NumberFormatException e) {
                showAlert(AlertType.ERROR, "Invalid Input", "Please enter a valid number.");
            }
        }
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        closeWindow(event);
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
