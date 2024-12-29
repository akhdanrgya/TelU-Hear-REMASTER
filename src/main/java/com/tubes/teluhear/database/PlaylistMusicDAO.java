package com.tubes.teluhear.database;

import com.mysql.cj.x.protobuf.MysqlxPrepare;

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

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playlist_id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PlaylistMusicModel playlistMusic = new PlaylistMusicModel(
                            rs.getInt("id"),
                            rs.getInt("playlist_id"),
                            rs.getInt("id_music")
                    );
                    playlistMusicList.add(playlistMusic);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return playlistMusicList;
    }


    public boolean addPlaylistMusic(PlaylistMusicModel playlistMusic) {
        String sql = "INSERT INTO playlist_music (id_playlist, id_music) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playlistMusic.getIdPlaylist());
            stmt.setInt(2, playlistMusic.getIdMusic());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding playlist music: " + e.getMessage());
        }
        return false;
    }

    public boolean deletePlaylistMusic(int id) {
        String sql = "DELETE FROM playlist_music WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting playlist music: " + e.getMessage());
            return false;
        }
    }
}
