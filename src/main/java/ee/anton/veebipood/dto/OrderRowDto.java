package ee.anton.veebipood.dto;

import java.io.Serializable;

public record OrderRowDto(
        Long id,
        Integer quantity
) implements Serializable {
}
