package com.tubes.teluhear;

import com.tubes.teluhear.database.dbConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import com.tubes.teluhear.database.MusicModel;
import com.tubes.teluhear.database.MusicDAO;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MusicController implements Initializable {

    @FXML
    private GridPane musicGrid;

    @FXML
    private Button pauseText;

    private MusicDAO musicDAO;
    private MusicModel currentMusic;
    private MediaPlayer mediaPlayer;

    private List<MusicModel> musicList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        musicDAO = new MusicDAO(dbConnection.getConnection());

        musicList = musicDAO.getAllMusic();


        populateMusicGrid(musicList);
        System.out.println("MusicController initialized.");
        System.out.println(musicList.size());
    }

    @FXML
    private void populateMusicGrid(List<MusicModel> musicDataList) {
        if (musicDataList == null || musicDataList.isEmpty()) {
            System.out.println("No music data found.");
            return;
        }

        try {
            for (int i = 0; i < musicDataList.size(); i++) {
                MusicModel music = musicDataList.get(i);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tubes/teluhear/MusicCard.fxml"));
                Pane musicCardView = loader.load();

                MusicCardController controller = loader.getController();
                controller.setMusicData(music, i + 1);

                controller.setClickListener(new MusicCardClickListener() {
                    @Override
                    public void onMusicCardClicked(MusicModel clickedMusic, List<MusicModel> musicList) {
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

                musicGrid.add(musicCardView, 0, i);
            }
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


