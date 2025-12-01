package ee.taavi.veebipood.service;

import ee.taavi.veebipood.entity.Person;
import ee.taavi.veebipood.repository.PersonRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Autowired
    private PersonRepository personRepository;

    public String generateToken(Long id){
        String token = Jwts.builder().compact();
        return token;
    }

    public Person getPersonByToken(String token){
        return personRepository.findById(1L).orElseThrow();
    }
}
