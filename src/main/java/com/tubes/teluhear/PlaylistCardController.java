package com.tubes.teluhear;

import com.tubes.teluhear.database.PlaylistModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistCardController {

    @FXML
    private ImageView imagePlaylist;

    @FXML
    private Label judulPlaylist;


    public void setPlaylistData(PlaylistModel playlist) {
        judulPlaylist.setText(playlist.getPlaylist_name());

        String imagePath = playlist.getImage();

        Image image = new Image(getClass().getResourceAsStream(imagePath));
        imagePlaylist.setImage(image);
    }

}
