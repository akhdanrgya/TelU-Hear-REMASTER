package com.tubes.teluhear;

import com.tubes.teluhear.database.dbConnection;
import com.tubes.teluhear.database.PlaylistDAO;
import com.tubes.teluhear.database.PlaylistModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class PlaylistFormController {

    @FXML
    private TextField inputJudul;

    @FXML

    private String judul;
    private int userId;

    private PlaylistDAO playlistDAO;

    private PlaylistController playlistController;

    public PlaylistFormController() {
        this.playlistDAO = new PlaylistDAO(dbConnection.getConnection());
    }

    @FXML
    void submit(ActionEvent event) throws IOException {
        judul = inputJudul.getText();
        userId = SessionManager.getInstance().getId();

        if (judul == null || judul.isEmpty()) {
            System.out.println("Judul tidak boleh kosong");
            return;
        }

        PlaylistModel playlist = new PlaylistModel(userId, judul, 0);

        if (playlistDAO.addPlaylist(playlist)) {
            System.out.println("Playlist berhasil ditambahkan");
        } else {
            System.out.println("Gagal menambahkan playlist");
        }

        Stage stage = (Stage) inputJudul.getScene().getWindow();
        stage.close();
        playlistController.reloadPlaylist();
    }

}
