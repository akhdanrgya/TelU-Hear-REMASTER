package com.tubes.teluhear;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import com.tubes.teluhear.database.MusicModel;

import java.net.URL;
import java.util.ResourceBundle;

public class MusicCardController implements Initializable {

    @FXML
    private Label musicArtist;

    @FXML
    private Label musicIndex;

    @FXML
    private Label musicJudul;

    private MusicModel musicModel;


    public void initialize (URL location, ResourceBundle resources){
        try {
            System.out.println("card load banget");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMusicData(MusicModel musicModel){
        this.musicModel = musicModel;

        musicJudul.setText(musicModel.getJudul());
        musicArtist.setText(musicModel.getArtist());

        String id = String.valueOf(musicModel.getId());
        musicIndex.setText(id);

    }
}