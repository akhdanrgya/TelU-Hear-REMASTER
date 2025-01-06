package com.tubes.teluhear.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MusicDAO {
    private Connection connection;

    public MusicDAO(Connection connection) {
        this.connection = connection;
    }

    public List<MusicModel> getAllMusic() {
        List<MusicModel> musicList = new ArrayList<>();
        String sql = "SELECT * FROM music";

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MusicModel music = new MusicModel(
                        rs.getInt("id"),
                        rs.getString("judul"),
                        rs.getString("artist"),
                        rs.getString("file_path")
                );
                musicList.add(music);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(musicList);
        return musicList;
    }

    public List<MusicModel> getMusicByIds(List<Integer> ids) {
        List<MusicModel> musicList = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM music WHERE id IN (");
        for (int i = 0; i < ids.size(); i++) {
            queryBuilder.append("?");
            if (i < ids.size() - 1) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(")");

        String query = queryBuilder.toString();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < ids.size(); i++) {
                stmt.setInt(i + 1, ids.get(i));
            }

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                MusicModel music = new MusicModel(
                        resultSet.getInt("id"),
                        resultSet.getString("judul"),
                        resultSet.getString("artist"),
                        resultSet.getString("file_path")
                );
                musicList.add(music);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return musicList;
    }

    public boolean addMusic(MusicModel music) {
        String sql = "INSERT INTO music (judul, artist, file_path) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, music.getJudul());
            stmt.setString(2, music.getArtist());
            stmt.setString(3, music.getFile_path());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding music: " + e.getMessage());
            return false;
        }
    }

    public int getMusicIdByJudul(String judul) {
        String sql = "SELECT id FROM music WHERE judul = ?";
        int musicId = -1;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, judul);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                musicId = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching music id: " + e.getMessage());
        }

        return musicId;
    }


}
