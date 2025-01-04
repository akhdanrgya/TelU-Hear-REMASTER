package com.tubes.teluhear.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SubsDAO {
    private Connection connection;

    public SubsDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean addSubs(int idUser) {
        String sql = "INSERT INTO subscription (id_user) VALUES (?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUser);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding subscription: " + e.getMessage());
        }
        return false;
    }

    public boolean checkSubs(int idUser) {
        String sql = "SELECT * FROM subscription WHERE id_user = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUser);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            System.out.println("Error checking subscription: " + e.getMessage());
        }
        return false;
    }


}
