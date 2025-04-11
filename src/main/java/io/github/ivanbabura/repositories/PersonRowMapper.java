package io.github.ivanbabura.repositories;

import io.github.ivanbabura.entities.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Person p = new Person();
        p.setId(resultSet.getInt("id"));
        p.setName(resultSet.getString("name"));
        return p;
    }
}