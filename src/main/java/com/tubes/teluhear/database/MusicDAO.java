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
                        resultSet.getString("genre"),
                        resultSet.getString("duration")
                );
                musicList.add(music);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return musicList;
    }
}
