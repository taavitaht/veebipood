package ee.taavi.veebipood.service;

import ee.taavi.veebipood.entity.Person;
import ee.taavi.veebipood.repository.PersonRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    @Autowired
    private PersonRepository personRepository;

    String superSecretKey = "k9MlU09IT5D5yrjFdEd8bu3j3tatD2Ycm2-cIz2oQmY";  // Keep in .env!!
    SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(superSecretKey));

    public String generateToken(Long personId){
        return Jwts
                .builder()
                .id(personId.toString())
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
        Long personId = Long.parseLong(claims.getId());
        return personRepository.findById(personId).orElseThrow();
    }
}
