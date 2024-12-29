package com.tubes.teluhear;

import com.tubes.teluhear.database.MusicModel;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MusicCardController {

    private MusicModel music;

    // Inisialisasi elemen UI
    private VBox musicCardView;
    private Label titleLabel;
    private Label artistLabel;
    private Label genreLabel;
    private Label durationLabel;

    public MusicCardController() {
        // Buat elemen UI
        titleLabel = new Label();
        artistLabel = new Label();
        genreLabel = new Label();
        durationLabel = new Label();

        // Gabungkan elemen-elemen tersebut dalam VBox
        musicCardView = new VBox(titleLabel, artistLabel, genreLabel, durationLabel);
    }

    // Set data musik ke label
    public void setMusicData(MusicModel music) {
        this.music = music;

        titleLabel.setText("Title: " + music.getJudul());
        artistLabel.setText("Artist: " + music.getArtist());
        genreLabel.setText("Genre: " + music.getGenre());
        durationLabel.setText("Duration: " + music.getDuration());
    }

    // Mengembalikan view dari MusicCard
    public VBox getView() {
        return musicCardView;
    }
}
