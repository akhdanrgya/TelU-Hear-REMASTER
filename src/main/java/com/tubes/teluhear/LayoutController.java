package com.tubes.teluhear;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tubes.teluhear.database.MusicModel;

public class LayoutController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private StackPane contentArea;

    @FXML
    private Button homeButton, musicButton, playlistButton, premiumButton;

    @FXML
    private Label playedJudul, playedArtist;

    public void initialize (URL location, ResourceBundle resources){

        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/com/tubes/teluhear/home.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);

        } catch (IOException ex) {
            Logger.getLogger(LayoutController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void homeButtonClick(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/tubes/teluhear/home.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void musicButtonClick(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/tubes/teluhear/music.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void playlistButtonClick(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/tubes/teluhear/playlist.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void premiumButtonClick(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/com/tubes/teluhear/premium.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void setPlayedMusic(MusicModel played) {

        Platform.runLater(() -> {
            System.out.println("Setting music: " + played.getJudul() + " by " + played.getArtist());
            System.out.println("Before setting text: " + playedJudul.getText() + " / " + playedArtist.getText());
            playedJudul.setText(played.getJudul());
            playedArtist.setText(played.getArtist());
            System.out.println("After setting text: " + playedJudul.getText() + " / " + playedArtist.getText());
            homeButton.setVisible(true);
        });


    }




}
