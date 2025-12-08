package ee.taavi.veebipood.repository;

import ee.taavi.veebipood.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByPerson_Id(Long id);
}
