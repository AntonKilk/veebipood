package ee.anton.veebipood.mapper;

import ee.anton.veebipood.dto.OrderDto;
import ee.anton.veebipood.dto.OrderRowDto;
import ee.anton.veebipood.dto.PersonDto;
import ee.anton.veebipood.entity.Order;
import ee.anton.veebipood.entity.OrderRow;
import ee.anton.veebipood.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "person.address", target = "address")
    @Mapping(source = "person.role", target = "role")
    OrderDto toDto(Order order);

    List<OrderDto> toDtoList(List<Order> orders);

    PersonDto toPersonDto(Person person);

    OrderRowDto toOrderRowDto(OrderRow orderRow);
}
