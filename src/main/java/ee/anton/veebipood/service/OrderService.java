package ee.anton.veebipood.service;

import ee.anton.veebipood.dto.OrderDto;
import ee.anton.veebipood.entity.Order;
import ee.anton.veebipood.entity.OrderRow;
import ee.anton.veebipood.entity.Person;
import ee.anton.veebipood.entity.Product;
import ee.anton.veebipood.mapper.OrderMapper;
import ee.anton.veebipood.repository.OrderRepository;
import ee.anton.veebipood.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

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
                    Product product = productRepository.findById(or.getProduct().getId()).orElseThrow();
                    orderRow.setProduct(product);
                    orderRow.setQuantity(or.getQuantity());
                    decreaseStock(product, or.getQuantity());
                    return orderRow;
                })
                .mapToDouble(or -> or.getQuantity() * or.getProduct().getPrice())
                .sum();
        order.setTotal(total);
        return orderRepository.save(order);
    }

    public Product decreaseStock(Product product, Integer change) {
        if (product.getStock() < change) {
            throw new RuntimeException("Not enough inventory");
        }
        product.setStock(product.getStock() - change);
        return productRepository.save(product);
    }

    public Product increaseStock(Product product, Integer change) {
        product.setStock(product.getStock() + change);
        return productRepository.save(product);
    }

    public OrderDto mapToOrder(Order order) {
        return orderMapper.toDto(order);
    }
}
