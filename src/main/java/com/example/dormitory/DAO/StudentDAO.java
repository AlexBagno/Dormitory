package com.example.dormitory.DAO;

import com.example.dormitory.models.Student;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class StudentDAO {
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


    public List<Student> index() {
        List<Student> students = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM students";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Student student = new Student();

                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("firstName"));
                student.setLastName(resultSet.getString("lastName"));
                student.setPoints(resultSet.getDouble("points"));
                student.setPriority(resultSet.getString("priority"));

                students.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students.stream().sorted((o1, o2) -> {

            if (o1.getPoints() - o2.getPoints() > 0)
                return -1;
            else if (o1.getPoints() == o2.getPoints())
                return 0;
            else
                return 1;
        }).collect(Collectors.toList());
    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM students WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
