package com.tubes.teluhear;

import com.tubes.teluhear.database.MusicModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

interface MusicCardClickListener {
    void onMusicCardClicked(MusicModel music);
}

public class MusicCardController {

    @FXML
    private Label musicIndex;

    @FXML
    private Label musicJudul;

    @FXML
    private Label musicArtist;

    @FXML
    private Pane musicCardView;

    private MusicModel musicData;
    private MusicCardClickListener clickListener;

    public void setMusicData(MusicModel music, int index) {
        this.musicData = music;
        musicIndex.setText(index + "."); // Tampilkan index musik
        musicJudul.setText(music.getJudul());
        musicArtist.setText(music.getArtist());
    }

    public void setClickListener(MusicCardClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @FXML
    private void onCardClicked(MouseEvent event) {
        if (clickListener != null && musicData != null) {
            clickListener.onMusicCardClicked(musicData);
        }
    }


}
