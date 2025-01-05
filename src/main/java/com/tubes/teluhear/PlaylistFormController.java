package com.tubes.teluhear;

import com.tubes.teluhear.database.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.tubes.teluhear.database.PlaylistDAO;
import com.tubes.teluhear.database.PlaylistModel;

import java.io.File;

public class PlaylistFormController {

    @FXML
    private TextField inputJudul;

    private String inputImagePath;
    private String judul;
    private int userId;

    private PlaylistDAO playlistDAO;

    public PlaylistFormController() {
        this.playlistDAO = new PlaylistDAO(dbConnection.getConnection());
    }


    @FXML
    void submit(ActionEvent event) {
        judul = inputJudul.getText();
        userId = SessionManager.getInstance().getId();

        PlaylistModel playlist = new PlaylistModel(userId, judul, inputImagePath, 0);

        playlistDAO.addPlaylist(playlist);

    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            try {
                String resourcePath = "src/main/resources/image/";
                File resourceFolder = new File(resourcePath);

                if (!resourceFolder.exists()) {
                    resourceFolder.mkdirs();
                }

                String sanitizedFileName = selectedFile.getName().replace(" ", "_");

                File destinationFile = new File(resourcePath + sanitizedFileName);

                java.nio.file.Files.copy(
                        selectedFile.toPath(),
                        destinationFile.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                );

                inputImagePath = "/image/" + sanitizedFileName;
                System.out.println("File copied to: " + inputImagePath);

            } catch (Exception e) {
                System.out.println("Error copying file: " + e.getMessage());
            }
        } else {
            System.out.println("No file selected");
        }
    }


}
