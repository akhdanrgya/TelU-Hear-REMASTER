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
        String sql = "SELECT * FROM playlist_music WHERE id_playlist = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playlist_id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PlaylistMusicModel playlistMusic = new PlaylistMusicModel(
                            rs.getInt("id"),
                            rs.getInt("id_playlist"),
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


    public boolean addPlaylistMusic(int idPlaylist, int idMusic) {
        String sql = "INSERT INTO playlist_music (id_playlist, id_music) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPlaylist);
            stmt.setInt(2, idMusic);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding playlist music: " + e.getMessage());
        }
        return false;
    }

    public boolean deletePlaylistMusic(int idPlaylist, int idMusic) {
        String sql = "DELETE FROM playlist_music WHERE id_playlist = ? AND id_music = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPlaylist);
            stmt.setInt(2, idMusic);
            System.out.println("Deleting playlist music with id_playlist: " + idPlaylist + " and id_music: " + idMusic);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting playlist music: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteAllPlaylistMusic(int idPlaylist) {
        String sql = "DELETE FROM playlist_music WHERE id_playlist = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPlaylist);
            System.out.println("Deleting playlist music with id_playlist: " + idPlaylist);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting playlist music: " + e.getMessage());
            return false;
        }
    }


}
