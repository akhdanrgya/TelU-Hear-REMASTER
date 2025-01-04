package com.tubes.teluhear;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class PlaylistFormController {

    private String inputImagePath;

    @FXML
    private TextField inputJudul;

    @FXML
    void submit(ActionEvent event) {
        // Implementasi untuk submit data playlist
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

                File destinationFile = new File(resourcePath + selectedFile.getName());

                java.nio.file.Files.copy(
                        selectedFile.toPath(),
                        destinationFile.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                );

                inputImagePath = "/image/" + selectedFile.getName();
                System.out.println("File copied to: " + inputImagePath);

            } catch (Exception e) {
                System.out.println("Error copying file: " + e.getMessage());
            }
        } else {
            System.out.println("No file selected");
        }
    }

}
