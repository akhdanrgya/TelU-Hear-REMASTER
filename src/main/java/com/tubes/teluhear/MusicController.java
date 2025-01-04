package com.tubes.teluhear;

import com.tubes.teluhear.database.dbConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MusicController implements Initializable {

    @FXML
    private GridPane musicGrid;

    @FXML
    private Slider musicSlider;

    @FXML
    private Label judulBawah;

    @FXML
    private Button pauseText;

    private MusicDAO musicDAO;
    private MusicModel currentMusic;
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        musicDAO = new MusicDAO(dbConnection.getConnection());

        List<MusicModel> musicDataList = musicDAO.getAllMusic();

        populateMusicGrid(musicDataList);

        System.out.println("MusicController initialized.");
    }

    private void populateMusicGrid(List<MusicModel> musicDataList) {
        if (musicDataList == null || musicDataList.isEmpty()) {
            System.out.println("No music data found.");
            return;
        }

        for (int i = 0; i < musicDataList.size(); i++) {
            MusicModel music = musicDataList.get(i);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tubes/teluhear/MusicCard.fxml"));
                Pane musicCardView = loader.load();

                MusicCardController controller = loader.getController();
                controller.setMusicData(music, i + 1);

                controller.setClickListener(new MusicCardClickListener() {
                    @Override
                    public void onMusicCardClicked(MusicModel music) {
                        setCurrentMusic(music);
                        playButton();
                    }
                });

                musicGrid.add(musicCardView, 0, i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setCurrentMusic(MusicModel music) {
        this.currentMusic = music;
    }

    void playButton() {
        if (currentMusic != null) {

            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            }

            File file = new File(currentMusic.getFile_path());
            if (file.exists()) {
                Media media = new Media(file.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
            } else {
                URL resource = getClass().getResource("/" + currentMusic.getFile_path());
                if (resource != null) {
                    System.out.println("Playing: " + currentMusic.getFile_path());
                    judulBawah.setText(currentMusic.getJudul());
                    pauseText.setText("Pause");
                    Media media = new Media(resource.toExternalForm());
                    mediaPlayer = new MediaPlayer(media);
                } else {
                    System.out.println("Resource tidak valid bos: " + currentMusic.getFile_path());
                }
            }

            if (mediaPlayer != null) {
                mediaPlayer.play();
            } else {
                System.out.println("Failed to initialize mediaPlayer.");
            }

        } else {
            judulBawah.setText("No music selected");
        }
    }


    @FXML
    void pauseButton(ActionEvent event) {
        if (mediaPlayer != null) {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                pauseText.setText("Play");
            } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                mediaPlayer.play();
                pauseText.setText("Pause");
            }
        }
    }


    @FXML
    void nextButton(ActionEvent event) {
        if (currentMusic != null) {
            System.out.println("Next: " + currentMusic.getFile_path());
        } else {
            System.out.println("No music selected");
        }
    }

    @FXML
    void previousButton(ActionEvent event) {
        if (currentMusic != null) {
            System.out.println("Prev: " + currentMusic.getFile_path());
        } else {
            System.out.println("No music selected");
        }
    }


}
