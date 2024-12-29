package com.tubes.teluhear;

import com.tubes.teluhear.database.MusicModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class MusicCardController {

    @FXML
    private Label musicIndex;

    @FXML
    private Label musicJudul;

    @FXML
    private Label musicArtist;

    @FXML
    private Pane musicCardView;

    public void setMusicData(MusicModel music, int index) {
        musicIndex.setText(index + "."); // Tampilkan index musik
        musicJudul.setText(music.getJudul());
        musicArtist.setText(music.getArtist());
    }
}
