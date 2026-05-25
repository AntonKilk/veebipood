package ee.anton.veebipood.controller;

import ee.anton.veebipood.dto.OrderDto;
import ee.anton.veebipood.entity.Order;
import ee.anton.veebipood.entity.OrderRow;
import ee.anton.veebipood.repository.OrderRepository;
import ee.anton.veebipood.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @GetMapping("orders")
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Cacheable(value = "orderCache", key = "#id")
    @GetMapping("orders/{id}")
    public OrderDto findById(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        return orderService.mapToOrder(order);
    }

    @PostMapping("orders")
    public Order save(@RequestBody List<OrderRow> orderRows) {
        return orderService.saveOrder(orderRows);
    }

    @CacheEvict(value = "orderCache", key = "#id")
    @PatchMapping("orders/{id}")
    public OrderDto archive(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setActive(false);
        Order modifiedOrder = orderRepository.save(order);
        return orderService.mapToOrder(modifiedOrder);
    }
}
