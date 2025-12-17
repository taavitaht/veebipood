package ee.taavi.veebipood.controller;

import ee.taavi.veebipood.dto.PersonDTO;
import ee.taavi.veebipood.entity.Person;
import ee.taavi.veebipood.entity.PersonRole;
import ee.taavi.veebipood.model.AuthToken;
import ee.taavi.veebipood.model.LoginCredentials;
import ee.taavi.veebipood.model.PasswordCredentials;
import ee.taavi.veebipood.repository.PersonRepository;
import ee.taavi.veebipood.service.JwtService;
import ee.taavi.veebipood.service.MailService;
import ee.taavi.veebipood.service.PersonService;
import ee.taavi.veebipood.util.Validations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    @Autowired
    private MailService mailService;

    @GetMapping("persons")
    public List<Person> getPersons() {
        return personRepository.findByOrderByIdAsc();
    }

    @Cacheable(value = "personCache", key = "#id")
    @GetMapping("person/{id}")
    public Person getPerson(@PathVariable Long id) {
        return personRepository.findById(id).orElseThrow();
    }

    @GetMapping("public-persons")
    public List<PersonDTO> getPublicPersons() {
        mailService.sendPlainText("taavi656@gmail.com", "Pealkiri", "Sisu");
        return List.of(modelMapper.map(personRepository.findAll(), PersonDTO[].class));
    }

    @PostMapping("signup")
    public Person signup(@RequestBody Person person) {
        return personService.savePerson(person);
    }

    @CachePut(value = "personCache", key = "#person.id")
    @PutMapping("persons")
    public Person editPerson(@RequestBody Person person) {
        if (person.getId() == null) {
            throw new RuntimeException("Cannot edit when id is missing");
        }

        if (person.getEmail() == null || person.getEmail().isBlank()) {
            throw new RuntimeException("Email cannot be empty");
        }
        if (!Validations.validateEmail(person.getEmail())) {
            throw new RuntimeException("Email is not valid");
        }

        Person existingPerson = personRepository.findById(person.getId()).orElseThrow();
        Person dbPerson = personRepository.findByEmail(person.getEmail());
        if (!existingPerson.getEmail().equals(person.getEmail()) && dbPerson != null) {
            throw new RuntimeException("Email already taken");
        }
        person.setPassword(existingPerson.getPassword());
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
    public Person getPerson() {
        Long personId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return personRepository.findById(personId).orElseThrow();
    }

    @PatchMapping("update-password")
    public Person updatePassword(@RequestBody PasswordCredentials passwordCredentials) {
        if (passwordCredentials.getId() == null) {
            throw new RuntimeException("Cannot edit when id is missing");
        }
        if (passwordCredentials.getOldPassword() == null) {
            throw new RuntimeException("Cannot edit when old password is missing");
        }
        if (passwordCredentials.getNewPassword() == null) {
            throw new RuntimeException("Cannot edit when new password is missing");
        }
        return personService.changePassword(passwordCredentials);
    }

    @PatchMapping("/change-admin")
    public List<Person> changeAdmin(@RequestParam Long id){
        Person person = personRepository.findById(id).orElseThrow();
        if(person.getRole().equals(PersonRole.CUSTOMER)){
            person.setRole(PersonRole.ADMIN);
        } else {
            person.setRole(PersonRole.CUSTOMER);
        }
        personRepository.save(person);
        return personRepository.findByOrderByIdAsc();
    }
}
