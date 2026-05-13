package ee.anton.veebipood.controller;

import ee.anton.veebipood.dto.LoginDto;
import ee.anton.veebipood.entity.Person;
import ee.anton.veebipood.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final PersonRepository personRepository;

    @GetMapping("persons")
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @PostMapping("signup")
    public void signup(@RequestBody Person person) {
        personRepository.save(person);
    }

    @PostMapping("login")
    public String login(@RequestBody LoginDto person) {
        if (person.email() == null || person.password() == null) {
            throw new RuntimeException();
        }
        return "Edukalt sisselogitud";
    }

}
