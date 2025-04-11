package io.github.ivanbabura.repositories;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonNameRowMapper implements RowMapper<String> {
    public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getString("name");
    }
}
