package com.tubes.teluhear;

import com.tubes.teluhear.database.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

    private MusicModel currentMusic;


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

        for (int i = 0; i < musicList.size(); i++) {
            MusicModel music = musicList.get(i);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tubes/teluhear/MusicCard.fxml"));
                Pane musicCardView = loader.load();

                MusicCardController controller = loader.getController();
                controller.setMusicData(music, i + 1);
                controller.setMusicList(musicList);

                controller.setClickListener(new MusicCardClickListener() {
                    @Override
                    public void onMusicCardClicked(MusicModel clickedMusic, List<MusicModel> musicList) {
                        MusicModel selectedMusic = musicList.stream()
                                .filter(m -> m.getId() == clickedMusic.getId())
                                .findFirst()
                                .orElse(null);

                        if (selectedMusic != null) {
                            setCurrentMusic(selectedMusic);
                            showPlayedMusic(selectedMusic);
                        } else {
                            System.out.println("Music not found in the playlist.");
                        }
                    }
                });

                playlistMusicGrid.add(musicCardView, 0, i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    @FXML
    void addMusic(ActionEvent event) {
        goToForm();
    }

    public void goToForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tubes/teluhear/addPlaylistMusicForm.fxml"));
            Pane root = loader.load();

            AddPlaylistMusicFormController controller = loader.getController();

            controller.setPlaylistId(playlistId);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Add Playlist");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentMusic(MusicModel music) {
        this.currentMusic = music;
    }

    public void showPlayedMusic(MusicModel musicModel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tubes/teluhear/PlayedMusic.fxml"));

            Parent root = loader.load();

            PlayedMusicController controller = loader.getController();

            if (controller != null) {
                controller.setMusicData(musicModel);
            } else {
                System.out.println("Controller is null");
            }

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            controller.stopMusicOnClose(stage);
            stage.setScene(scene);
            stage.setTitle("Played Music");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}