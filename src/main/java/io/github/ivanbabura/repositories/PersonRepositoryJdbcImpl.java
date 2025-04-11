package io.github.ivanbabura.repositories;

import io.github.ivanbabura.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepositoryJdbcImpl implements PersonRepository {
    private final Connection connection;

    @Autowired
    @Lazy
    public PersonRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Person findById(int id) {
        try {
            String queryFindBiId = "SELECT * FROM people WHERE id = (?);";
            PreparedStatement preparedStatement = connection.prepareStatement(queryFindBiId);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                return null;
            Person person = new Person();
            person.setId(resultSet.getInt(1));
            person.setName(resultSet.getString(2));
            return person;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(String name) {
        try {
            String queryInsert = "INSERT INTO people (name) values (?);";
            PreparedStatement preparedStatement = connection.prepareStatement(queryInsert);
            preparedStatement.setString(1, name);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update_name(Person person) {
        try {
            String queryUpdate = "UPDATE people SET name = ? WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(queryUpdate);
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            String queryDelete = "DELETE FROM people WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(queryDelete);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Person> getAll() {
        try (Statement statement = connection.createStatement()) {
            String querySelectAll = "SELECT * FROM people;";
            ResultSet resultSet = statement.executeQuery(querySelectAll);
            return getListFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Person> getAllStartsWith(String startOfName) {
        String querySelectAllLike = "SELECT * FROM people WHERE name LIKE ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(querySelectAllLike)) {
            preparedStatement.setString(1, startOfName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            return getListFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Person> getListFromResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Person> list = new ArrayList<>();
        while(resultSet.next()){
            Person p = new Person();
            p.setId(resultSet.getInt(1));
            p.setName(resultSet.getString(2));
            list.add(p);
        }
        return list;
    }

    @Override
    public List<String> getAllNamesStartsWith(String startOfName) {
        String querySelectAllLike = "SELECT name FROM people WHERE name LIKE ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(querySelectAllLike)) {
            preparedStatement.setString(1, startOfName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<String> list = new ArrayList<>();
            while(resultSet.next()){
                list.add(resultSet.getString(1));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
