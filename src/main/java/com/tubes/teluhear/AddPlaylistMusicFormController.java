package com.tubes.teluhear;

import com.tubes.teluhear.database.MusicDAO;
import com.tubes.teluhear.database.MusicModel;
import com.tubes.teluhear.database.PlaylistMusicDAO;
import com.tubes.teluhear.database.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.sql.Connection;
import java.util.List;

public class AddPlaylistMusicFormController {

    @FXML
    private ChoiceBox<String> musicList;

    private MusicDAO musicDAO;

    private PlaylistMusicDAO playlistMusicDAO;

    private int idMusic;
    private int playlistId;

    public AddPlaylistMusicFormController() {
        this.playlistMusicDAO = new PlaylistMusicDAO(dbConnection.getConnection());
        this.musicDAO = new MusicDAO(dbConnection.getConnection());
    }

    public void initialize() {

        loadMusicList();
    }

    private void loadMusicList() {
        List<MusicModel> music = musicDAO.getAllMusic();

        ObservableList<String> musicTitles = FXCollections.observableArrayList();
        for (MusicModel m : music) {
            musicTitles.add(m.getJudul());
        }

        musicList.setItems(musicTitles);
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId; // Simpan playlistId yang dikirim
        System.out.println("Playlist ID set in AddPlaylistMusicFormController: " + playlistId);
    }

    @FXML
    void addButton(ActionEvent event) {
        String selectedMusic = musicList.getValue();
        if (selectedMusic != null) {
            System.out.println("Selected music: " + selectedMusic);
            idMusic = musicDAO.getMusicIdByJudul(selectedMusic);

            playlistMusicDAO.addPlaylistMusic(playlistId, idMusic);
        } else {
            System.out.println("No music selected!");
        }
    }
}
