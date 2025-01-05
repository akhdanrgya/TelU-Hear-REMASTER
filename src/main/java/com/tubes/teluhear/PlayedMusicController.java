package com.tubes.teluhear;

import com.tubes.teluhear.database.MusicModel;
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
import java.util.ResourceBundle;

public class PlayedMusicController implements Initializable {

    @FXML
    private Label Judul;

    @FXML
    private Slider SliderMusic;

    @FXML
    private Button pauseText;

    private MediaPlayer mediaPlayer;
    private MusicCardClickListener clickListener;

    private MusicModel currentMusic;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playMusic();
    }

    public void setMusicData(MusicModel musicData) {
        Judul.setText(musicData.getJudul());
        setCurrentMusic(musicData);
    }

    public void setCurrentMusic(MusicModel music) {
        this.currentMusic = music;
    }

    @FXML
    void Next(ActionEvent event) {
        // Logic untuk next
    }

    @FXML
    void Play(ActionEvent event) {
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

    void playMusic() {
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
    }

    @FXML
    void Prev(ActionEvent event) {
        // Logic untuk prev
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
