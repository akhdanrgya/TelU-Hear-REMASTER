package com.tubes.teluhear;

import com.tubes.teluhear.database.dbConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import com.tubes.teluhear.database.MusicModel;
import com.tubes.teluhear.MusicCardController;
import com.tubes.teluhear.database.MusicDAO;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class MusicController implements Initializable {

    @FXML
    private GridPane musicGrid;

    private MusicDAO musicDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Pastikan koneksi berhasil
//        Connection connection = dbConnection.getConnection();
//        if (connection == null) {
//            System.err.println("Koneksi gagal!");
//            return;
//        }

        // Inisialisasi MusicDAO dengan koneksi
        musicDAO = new MusicDAO(dbConnection.getConnection());

        // Ambil data musik
        List<MusicModel> musicDataList = musicDAO.getAllMusic();

        // Populasi GridPane dengan data musik
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

            // Buat MusicCardController untuk setiap musik
            MusicCardController musicCard = new MusicCardController();
            musicCard.setMusicData(music);

            System.out.println(music);

            // Tambahkan MusicCard ke GridPane
            musicGrid.add(musicCard.getView(), i % 3, i / 3);
        }
    }
}
