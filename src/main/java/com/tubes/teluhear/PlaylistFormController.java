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

//        tutup halaman form disini ...
        // Menutup halaman (stage)
        Stage stage = (Stage) inputJudul.getScene().getWindow();
        stage.close(); // Menutup jendela setelah submit
        playlistController.reloadPlaylist();
    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Filter hanya untuk file gambar
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Ambil window dari event
        Window window = inputJudul.getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(window);

        if (selectedFile != null) {
            try {
                // Path folder penyimpanan gambar
                String resourcePath = "src/main/resources/image/";
                File resourceFolder = new File(resourcePath);

                if (!resourceFolder.exists() && !resourceFolder.mkdirs()) {
                    throw new IOException("Gagal membuat folder: " + resourceFolder.getAbsolutePath());
                }

                // Sanitasi nama file
                String sanitizedFileName = selectedFile.getName().replaceAll("[^a-zA-Z0-9._-]", "_");

                // Lokasi file tujuan
                File destinationFile = new File(resourceFolder, sanitizedFileName);

                // Salin file
                Files.copy(
                        selectedFile.toPath(),
                        destinationFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING
                );

                // Simpan path relatif untuk database
                inputImagePath = "/image/" + sanitizedFileName;

                // Tampilkan gambar ke ImageView
                String imagePath = destinationFile.toURI().toString(); // Konversi ke URI agar bisa dibaca ImageView
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
