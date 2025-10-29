package ee.taavi.veebipood.controller;

import ee.taavi.veebipood.entity.Product;
import ee.taavi.veebipood.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        productRepository.save(product);
        return productRepository.findAll();
    }


}
