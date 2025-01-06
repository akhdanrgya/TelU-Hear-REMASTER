package com.tubes.teluhear;

import com.tubes.teluhear.database.PlaylistDAO;
import com.tubes.teluhear.database.PlaylistModel;
import com.tubes.teluhear.database.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    private PlaylistDAO playlistDAO;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button editBtn;

    private boolean btn;

    public PlaylistCardController() {
        this.playlistDAO = new PlaylistDAO(dbConnection.getConnection());
    }


    public void setPlaylistData(PlaylistModel playlist, boolean btn) {
        this.playlistData = playlist;
        judulPlaylist.setText(playlist.getPlaylist_name());

        if(!btn){
            editBtn.setVisible(false);
            deleteBtn.setVisible(false);
        }
    }


    @FXML
    private void handleCardClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tubes/teluhear/PlaylistMusic.fxml"));
            Scene scene = new Scene(loader.load());

            PlaylistMusicController controller = loader.getController();
            controller.setPlaylistId(playlistData.getId());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Playlist Music");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void delete(ActionEvent event) {
        playlistDAO.deletePlaylist(playlistData.getId());
    }

    @FXML
    void edit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tubes/teluhear/editPlaylistForm.fxml"));
            Scene scene = new Scene(loader.load());

            EditPlaylistController controller = loader.getController();
            controller.setPlaylist(playlistData.getId(), playlistData.getPlaylist_name());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Edit Playlist");
            stage.show();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
