package ee.taavi.veebipood.service;

import ee.taavi.veebipood.entity.Person;
import ee.taavi.veebipood.entity.PersonRole;
import ee.taavi.veebipood.repository.PersonRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    @Autowired
    private PersonRepository personRepository;

    String superSecretKey = "k9MlU09IT5D5yrjFdEd8bu3j3tatD2Ycm2-cIz2oQmY";  // Keep in .env!!
    SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(superSecretKey));

    public String generateToken(Person person){
        return Jwts
                .builder()
                .id(person.getId().toString())
                .issuer(person.getRole().toString())
                .subject(person.getFirstName() + " " + person.getLastName())
                .expiration(Date.from(Instant.now().plus(Duration.ofHours(2))))
                .signWith(secretKey)
                .compact();
    }

    public Person getPersonByToken(String token){
        Claims claims = Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseClaimsJws(token)
                .getPayload();
//        Long personId = Long.parseLong(claims.getId());
//        return personRepository.findById(personId).orElseThrow();
        Person person = new Person();
        person.setId(Long.parseLong(claims.getId()));
        person.setRole(PersonRole.valueOf(claims.getIssuer()));
        person.setFirstName(claims.getSubject().split(" ")[0]);
        person.setLastName(claims.getSubject().split(" ")[1]);
        return person;
    }
}
