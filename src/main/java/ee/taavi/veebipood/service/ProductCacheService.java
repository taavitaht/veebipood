package ee.taavi.veebipood.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ee.taavi.veebipood.entity.Product;
import ee.taavi.veebipood.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class ProductCacheService {

    @Autowired
    private ProductRepository productRepository;

    private LoadingCache<Long, Product> productLoadingCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(
                    new CacheLoader<Long, Product>() {
                        @Override
                        public Product load(Long key) throws Exception {
                            return productRepository.findById(key).orElseThrow();
                        }
                    });

    public Product getProduct(long id) throws ExecutionException {
        return productLoadingCache.get(id);
    }

    public void putProduct(Product product){
        productLoadingCache.put(product.getId(), product);
    }

    public void removeProduct(long id){
        productLoadingCache.invalidate(id);
    }
}
