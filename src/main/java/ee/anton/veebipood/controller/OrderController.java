package ee.anton.veebipood.controller;

import ee.anton.veebipood.entity.Order;
import ee.anton.veebipood.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;

    @GetMapping("orders")
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @GetMapping("orders/{id}")
    public Order findById(@PathVariable Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @PostMapping("orders")
    public Order save(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @PutMapping("orders/{id}")
    public Order archive(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setActive(false);
        orderRepository.save(order);
        return order;
    }
}
