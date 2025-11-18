package ee.taavi.veebipood.service;

import ee.taavi.veebipood.entity.Order;
import ee.taavi.veebipood.entity.OrderRow;
import ee.taavi.veebipood.entity.Person;
import ee.taavi.veebipood.entity.Product;
import ee.taavi.veebipood.repository.OrderRepository;
import ee.taavi.veebipood.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Order saveOrder(List<OrderRow> orderRows, Long personId) {
        Order order = new Order();
        order.setCreated(new Date());
        order.setOrderRows(orderRows);

        double sum = 0;
        for (OrderRow orderRow : orderRows){
            Product dbProduct = productRepository.findById(orderRow.getProduct().getId()).orElseThrow();
            sum += dbProduct.getPrice() * orderRow.getQuantity();
        }
        order.setTotal(sum);

        Person person = new Person();
        person.setId(personId);
        order.setPerson(person); // hiljem autentimisest

        return orderRepository.save(order);
    }
}
