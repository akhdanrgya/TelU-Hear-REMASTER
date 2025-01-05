package com.tubes.teluhear;

import com.tubes.teluhear.database.MusicModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayedMusicController implements Initializable {

    @FXML
    private Label Judul;

    @FXML
    private Slider SliderMusic;

    private MediaPlayer mediaPlayer;
    private MusicCardClickListener clickListener;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setMusicData(MusicModel musicData) {
        Judul.setText(musicData.getJudul());
    }

    @FXML
    void Next(ActionEvent event) {
        // Logic untuk next
    }

    @FXML
    void Play(ActionEvent event) {
        // Logic untuk play
    }

    @FXML
    void Prev(ActionEvent event) {
        // Logic untuk prev
    }
}

