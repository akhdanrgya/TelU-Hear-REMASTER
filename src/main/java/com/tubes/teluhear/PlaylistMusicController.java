package com.tubes.teluhear;

import com.tubes.teluhear.database.PlaylistMusicDAO;
import com.tubes.teluhear.database.PlaylistMusicModel;
import com.tubes.teluhear.database.dbConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PlaylistMusicController implements Initializable {

    @FXML
    private ListView<String> playlistMusicListView;

    private int playlistId;

    private PlaylistMusicDAO playlistMusicDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Debugging untuk memastikan ListView terhubung dengan benar
        System.out.println("Is playlistMusicListView null in initialize? " + (playlistMusicListView == null));

        // Inisialisasi DAO
        playlistMusicDAO = new PlaylistMusicDAO(dbConnection.getConnection());
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;

        // Cek apakah ListView sudah terhubung
//        if (playlistMusicListView == null) {
//            System.out.println("playlistMusicListView is null in setPlaylistId!");
//            return;
//        }

        System.out.println("Setting Playlist ID: " + playlistId);

        // Ambil data musik dari DAO
        List<PlaylistMusicModel> playlistMusicList = playlistMusicDAO.getPlaylistMusic(playlistId);

        // Panggil metode untuk mengisi ListView dengan data musik
        populatePlaylistMusicList(playlistMusicList);
    }

    private void populatePlaylistMusicList(List<PlaylistMusicModel> playlistMusicList) {
        // Clear items sebelumnya
//        playlistMusicListView.getItems().clear();

        // Jika data musik kosong, tampilkan pesan
        if (playlistMusicList == null || playlistMusicList.isEmpty()) {
            System.out.println("No music found in this playlist.");
//            playlistMusicListView.getItems().add("No music found in this playlist.");
            return;
        }

        // Menambahkan data musik ke ListView
        for (PlaylistMusicModel music : playlistMusicList) {
            System.out.println("Music ID: " + music.getIdMusic());  // Debugging
//            playlistMusicListView.getItems().add("Music ID: " + music.getIdMusic());
        }
    }
}
