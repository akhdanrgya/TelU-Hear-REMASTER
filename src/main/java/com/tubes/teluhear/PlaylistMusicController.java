package com.tubes.teluhear;

import com.tubes.teluhear.database.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PlaylistMusicController implements Initializable {

    @FXML
    private ListView<String> playlistMusicListView;

    @FXML
    private GridPane playlistMusicGrid;

    private int playlistId;

    private PlaylistMusicDAO playlistMusicDAO;

    private MusicDAO musicDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        System.out.println("Is playlistMusicListView null in initialize? " + (playlistMusicListView == null));


        playlistMusicDAO = new PlaylistMusicDAO(dbConnection.getConnection());
        musicDAO = new MusicDAO(dbConnection.getConnection());
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;

        System.out.println("Setting Playlist ID: " + playlistId);

        List<PlaylistMusicModel> playlistMusicList = playlistMusicDAO.getPlaylistMusic(playlistId);

        List<MusicModel> musicList = musicDAO.getMusicByIds(getMusicIdsFromPlaylist(playlistMusicList));

        populatePlaylistMusicListID(musicList);
    }

    private List<Integer> getMusicIdsFromPlaylist(List<PlaylistMusicModel> playlistMusicList) {
        List<Integer> musicIds = new ArrayList<>();

        for (PlaylistMusicModel music : playlistMusicList) {
            musicIds.add(music.getIdMusic());
        }

        return musicIds;
    }

    private void populatePlaylistMusicListID(List<MusicModel> musicList) {
        if (musicList == null || musicList.isEmpty()) {
            System.out.println("No music found in this playlist.");
            return;
        }

        for (MusicModel music : musicList) {
            System.out.println("Music ID: " + music.getJudul());
        }

        for (int i = 0; i < musicList.size(); i++) {
            MusicModel music = musicList.get(i);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tubes/teluhear/MusicCard.fxml"));
                Pane musicCardView = loader.load();

                MusicCardController controller = loader.getController();
                controller.setMusicData(music, i + 1);

                playlistMusicGrid.add(musicCardView, 0, i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
