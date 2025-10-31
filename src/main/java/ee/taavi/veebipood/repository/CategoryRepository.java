package ee.taavi.veebipood.repository;

import ee.taavi.veebipood.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
