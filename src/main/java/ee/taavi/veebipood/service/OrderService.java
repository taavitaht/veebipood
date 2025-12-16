package ee.taavi.veebipood.service;

import ee.taavi.veebipood.entity.Order;
import ee.taavi.veebipood.entity.OrderRow;
import ee.taavi.veebipood.entity.Person;
import ee.taavi.veebipood.entity.Product;
import ee.taavi.veebipood.model.EveryPayBody;
import ee.taavi.veebipood.model.EveryPayResponse;
import ee.taavi.veebipood.repository.OrderRepository;
import ee.taavi.veebipood.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductCacheService productCacheService;

    public Order saveOrder(List<OrderRow> orderRows, Long personId) throws ExecutionException {
        Order order = new Order();
        order.setCreated(new Date());
        order.setOrderRows(orderRows);

        double sum = 0;
        for (OrderRow orderRow : orderRows){
            // Product dbProduct = productRepository.findById(orderRow.getProduct().getId()).orElseThrow();
            Product dbProduct = productCacheService.getProduct(orderRow.getProduct().getId());
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

        String url = "https://igw-demo.every-pay.com/api/v4/payments/oneoff";

        EveryPayBody body = new EveryPayBody();
        body.setAccount_name("EUR3D1");
        body.setNonce("bla" + ZonedDateTime.now() + UUID.randomUUID());
        body.setTimestamp(ZonedDateTime.now().toString());
        body.setAmount(total);
        body.setOrder_reference("uiop" + id);
        body.setCustomer_url("https://err.ee");
        body.setApi_username("e36eb40f5ec87fa2");

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("e36eb40f5ec87fa2", "7b91a3b9e1b74524c2e9fc282f8ac8cd");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EveryPayBody> httpEntity = new HttpEntity<>(body, headers);

        EveryPayResponse response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, EveryPayResponse.class).getBody();

        if (response == null){
            throw new RuntimeException("EveryPay response is null");
        }

        return response.getPayment_link();
    }
}
