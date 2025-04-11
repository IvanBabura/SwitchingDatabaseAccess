package io.github.ivanbabura.services;

import io.github.ivanbabura.entities.Person;
import io.github.ivanbabura.repositories.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {
    @Mock
    private PersonRepository repository;
    @InjectMocks
    private PersonServiceImpl service;
    private final Person PERSON_1 = new Person();
    private final Person PERSON_2 = new Person();


    @BeforeEach
    public void resetPersons(){
        PERSON_1.setId(1);
        PERSON_1.setName("Bob");
        PERSON_2.setId(2);
        PERSON_2.setName("Anna");
    }

    @Test
    public void testFindById() {
        Mockito.when(repository.findById(Mockito.any(int.class))).thenReturn(Stream.of(PERSON_1, PERSON_2)
                .filter(x -> x.getId().equals(PERSON_2.getId()))
                .findFirst()
                .orElse(null));
        assertEquals(PERSON_2, service.findById(PERSON_2.getId()));

        Mockito.when(repository.findById(PERSON_2.getId())).thenReturn(PERSON_2);
        Person result = service.findById(PERSON_2.getId());
        assertNotNull(result);
        assertEquals(PERSON_2, result);
    }

    @Test
    public void testSave() {
        service.save("John");
        Mockito.verify(repository, Mockito.times(1)).save("John");
    }

    @Test
    public void testUpdate() {
        int index = PERSON_2.getId();
        Person person_update = new Person();
        person_update.setId(index);
        person_update.setName("Mary");
        Mockito.when(repository.update_name(Mockito.any(Person.class)))
                .thenReturn(Stream.of(PERSON_1, PERSON_2)
                        .anyMatch(y -> y.getId().equals(index))
                );
        assertTrue(service.update_name(person_update));
        assertTrue(service.update_name(PERSON_1));
    }

    @Test
    public void testDelete() {
        service.delete(2);
        Mockito.verify(repository, Mockito.times(1)).delete(2);
    }

    @Test
    public void testGetAll() {
        List<Person> list = new ArrayList<>();
        list.add(PERSON_1);
        list.add(PERSON_2);
        Mockito.when(repository.getAll()).thenReturn(Stream.of(PERSON_1, PERSON_2).collect(Collectors.toList()));
        assertEquals(list, service.getAll());
    }


    @Test
    void getAllStartsWith() {
        Person person_3 = new Person();
        person_3.setName("Boss");
        String str = "Bo";
        List<Person> list = new ArrayList<>();
        list.add(PERSON_1);
        list.add(person_3);
        Mockito.when(repository.getAllStartsWith(str)).thenReturn(Stream.of(PERSON_1, PERSON_2,person_3)
                .filter(x -> x.getName().startsWith(str))
                .collect(Collectors.toList()));
        List<Person> gettinList = service.getAllStartsWith(str);
        assertEquals(list, gettinList);
        assertEquals(2, gettinList.size());
    }

    @Test
    void getAllNamesStartsWith() {
        Person person_3 = new Person();
        person_3.setName("Boss");
        String str = "Bo";
        List<String> list = new ArrayList<>();
        list.add(PERSON_1.getName());
        list.add(person_3.getName());
        Mockito.when(repository.getAllNamesStartsWith(str)).thenReturn(Stream.of(PERSON_1, PERSON_2,person_3)
                .map(Person::getName)
                .filter(name -> name.startsWith(str))
                .collect(Collectors.toList()));
        List<String> gettinList = service.getAllNamesStartsWith(str);
        assertEquals(list, gettinList);
        assertEquals(2, gettinList.size());
    }
}