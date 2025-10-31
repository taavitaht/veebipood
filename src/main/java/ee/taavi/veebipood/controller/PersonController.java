package ee.taavi.veebipood.controller;

import ee.taavi.veebipood.entity.Person;
import ee.taavi.veebipood.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("persons")
    public List<Person> getPersons(){
        return personRepository.findAll();
    }

    @PostMapping("signup")
    public Person signup(@RequestBody Person person){
        if(person.getId() != null){
            throw new RuntimeException("Cannot register when id is present");
        }
        if(person.getEmail() == null || person.getEmail().isEmpty()){
            throw new RuntimeException("Email cannot be empty");
        }
        if(person.getPassword() == null || person.getPassword().isEmpty()){
            throw new RuntimeException("Password cannot be empty");
        }
        return personRepository.save(person);
    }

    @PutMapping("persons")
    public Person editPerson(@RequestBody Person person){
        if(person.getId() == null){
            throw new RuntimeException("Cannot edit when id is missing");
        }
        if(person.getEmail() == null || person.getEmail().isEmpty()){
            throw new RuntimeException("Email cannot be empty");
        }
        if(person.getPassword() == null || person.getPassword().isEmpty()){
            throw new RuntimeException("Password cannot be empty");
        }
        return personRepository.save(person);
    }
}
