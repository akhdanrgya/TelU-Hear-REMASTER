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
    private TextField paymentField;  // Untuk mengambil input pembayaran dari TextField

    @FXML
    private Button bayarButton;      // Tombol untuk melakukan pembayaran

    @FXML
    private Button cancelButton;     // Tombol untuk membatalkan

    public PaymentController() {
        this.subsDAO = new SubsDAO(dbConnection.getConnection());
    }

    // Mengambil idUser dari PremiumController
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    // Aksi ketika tombol "BAYAR" ditekan
    @FXML
    private void handlePaymentAction(ActionEvent event) {
        String paymentText = paymentField.getText();  // Mengambil input dari TextField

        // Validasi input (harus berupa angka)
        if (paymentText.isEmpty()) {
            showAlert(AlertType.ERROR, "Input Error", "Please enter the payment amount.");
        } else {
            try {
                // Mencoba mengkonversi input ke tipe double
                double amount = Double.parseDouble(paymentText);

                // Proses pembayaran (simulasi atau implementasi lebih lanjut)
                if (amount >= 1000000) {
                    // Simpan langganan premium di database
                    subsDAO.addSubs(idUser);
                    
                    // Beri pesan keberhasilan pembayaran
                    showAlert(AlertType.INFORMATION, "Payment Successful", "Payment of " + amount + " was successful!");

                    // Menutup window pembayaran setelah sukses
                    closeWindow(event);
                } else {
                    showAlert(AlertType.WARNING, "Payment Error", "Insufficient amount. Please enter at least 1.000.000");
                }
            } catch (NumberFormatException e) {
                // Jika input bukan angka
                showAlert(AlertType.ERROR, "Invalid Input", "Please enter a valid number.");
            }
        }
    }

    // Aksi ketika tombol "CANCEL" ditekan
    @FXML
    private void handleCancelAction(ActionEvent event) {
        // Menutup window saat tombol CANCEL ditekan
        closeWindow(event);
    }

    // Metode untuk menampilkan alert (pesan peringatan atau informasi)
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Menutup window setelah pembayaran berhasil atau dibatalkan
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
