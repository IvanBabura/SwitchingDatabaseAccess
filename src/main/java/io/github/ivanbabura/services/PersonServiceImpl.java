package io.github.ivanbabura.services;

import io.github.ivanbabura.entities.Person;
import io.github.ivanbabura.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person findById(int id) {
        return personRepository.findById(id);
    }

    @Override
    public void save(String name) {
        personRepository.save(name);
    }

    @Override
    public boolean update_name(Person person) {
        return personRepository.update_name(person);
    }

    @Override
    public void delete(int id) {
        personRepository.delete(id);
    }

    @Override
    public List<Person> getAll() {
        return personRepository.getAll();
    }

    @Override
    public List<Person> getAllStartsWith(String startOfName) {
        return personRepository.getAllStartsWith(startOfName);
    }

    @Override
    public List<String> getAllNamesStartsWith(String startOfName) {
        return personRepository.getAllNamesStartsWith(startOfName);
    }
}
