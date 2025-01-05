package com.tubes.teluhear;

import com.tubes.teluhear.database.dbConnection;
import com.tubes.teluhear.database.PlaylistDAO;
import com.tubes.teluhear.database.PlaylistModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class PlaylistFormController {

    @FXML
    private TextField inputJudul;

    @FXML
    private ImageView gambarPlaylist;

    private String inputImagePath;
    private String judul;
    private int userId;

    private PlaylistDAO playlistDAO;

    private PlaylistController playlistController;

    public PlaylistFormController() {
        this.playlistDAO = new PlaylistDAO(dbConnection.getConnection());
    }

    @FXML
    void submit(ActionEvent event) throws IOException {
        judul = inputJudul.getText();
        userId = SessionManager.getInstance().getId();

        if (judul == null || judul.isEmpty() || inputImagePath == null || inputImagePath.isEmpty()) {
            System.out.println("Judul atau gambar tidak boleh kosong");
            return;
        }

        PlaylistModel playlist = new PlaylistModel(userId, judul, inputImagePath, 0);

        if (playlistDAO.addPlaylist(playlist)) {
            System.out.println("Playlist berhasil ditambahkan");
        } else {
            System.out.println("Gagal menambahkan playlist");
        }

        Stage stage = (Stage) inputJudul.getScene().getWindow();
        stage.close();
        playlistController.reloadPlaylist();
    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        Window window = inputJudul.getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(window);

        if (selectedFile != null) {
            try {
                String resourcePath = "src/main/resources/image/";
                File resourceFolder = new File(resourcePath);

                if (!resourceFolder.exists() && !resourceFolder.mkdirs()) {
                    throw new IOException("Gagal membuat folder: " + resourceFolder.getAbsolutePath());
                }

                String sanitizedFileName = selectedFile.getName().replaceAll("[^a-zA-Z0-9._-]", "_");

                File destinationFile = new File(resourceFolder, sanitizedFileName);

                Files.copy(
                        selectedFile.toPath(),
                        destinationFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );

                inputImagePath = "/image/" + sanitizedFileName;

                String imagePath = destinationFile.toURI().toString();
                gambarPlaylist.setImage(new Image(imagePath));

                System.out.println("File berhasil diupload: " + inputImagePath);

            } catch (IOException e) {
                System.err.println("Error copying file: " + e.getMessage());
            }
        } else {
            System.out.println("Tidak ada file yang dipilih");
        }
    }

}
