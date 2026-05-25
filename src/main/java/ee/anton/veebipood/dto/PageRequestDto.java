package ee.anton.veebipood.dto;

import java.io.Serializable;

public record PageRequestDto(
        int page,
        int size
) implements Serializable {
}
