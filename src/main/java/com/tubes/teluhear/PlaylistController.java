package com.tubes.teluhear;

import com.tubes.teluhear.database.PlaylistDAO;
import com.tubes.teluhear.database.PlaylistModel;
import com.tubes.teluhear.database.dbConnection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.geometry.Insets;
import javafx.stage.Stage;

public class PlaylistController implements Initializable {

    @FXML
    private GridPane playlistGrid;

    @FXML
    private Button addButton;

    private PlaylistDAO playlistDAO;
    private int userId;

    public void initialize(URL url, ResourceBundle rb) {
        playlistDAO = new PlaylistDAO(dbConnection.getConnection());
        userId = SessionManager.getInstance().getId();

        reloadPlaylist();
    }

    public void reloadPlaylist() {
        List<PlaylistModel> playlistDataList = playlistDAO.getPlaylistByUser(userId);
        System.out.println("Reloading playlist data: " + playlistDataList);
        File dir = new File("src/main/resources/image");
        File[] files = dir.listFiles();
        System.out.println(Arrays.toString(files));
        playlistGrid.getChildren().clear();

        populatePlaylistGrid(playlistDataList);

        if (SessionManager.getInstance().getRole().equals("free")) {
            if (playlistDataList.size() > 1) {
                addButton.setDisable(true);
            }
        }
    }

    private void populatePlaylistGrid(List<PlaylistModel> playlistModelList) {
        if (playlistModelList == null || playlistModelList.isEmpty()) {
            System.out.println("No playlist data found.");
            return;
        }

        System.out.println(playlistModelList);

        int column = 0;
        int row = 0;

        for (PlaylistModel playlist : playlistModelList) {
            try {
                System.out.println(playlist);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tubes/teluhear/PlaylistCard.fxml"));
                Pane playlistCardView = loader.load();

                PlaylistCardController controller = loader.getController();
                controller.setPlaylistData(playlist);

                playlistGrid.add(playlistCardView, column, row);
                playlistGrid.setMargin(playlistCardView, new Insets(10));

                column++;
                if (column >= 3) {
                    column = 0;
                    row++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addPlaylist(ActionEvent actionEvent) {
        System.out.println("Add Playlist");
        goToForm();
    }

    private void goToForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tubes/teluhear/addPlaylistForm.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Add Playlist");

            stage.show();
            stage.setOnCloseRequest(event -> reloadPlaylist());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
