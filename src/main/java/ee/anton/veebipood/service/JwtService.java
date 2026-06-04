package ee.anton.veebipood.service;

import ee.anton.veebipood.entity.Person;
import ee.anton.veebipood.repository.PersonRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static java.lang.Long.parseLong;

@Component
public class JwtService {
    private final String base64string = "cXVpZXRseXRlbGxtZWF0YXJlaW1hZ2luZWJyZWFrZmFzdHBvc2l0aW9uY2FtcHNoaW4";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(base64string));
    private final PersonRepository personRepository;

    public JwtService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person parseToken(String token) {
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        Long id = parseLong(claims.getId());
        return personRepository.findById(id).orElseThrow();
    }

    public String generateToken(Person person) {
        Date today = new Date();
        Date expiryDate = new Date(today.getTime() + 20 * 60 * 1000);

        String token = Jwts.builder()
                .signWith(secretKey)
                .id(person.getId().toString())
                .expiration(expiryDate)
                .compact();
        return token;
    }
}
