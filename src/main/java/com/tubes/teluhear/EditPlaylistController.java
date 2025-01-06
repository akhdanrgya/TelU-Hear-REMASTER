package com.tubes.teluhear;

import com.tubes.teluhear.database.PlaylistDAO;
import com.tubes.teluhear.database.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditPlaylistController {

    @FXML
    private TextField inputJudul;

    @FXML
    private Label alertMessage;

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
        String editedJudul = inputJudul.getText().trim();

        // Cek apakah ada perubahan pada judul
        if (editedJudul.isEmpty()) {
            alertMessage.setText("Judul tidak boleh kosong!");
        } else if (editedJudul.equals(judul)) {
            alertMessage.setText("Tidak ada perubahan pada judul");
        } else {
            boolean isUpdated = playlistDAO.updatePlaylist(playlistId, editedJudul);
            if (isUpdated) {
                alertMessage.setText("Playlist berhasil diperbarui!");
                this.judul = editedJudul;
            } else {
                alertMessage.setText("Gagal memperbarui playlist.");
            }
        }
    }
}
