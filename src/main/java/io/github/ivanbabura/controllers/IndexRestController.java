package io.github.ivanbabura.controllers;

import io.github.ivanbabura.entities.Person;
import io.github.ivanbabura.services.PersonServicesStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/sdba", produces = MediaType.APPLICATION_JSON_VALUE)
public class IndexRestController {
    private final PersonServicesStorage personServicesStorage;

    @Autowired
    public IndexRestController(PersonServicesStorage personServicesStorage) {
        this.personServicesStorage = personServicesStorage;
    }

    @PostMapping("/type")
    public ResponseEntity<String> selectedTypeOfJdbc(@RequestBody String nameOfType) {
        personServicesStorage.setTypeOfService(nameOfType);
        return new ResponseEntity<>(nameOfType, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Person> findById(@RequestParam() Integer id) {
        return (personServicesStorage.isNotNull())
                ? new ResponseEntity<>(personServicesStorage.getSelectedService().findById(id), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping()
    public ResponseEntity<String> saveName(@RequestBody() String name) {
        if (personServicesStorage.isNotNull()) {
            personServicesStorage.getSelectedService().save(name);
            return new ResponseEntity<>("Saved.", HttpStatus.CREATED);
        } else
            return new ResponseEntity<>("Service error.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity<String> update_name(@RequestBody() Person person){
        if (personServicesStorage.isNotNull()) {
            if (personServicesStorage.getSelectedService().update_name(person)) {
                return new ResponseEntity<>("Updated.", HttpStatus.OK);
            } else
                return new ResponseEntity<>("Not updated.", HttpStatus.OK);
        } else
            return new ResponseEntity<>("Service error.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteById(@RequestParam() Integer id) {
        if (personServicesStorage.isNotNull()){
            personServicesStorage.getSelectedService().delete(id);
            return new ResponseEntity<>("Deleted.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Service error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Person>> getAll() {
        return (personServicesStorage.isNotNull())
                ? new ResponseEntity<>(personServicesStorage.getSelectedService().getAll(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/like")
    public ResponseEntity<List<Person>> getAllLikeTemplate(@RequestParam() String startOfName) {
        return (personServicesStorage.isNotNull())
                ? new ResponseEntity<>(personServicesStorage.getSelectedService().getAllStartsWith(startOfName), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/likeNameOnly")
    public ResponseEntity<List<String>> getAllNamesStartsWith(@RequestParam() String startOfName) {
        return (personServicesStorage.isNotNull())
                ? new ResponseEntity<>(personServicesStorage.getSelectedService().getAllNamesStartsWith(startOfName), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
