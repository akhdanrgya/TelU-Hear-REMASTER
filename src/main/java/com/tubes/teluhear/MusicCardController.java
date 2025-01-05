package com.tubes.teluhear;

import com.tubes.teluhear.database.MusicModel;
import com.tubes.teluhear.database.MusicDAO;
import com.tubes.teluhear.database.dbConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MusicCardController implements Initializable {

    @FXML
    private Label musicIndex;

    @FXML
    private Label musicJudul;

    @FXML
    private Label musicArtist;

    @FXML
    private Pane musicCardView;

    private MusicModel musicData;

    private LayoutController layoutController;

    private MusicDAO musicDAO;

    private MusicCardClickListener clickListener;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        musicDAO = new MusicDAO(dbConnection.getConnection());
    }

    public void setMusicData(MusicModel music, int index) {
        this.musicData = music;
        musicIndex.setText(index + ".");
        musicJudul.setText(music.getJudul());
        musicArtist.setText(music.getArtist());
    }

    public void setLayoutController(LayoutController layoutController) {
        this.layoutController = layoutController;
    }

    public void setClickListener(MusicCardClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @FXML
    private void onCardClicked(MouseEvent event) {

        System.out.println(layoutController);

        if (layoutController != null) {
            layoutController.showPlayedMusic(musicData);
        }

        if (clickListener != null) {
            clickListener.onMusicCardClicked(musicData);
        }

    }
}
