package ee.taavi.veebipood.controller;

import ee.taavi.veebipood.entity.Product;
import ee.taavi.veebipood.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:5173")
@RestController
public class ProductController {

    @Autowired // Dependency Injection
    private ProductRepository productRepository;

    // localhost:8080/product
    @GetMapping("products")
    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    // localhost:8080/products
    @PostMapping("products")
    public List<Product> addProduct(@RequestBody Product product){
        if(product.getId() != null){
            throw new RuntimeException("Cannot add when id is present");
        }
        if(product.getName()== null || product.getName().isEmpty()){
            throw new RuntimeException("Name cannot be empty");
        }
        if(product.getPrice() <= 0){
            throw new RuntimeException("Price cannot be negative or 0");
        }
        productRepository.save(product);
        return productRepository.findAll();
    }

    // Delete variant 1
    // localhost:8080/products?id=2
    @DeleteMapping("products")
    public List<Product> deleteProduct(@RequestParam Long id){
        productRepository.deleteById(id);
        return productRepository.findAll();
    }

    // Delete variant 2
    // localhost:8080/products/2
    @DeleteMapping("products2/{id}")
    public List<Product> deleteProduct2(@PathVariable Long id){
        productRepository.deleteById(id);
        return productRepository.findAll();
    }

    // Get 1 product
    @GetMapping("products/{id}")
    public Product getProduct(@PathVariable Long id){
        return productRepository.findById(id).orElseThrow();
    }

    // Edit product
    @PutMapping("products")
    public List<Product> editProduct(@RequestBody Product product){
        if(product.getId() == null || product.getId() <= 0){
            throw new RuntimeException("Cannot edit when id is null or empty");
        }
        if(product.getName()== null || product.getName().isEmpty()){
            throw new RuntimeException("Name cannot be empty");
        }
        if(product.getPrice() <= 0){
            throw new RuntimeException("Price cannot be negative or 0");
        }

        productRepository.save(product);
        return productRepository.findAll();
    }
}
