package ee.taavi.veebipood.controller;

import ee.taavi.veebipood.entity.Category;
import ee.taavi.veebipood.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("categories")
    public ResponseEntity<List<Category>> getCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryRepository.findAll());
    }

    @PostMapping("categories")
    public ResponseEntity<List<Category>> addCategory(@RequestBody Category category){
        categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.OK).body(categoryRepository.findAll());
    }

    @DeleteMapping("categories/{id}")
    public ResponseEntity<List<Category>> deleteCategory(@PathVariable Long id){
        categoryRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(categoryRepository.findAll());
    }

}
