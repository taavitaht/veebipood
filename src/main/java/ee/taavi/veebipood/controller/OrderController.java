package ee.taavi.veebipood.controller;

import ee.taavi.veebipood.entity.Order;
import ee.taavi.veebipood.entity.OrderRow;
import ee.taavi.veebipood.entity.Person;
import ee.taavi.veebipood.entity.Product;
import ee.taavi.veebipood.repository.OrderRepository;
import ee.taavi.veebipood.repository.ProductRepository;
import ee.taavi.veebipood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin("http://localhost:5173")
@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping("orders")
    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    @PostMapping("order/{personId}")
    public Order createOrder(@RequestBody List<OrderRow> orderRows, @PathVariable("personId") Long personId){

        return orderService.saveOrder(orderRows, personId);
    }
}
