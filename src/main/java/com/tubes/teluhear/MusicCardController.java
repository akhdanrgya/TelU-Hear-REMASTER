package com.tubes.teluhear;

import com.tubes.teluhear.database.MusicModel;
import com.tubes.teluhear.database.MusicDAO;
import com.tubes.teluhear.database.PlaylistMusicDAO;
import com.tubes.teluhear.database.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
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

    @FXML
    private Button deleteBtn;

    private MusicModel musicData;

    private MusicDAO musicDAO;

    private PlaylistMusicDAO playlistMusicDAO;

    private MusicCardClickListener clickListener;

    private List<MusicModel> musicList;

    private int playlistId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        musicDAO = new MusicDAO(dbConnection.getConnection());
        playlistMusicDAO = new PlaylistMusicDAO(dbConnection.getConnection());
    }

    public void setMusicData(MusicModel music, int index, boolean btn) {
        this.musicData = music;
        musicIndex.setText(index + ".");
        musicJudul.setText(music.getJudul());
        musicArtist.setText(music.getArtist());

        if (!btn) {
            deleteBtn.setVisible(false);
        }
    }

    public void setMusicList(List<MusicModel> musicList) {
        this.musicList = musicList;
    }

    public void setClickListener(MusicCardClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    @FXML
    private void onCardClicked(MouseEvent event) {

        if (clickListener != null) {
            clickListener.onMusicCardClicked(musicData, musicList);
        }

    }

    @FXML
    void delete(ActionEvent event) {
        playlistMusicDAO.deletePlaylistMusic(playlistId, musicData.getId());
    }

}
