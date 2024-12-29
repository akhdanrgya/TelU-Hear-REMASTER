package com.tubes.teluhear.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistMusicDAO {
    private Connection connection;

    public PlaylistMusicDAO(Connection connection) {
        this.connection = connection;
    }

    public List<PlaylistMusicModel> getPlaylistMusic(int playlist_id) {
        List<PlaylistMusicModel> playlistMusicList = new ArrayList<>();
        String sql = "SELECT * FROM playlist_music WHERE playlist_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()){
            while (rs.next()) {
                PlaylistMusicModel playlistMusic = new PlaylistMusicModel(
                        rs.getInt("id"),
                        rs.getInt("id_playlist"),
                        rs.getInt("id_music")
                );
                playlistMusicList.add(playlistMusic);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return playlistMusicList;
    }
}
