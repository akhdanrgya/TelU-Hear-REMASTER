package com.tubes.teluhear;

import com.tubes.teluhear.database.MusicDAO;
import com.tubes.teluhear.database.MusicModel;

import com.tubes.teluhear.database.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class PlayedMusicController implements Initializable {

    @FXML
    private Label Judul;

    @FXML
    private Slider SliderMusic;

    @FXML
    private Button pauseText;

    private MediaPlayer mediaPlayer;
    private List<MusicModel> musicList;
    private int currentIndex = 0;
    private MusicDAO musicDAO;
    private MusicModel currentMusic;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Connection connection = dbConnection.getConnection();
        musicDAO = new MusicDAO(connection);

        musicList = musicDAO.getAllMusic();

        if (!musicList.isEmpty()) {
            setMusicData(musicList.get(currentIndex));
        }

        // Tambahkan listener ke slider untuk kontrol musik
        SliderMusic.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (SliderMusic.isValueChanging() && mediaPlayer != null) {
                mediaPlayer.seek(mediaPlayer.getMedia().getDuration().multiply(newValue.doubleValue() / 100));
            }
        });
    }

    public void setMusicData(MusicModel musicData) {
        Judul.setText(musicData.getJudul());
        setCurrentMusic(musicData);
    }

    public void setCurrentMusic(MusicModel music) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
        currentMusic = music;

        File file = new File(music.getFile_path());
        Media media = file.exists()
                ? new Media(file.toURI().toString())
                : new Media(getClass().getResource("/" + music.getFile_path()).toExternalForm());
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setOnReady(() -> {
            SliderMusic.setMax(100); // Slider max selalu 100
        });

        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (!SliderMusic.isValueChanging()) {
                double progress = newValue.toSeconds() / mediaPlayer.getMedia().getDuration().toSeconds() * 100;
                SliderMusic.setValue(progress);
            }
        });

        mediaPlayer.setOnEndOfMedia(() -> Next(null));
    }

    @FXML
    void Next(ActionEvent event) {
        if (musicList != null && !musicList.isEmpty()) {
            currentIndex = (currentIndex + 1) % musicList.size();
            setMusicData(musicList.get(currentIndex));
            mediaPlayer.play();
            pauseText.setText("Pause");
        }
    }

    @FXML
    void Prev(ActionEvent event) {
        if (musicList != null && !musicList.isEmpty()) {
            currentIndex = (currentIndex - 1 + musicList.size()) % musicList.size();
            setMusicData(musicList.get(currentIndex));
            mediaPlayer.play();
            pauseText.setText("Pause");
        }
    }

    @FXML
    void Play(ActionEvent event) {
        if (mediaPlayer != null) {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                pauseText.setText("Play");
            } else {
                mediaPlayer.play();
                pauseText.setText("Pause");
            }
        }
    }

    public void stopMusicOnClose(Stage stage) {
        stage.setOnCloseRequest(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            }
        });
    }
}
