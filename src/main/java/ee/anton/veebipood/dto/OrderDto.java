package ee.anton.veebipood.dto;

import ee.anton.veebipood.entity.Address;
import ee.anton.veebipood.entity.PersonRole;

import java.io.Serializable;
import java.util.List;

public record OrderDto(
        Long id,
        PersonDto person,
        List<OrderRowDto> orderRows,
        Boolean active,
        Address address,
        PersonRole role
) implements Serializable {
}
