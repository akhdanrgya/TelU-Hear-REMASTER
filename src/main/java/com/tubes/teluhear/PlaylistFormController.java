package com.tubes.teluhear;

import com.tubes.teluhear.database.dbConnection;
import com.tubes.teluhear.database.PlaylistDAO;
import com.tubes.teluhear.database.PlaylistModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

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
