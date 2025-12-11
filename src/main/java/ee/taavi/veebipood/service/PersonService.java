package ee.taavi.veebipood.service;

import ee.taavi.veebipood.entity.Person;
import ee.taavi.veebipood.model.LoginCredentials;
import ee.taavi.veebipood.model.PasswordCredentials;
import ee.taavi.veebipood.repository.PersonRepository;
import ee.taavi.veebipood.util.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtService jwtService;

    public Person savePerson(Person person) {
        if(person.getId() != null){
            throw new RuntimeException("Cannot register when id is present");
        }
        // == null - võti täielikult puudu, isBlank - võti puudu või võti olemas aga tühi väärtus (tühik)
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
        if(person.getPassword() == null || person.getPassword().isBlank()){
            throw new RuntimeException("Password cannot be empty");
        }

        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
        return personRepository.save(person);
    }


    public String getToken(LoginCredentials loginCredentials) {
        Person person = personRepository.findByEmail(loginCredentials.getEmail());
        if (person == null){
            throw new RuntimeException("Invalid email");
        }
        if (person.getPassword() == null){
            throw new RuntimeException("Missing password");
        }
        if (!bCryptPasswordEncoder.matches(loginCredentials.getPassword(), person.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        return jwtService.generateToken(person);
    }

    public Person changePassword(PasswordCredentials passwordCredentials) {
        Person existingPerson = personRepository.findById(passwordCredentials.getId()).orElseThrow();
        if(!bCryptPasswordEncoder.matches(passwordCredentials.getOldPassword(), existingPerson.getPassword())){
            throw new RuntimeException("Old password doesn't match");
        }
        existingPerson.setPassword(bCryptPasswordEncoder.encode(passwordCredentials.getNewPassword()));
        return personRepository.save(existingPerson);
    }
}
