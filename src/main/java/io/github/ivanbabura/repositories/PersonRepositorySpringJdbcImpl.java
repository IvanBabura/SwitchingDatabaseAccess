package io.github.ivanbabura.repositories;

import io.github.ivanbabura.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class PersonRepositorySpringJdbcImpl implements PersonRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonRepositorySpringJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Person findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM people WHERE id=?;", new Object[]{id}, new PersonRowMapper());
    }

    @Override
    public void save(String name) {
        jdbcTemplate.update("INSERT INTO people (name) VALUES (?);", name);
    }

    @Override
    public boolean update_name(Person person) {
        jdbcTemplate.update("UPDATE people SET name=? WHERE id=?;",person.getName(),person.getId());
        return true;
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM people WHERE id=?;", id);
    }

    @Override
    public List<Person> getAll() {
        return jdbcTemplate.query("SELECT * FROM people;", new PersonRowMapper());
    }

    @Override
    public List<Person> getAllStartsWith(String startOfName) {
        return jdbcTemplate.query("SELECT * FROM people WHERE name LIKE ?;",new PersonRowMapper(), startOfName + '%');
    }

    @Override
    public List<String> getAllNamesStartsWith(String startOfName) {
        return jdbcTemplate.query("SELECT name FROM people WHERE name LIKE ?;", new PersonNameRowMapper(), startOfName + '%');
    }
}