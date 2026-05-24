package ee.anton.veebipood.service;

import ee.anton.veebipood.dto.ProductDto;
import ee.anton.veebipood.entity.Product;
import ee.anton.veebipood.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product updateProductFields(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.name());
        product.setPrice(productDto.price());
        product.setActive(productDto.active());
        product.setStock(productDto.stock());
        product.setDescription(productDto.description());
        product.setImage(productDto.image());
        return productRepository.save(product);
    }
}
