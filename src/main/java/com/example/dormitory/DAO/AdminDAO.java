package com.example.dormitory.DAO;

import com.example.dormitory.models.Admin;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdminDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/groups";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "sasa310705";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Admin> index() {
        List<Admin> admins = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM admins";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Admin admin = new Admin();

                admin.setPassword(resultSet.getString("password"));

                admins.add(admin);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return admins;
    }
}
