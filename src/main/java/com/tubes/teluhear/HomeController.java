package com.tubes.teluhear;

import com.tubes.teluhear.database.PlaylistDAO;
import com.tubes.teluhear.database.PlaylistModel;
import com.tubes.teluhear.database.MusicDAO;
import com.tubes.teluhear.database.MusicModel;
import com.tubes.teluhear.database.dbConnection;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private GridPane musicGrid;

    @FXML
    private GridPane playlistGrid;

    @FXML
    private Label welcomeText;

    private PlaylistDAO playlistDAO;
    private MusicDAO musicDAO;

    private List<MusicModel> musicList;
    private MusicModel currentMusic;
    private MediaPlayer mediaPlayer;

    private int userId = SessionManager.getInstance().getId();


    public void initialize(URL url, ResourceBundle rb) {
        playlistDAO = new PlaylistDAO(dbConnection.getConnection());
        musicDAO = new MusicDAO(dbConnection.getConnection());
        String username = SessionManager.getInstance().getUsername();

        List<PlaylistModel> playlistDataList = playlistDAO.getPlaylistByUser(userId);
        List<MusicModel> musicDataList = musicDAO.getAllMusic();

        musicList = musicDataList;

        populatePlaylist(playlistDataList);
        populateMusic(musicDataList);

        welcomeText.setText("Welcome Back " + username + "!");

    }

    private void populatePlaylist(List<PlaylistModel> playlistModelList) {
        if (playlistModelList == null || playlistModelList.isEmpty()) {
            System.out.println("No playlist found");
            return;
        }

        int column = 0;
        int row = 0;

        for (int i = 0; i < playlistModelList.size(); i++) {
            PlaylistModel playlistModel = playlistModelList.get(i);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tubes/teluhear/PlaylistCard.fxml"));
                Pane playlistCardView = loader.load();

                PlaylistCardController playlistCardController = loader.getController();
                playlistCardController.setPlaylistData(playlistModel, false);

                playlistGrid.add(playlistCardView, column, row);
                playlistGrid.setMargin(playlistCardView, new Insets(10));

                column++;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void populateMusic(List<MusicModel> musicModelList) {
        if (musicModelList == null || musicModelList.isEmpty()) {
            System.out.println("No music found");
            return;
        }

        int column = 0;
        int row = 0;

        for (int i = 0; i < musicModelList.size(); i++) {
            MusicModel musicModel = musicModelList.get(i);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tubes/teluhear/MusicCard.fxml"));
                Pane musicCardView = loader.load();

                MusicCardController musicCardController = loader.getController();
                musicCardController.setMusicData(musicModel, i + 1, false);

                musicCardController.setClickListener(new MusicCardClickListener() {
                    @Override
                    public void onMusicCardClicked(MusicModel clickedMusic, List<MusicModel> musicModels) {
                        if (musicList == null || musicList.isEmpty()) {
                            System.out.println("Music list is empty or null.");
                            return;
                        }

                        MusicModel selectedMusic = musicList.stream()
                                .filter(m -> m.getId() == clickedMusic.getId())
                                .findFirst()
                                .orElse(null);

                        if (selectedMusic != null) {
                            setCurrentMusic(selectedMusic);
                            showPlayedMusic(selectedMusic);
                        } else {
                            System.out.println("Music not found in musicList.");
                        }
                    }
                });

                musicGrid.add(musicCardView, column, row);

                row++;
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                int index = musicList.indexOf(musicModel);
                controller.setMusicData(musicList, index);
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


