package ee.anton.veebipood.dto;

import java.io.Serializable;

public record PersonDto(
        String firstName,
        String lastName,
        String email,
        String password,
        String personalCode
) implements Serializable {
}
