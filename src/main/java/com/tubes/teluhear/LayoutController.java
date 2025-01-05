package com.tubes.teluhear;

import com.tubes.teluhear.database.MusicDAO;
import com.tubes.teluhear.database.MusicModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LayoutController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private StackPane contentArea;

    @FXML
    private StackPane playedMusicArea;

    private MusicDAO musicDAO;

    public void initialize(URL location, ResourceBundle resources) {
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tubes/teluhear/music.fxml"));
        Parent fxml = loader.load();

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

}
