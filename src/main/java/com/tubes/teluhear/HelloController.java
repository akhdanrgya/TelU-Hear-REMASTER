package com.tubes.teluhear;

import com.tubes.teluhear.database.dbConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    public HelloController() {
        dbConnection.connect();
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

    }
}