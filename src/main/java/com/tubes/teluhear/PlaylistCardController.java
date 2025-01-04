package com.tubes.teluhear;

import com.tubes.teluhear.database.PlaylistModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class PlaylistCardController {

    @FXML
    private ImageView imagePlaylist;

    @FXML
    private Label judulPlaylist;

    private PlaylistModel playlistData;


    public void setPlaylistData(PlaylistModel playlist) {
        this.playlistData = playlist; // Set data
        judulPlaylist.setText(playlist.getPlaylist_name());

        String imagePath = playlist.getImage();

        Image image = new Image(getClass().getResourceAsStream(imagePath));
        imagePlaylist.setImage(image);
    }

    @FXML
    private void handleCardClick() {
        try {
            // Load FXML untuk window baru
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tubes/teluhear/PlaylistMusic.fxml"));
            Scene scene = new Scene(loader.load());

            // Ambil controller window baru
            PlaylistMusicController controller = loader.getController();
            controller.setPlaylistId(playlistData.getId());

            // Tampilkan window baru
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Playlist Music");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
