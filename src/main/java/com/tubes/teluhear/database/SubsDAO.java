package com.tubes.teluhear.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubsDAO {
    private Connection connection;

    public SubsDAO(Connection connection) {
        this.connection = connection;
    }

}
