package com.tubes.teluhear.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {
    private Connection connection;

    public PlaylistDAO(Connection connection) {
        this.connection = connection;
    }

    public List<PlaylistModel> getPlaylist() {
        List<PlaylistModel> playlistList = new ArrayList<>();
        String sql = "SELECT * FROM playlist";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PlaylistModel playlist = new PlaylistModel(
                        rs.getInt("id_user"),
                        rs.getString("playlist_name"),
                        rs.getString("image")
                );
                playlistList.add(playlist);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return playlistList;
    }
}
