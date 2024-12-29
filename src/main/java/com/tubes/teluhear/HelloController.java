package com.tubes.teluhear;

import com.tubes.teluhear.database.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class HelloController {
    @FXML
    private Label welcomeText;

    private UserDAO UserDAO;

    private MusicDAO MusicDAO;

    private PlaylistDAO PlaylistDAO;

    public HelloController() {
        dbConnection.connect();
        this.UserDAO = new UserDAO(dbConnection.getConnection());
        this.MusicDAO = new MusicDAO(dbConnection.getConnection());
        this.PlaylistDAO = new PlaylistDAO(dbConnection.getConnection());
        System.out.println("Init");
    }

    @FXML
    protected void onHelloButtonClick() throws SQLException {
        welcomeText.setText("Welcome to JavaFX Application!");


    }
}