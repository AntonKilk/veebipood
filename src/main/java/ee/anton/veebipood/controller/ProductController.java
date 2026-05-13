package ee.anton.veebipood.controller;

import ee.anton.veebipood.dto.ProductDto;
import ee.anton.veebipood.entity.Product;
import ee.anton.veebipood.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("products")
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @GetMapping("products/{id}")
    public Product findOne(@PathVariable Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @PostMapping("products")
    public Product save(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PatchMapping("products/{id}")
    public Product update(@PathVariable Long id, @RequestBody ProductDto dto) {
        Product product = productRepository.findById(id).orElseThrow();
        if (dto.name() != null) product.setName(dto.name());
        if (dto.price() != null) product.setPrice(dto.price());
        if (dto.active() != null) product.setActive(dto.active());
        if (dto.stock() != null) product.setStock(dto.stock());
        if (dto.description() != null) product.setDescription(dto.description());
        if (dto.image() != null) product.setImage(dto.image());
        return productRepository.save(product);
    }

    @DeleteMapping("products/{id}")
    public void delete(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}
