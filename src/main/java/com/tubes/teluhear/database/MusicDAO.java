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

        try(PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                MusicModel music = new MusicModel(
                        rs.getInt("id"),
                        rs.getString("judul"),
                        rs.getString("artist"),
                        rs.getString("genre"),
                        rs.getString("duration")
                );
                musicList.add(music);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(musicList);
        return musicList;
    }
}
