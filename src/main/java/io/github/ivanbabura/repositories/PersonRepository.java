package io.github.ivanbabura.repositories;

import io.github.ivanbabura.entities.Person;

import java.util.List;

public interface PersonRepository  {
    Person findById(int id);
    void save(String name);
    boolean update_name(Person person);
    void delete(int id);
    List<Person> getAll();
    List<Person> getAllStartsWith(String startOfName);
    List<String> getAllNamesStartsWith(String startOfName);

    // long count();
    // Person findByName(String name);
    // void save(Person person);
    // void delete(Person person);
}
