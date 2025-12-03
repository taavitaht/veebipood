package ee.taavi.veebipood.controller;

import ee.taavi.veebipood.dto.PersonDTO;
import ee.taavi.veebipood.entity.Person;
import ee.taavi.veebipood.model.LoginCredentials;
import ee.taavi.veebipood.repository.PersonRepository;
import ee.taavi.veebipood.service.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin("http://localhost:5173")
@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private JwtService jwtService;

    @GetMapping("persons")
    public List<Person> getPersons(){
        return personRepository.findAll();
    }

    @GetMapping("public-persons")
    public List<PersonDTO> getPublicPersons(){
        return List.of(modelMapper.map(personRepository.findAll(), PersonDTO[].class));
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

    @PostMapping("login")
    public String login(@RequestBody LoginCredentials loginCredentials) {
        Person person = personRepository.findByEmail(loginCredentials.getEmail());
        if (person == null){
            throw new RuntimeException("Invalid email");
        }
        return jwtService.generateToken(person.getId());
    }
}
