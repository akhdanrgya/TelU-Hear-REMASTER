package com.tubes.teluhear;

import com.tubes.teluhear.database.PlaylistDAO;
import com.tubes.teluhear.database.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EditPlaylistController {

    @FXML
    private ImageView gambarPlaylist;

    @FXML
    private TextField inputJudul;

    private PlaylistDAO playlistDAO;

    private int playlistId;
    private String judul;

    public EditPlaylistController() {
        this.playlistDAO = new PlaylistDAO(dbConnection.getConnection());
    }

    public void setPlaylist(int id, String judul) {
        this.playlistId = id;
        this.judul = judul;

        inputJudul.setText(judul);

    }

    @FXML
    void submit(ActionEvent event) {
        String editedJudul = inputJudul.getText();

        playlistDAO.updatePlaylist(playlistId, judul);
    }

    @FXML
    void uploadImage(ActionEvent event) {

    }

}
