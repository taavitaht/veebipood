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

    public String makePayment(Long id, double total) {
        /*
        {
            "account_name": "EUR3D1",                   konto nimi, kuhu raha läheb
                "nonce": "a16a5784sdafb51fkm",          turvaelement, peab olema unikaalne igas päringus
                "timestamp": "2025-11-26T09:59:32Z",    turvaelement, ajaempel
                "amount": 123.45,                       summa, max. 7000€
                "order_reference": "asdf789",           tellimuse number, kui on makstud siis teist korda ei saa maksta
                "customer_url": "https://err.ee",       kuhu suunatakse tagasi, ei saa olla localhost
                "api_username": "e36eb40f5ec87fa2"      kasutajanimi, peab ühtima Authorization kasutajanimega
        }
        */

        return "";
    }
}
