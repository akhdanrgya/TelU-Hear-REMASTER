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
                        rs.getInt("id")
                );
                playlistList.add(playlist);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return playlistList;
    }

    public boolean addPlaylist(PlaylistModel playlist) {
        String sql = "INSERT INTO playlist (id_user, playlist_name) VALUES (?, ?, )";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playlist.getUser_id());
            stmt.setString(2, playlist.getPlaylist_name());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding playlist: " + e.getMessage());
        }
        return false;
    }

    public boolean deletePlaylist(int id) {
        String sql = "DELETE FROM playlist WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting playlist: " + e.getMessage());
            return false;
        }
    }

    public List<PlaylistModel> getPlaylistByUser(int userId) {
        List<PlaylistModel> playlistList = new ArrayList<>();
        String sql = "SELECT * FROM playlist WHERE id_user = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PlaylistModel playlist = new PlaylistModel(
                            rs.getInt("id_user"),
                            rs.getString("playlist_name"),
                            rs.getInt("id")
                    );
                    playlistList.add(playlist);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching playlists by user: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return playlistList;
    }

    public boolean updatePlaylist(int id, String playlist_name) {
        String sql = "UPDATE playlist SET playlist_name = ?  WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, playlist_name);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating playlist: " + e.getMessage());
            return false;
        }
    }


}
