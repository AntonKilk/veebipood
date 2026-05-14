package ee.anton.veebipood.controller;

import ee.anton.veebipood.dto.ProductDto;
import ee.anton.veebipood.entity.Product;
import ee.anton.veebipood.mapper.ProductMapper;
import ee.anton.veebipood.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping("products")
    public Page<Product> findAll(Pageable pageable, @RequestParam(required = false) Long categoryId) {
        if (categoryId != null) {
            return productRepository.findAllByCategoryId(pageable, categoryId);
        }
        return productRepository.findAll(pageable);
    }

    @GetMapping("products/{id}")
    public Product findOne(@PathVariable Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @PostMapping("products")
    public Product save(@RequestBody ProductDto dto) {
        Product product = productMapper.toEntity(dto);
        return productRepository.save(product);
    }

    @PutMapping("products/{id}")
    public Product update(@PathVariable Long id, @RequestBody ProductDto dto) {
        Product product = productRepository.findById(id).orElseThrow();
        productMapper.update(dto, product);
        return productRepository.save(product);
    }

    @DeleteMapping("products/{id}")
    public void delete(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}
