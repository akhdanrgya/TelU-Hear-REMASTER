package com.tubes.teluhear;

import com.tubes.teluhear.database.dbConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import com.tubes.teluhear.database.MusicModel;
import com.tubes.teluhear.database.MusicDAO;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MusicController implements Initializable {

    @FXML
    private GridPane musicGrid;

    private MusicDAO musicDAO;

    @FXML
    private Slider musicSlider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        musicDAO = new MusicDAO(dbConnection.getConnection());

        List<MusicModel> musicDataList = musicDAO.getAllMusic();

        populateMusicGrid(musicDataList);

        System.out.println("MusicController initialized.");
    }

    private void populateMusicGrid(List<MusicModel> musicDataList) {
        if (musicDataList == null || musicDataList.isEmpty()) {
            System.out.println("No music data found.");
            return;
        }

        for (int i = 0; i < musicDataList.size(); i++) {
            MusicModel music = musicDataList.get(i);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tubes/teluhear/MusicCard.fxml"));
                Pane musicCardView = loader.load();

                MusicCardController controller = loader.getController();
                controller.setMusicData(music, i+1);

                musicGrid.add(musicCardView, 0, i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void playButton(ActionEvent event) {
        System.out.println("playButton clicked");
    }

    @FXML
    void nextButton(ActionEvent event) {
        System.out.println("nextButton clicked");
    }

    @FXML
    void previousButton(ActionEvent event) {
        System.out.println("previousButton clicked");
    }

}
