package ee.taavi.veebipood.controller;

import ee.taavi.veebipood.dto.PersonDTO;
import ee.taavi.veebipood.entity.Person;
import ee.taavi.veebipood.model.AuthToken;
import ee.taavi.veebipood.model.LoginCredentials;
import ee.taavi.veebipood.repository.PersonRepository;
import ee.taavi.veebipood.service.JwtService;
import ee.taavi.veebipood.service.PersonService;
import ee.taavi.veebipood.util.Validations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private PersonService personService;

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
        return personService.savePerson(person);
    }


    @PutMapping("persons")
    public Person editPerson(@RequestBody Person person){
        if(person.getId() == null){
            throw new RuntimeException("Cannot edit when id is missing");
        }

        if(person.getEmail() == null || person.getEmail().isBlank()){
            throw new RuntimeException("Email cannot be empty");
        }
        if(!Validations.validateEmail(person.getEmail())){
            throw new RuntimeException("Email is not valid");
        }
        Person dbPerson = personRepository.findByEmail(person.getEmail());
        if(dbPerson != null){
            throw new RuntimeException("Email already taken");
        }

//        if(person.getPassword() == null || person.getPassword().isBlank()){
//            throw new RuntimeException("Password cannot be empty");
//        }
        return personRepository.save(person);
    }

    @PostMapping("login")
    public AuthToken login(@RequestBody LoginCredentials loginCredentials) {
        AuthToken authToken = new AuthToken();
        authToken.setToken(personService.getToken(loginCredentials));
        return authToken;
    }

    @GetMapping("person")
    public Person getPerson(){
        Long personId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return personRepository.findById(personId).orElseThrow();
    }
}
