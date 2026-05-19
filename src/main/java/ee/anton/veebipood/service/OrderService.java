package ee.anton.veebipood.service;

import ee.anton.veebipood.entity.Order;
import ee.anton.veebipood.entity.OrderRow;
import ee.anton.veebipood.entity.Person;
import ee.anton.veebipood.repository.OrderRepository;
import ee.anton.veebipood.repository.PersonRepository;
import ee.anton.veebipood.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PersonRepository personRepository;
    private final ProductRepository productRepository;

    public Order saveOrder(List<OrderRow> orderRows) {
        Order order = new Order();
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("Anton");
        order.setPerson(person); //TODO: Add after auth
        order.setOrderRows(orderRows);
        order.setActive(true);
        Double total = orderRows
                .stream()
                .map(or -> {
                    OrderRow orderRow = new OrderRow();
                    orderRow.setProduct((productRepository.findById(or.getProduct()
                            .getId())).orElseThrow());
                    orderRow.setQuantity(or.getQuantity());
                    return orderRow;
                })
                .mapToDouble(or -> or.getQuantity() * or.getProduct().getPrice())
                .sum();
        order.setTotal(total);
        return orderRepository.save(order);
    }
}
