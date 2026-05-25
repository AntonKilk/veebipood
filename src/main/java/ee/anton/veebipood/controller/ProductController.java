package ee.anton.veebipood.controller;

import ee.anton.veebipood.dto.PageRequestDto;
import ee.anton.veebipood.dto.ProductDto;
import ee.anton.veebipood.entity.Product;
import ee.anton.veebipood.repository.ProductRepository;
import ee.anton.veebipood.service.CacheService;
import ee.anton.veebipood.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final CacheService cacheService;

    @GetMapping("products")
    public Page<Product> findAll(Pageable pageable, @RequestParam(required = false) Long categoryId) {
        if (categoryId != null) {
            return productRepository.findAllByCategoryId(pageable, categoryId);
        }
        return productRepository.findAll(pageable);
    }

    @GetMapping("products/{id}")
    public Product findOne(@PathVariable Long id) throws ExecutionException {
        return cacheService.getProduct(id);
    }

    @PostMapping("products")
    public Product save(@RequestBody ProductDto dto) {
        Product product = productService.updateProductFields(dto);
        return productRepository.save(product);
    }

    @PutMapping("products/{id}")
    public Product update(@PathVariable Long id, @RequestBody ProductDto dto) {
        Product product = productService.updateProductFields(dto);
        cacheService.updateProduct(id);
        return productRepository.save(product);
    }

    @DeleteMapping("products/{id}")
    public void delete(@PathVariable Long id) {
        productRepository.deleteById(id);
        cacheService.deleteProduct(id);
    }

    @MessageMapping("/products-update")
    @SendTo("/get-products")
    public Page<Product> getUpdatedProducts(@RequestBody PageRequestDto request) {
        return productRepository.findAll(
                PageRequest.of(request.page(), request.size(), Sort.by("stock").ascending()));
    }
}
