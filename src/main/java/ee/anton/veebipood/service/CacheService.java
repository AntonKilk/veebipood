package ee.anton.veebipood.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ee.anton.veebipood.entity.Product;
import ee.anton.veebipood.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final ProductRepository productRepository;

    private LoadingCache<Long, Product> productCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(Duration.ofMinutes(10))
            .build(
                    new CacheLoader<Long, Product>() {
                        @Override
                        public Product load(Long key) throws Exception {
                            System.out.println("get product from DB by id " + key);
                            return productRepository.findById(key).orElseThrow();
                        }
                    });

    public Product getProduct(Long id) throws ExecutionException {
        System.out.println("get product from cache");
        return productCache.get(id);
    }

    public void updateProduct(Long id) {
        productCache.refresh(id);
    }

    public void deleteProduct(Long id) {
        productCache.invalidate(id);
    }
}
