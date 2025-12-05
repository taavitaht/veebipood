package ee.taavi.veebipood.controller;

import ee.taavi.veebipood.model.supplier.Supplier1;
import ee.taavi.veebipood.model.supplier.Supplier2;
import ee.taavi.veebipood.model.supplier.Supplier3;
import ee.taavi.veebipood.model.supplier.Supplier3Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RestController
public class SupplierController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("supplier1")
    public List<Supplier1> supplier1(){
        System.out.println(restTemplate);
        String url = "https://fakestoreapi.com/products";
        // (URL, method, body+headers, tagastatav andmetüüp)
        Supplier1[] body = restTemplate.exchange(url, HttpMethod.GET, null, Supplier1[].class).getBody();
        return Arrays.stream(body)
                .filter(e -> e.getRating().getRate() >= 3.0)
                .sorted(Comparator.comparing(Supplier1::getPrice))
                .toList();
    }

    @GetMapping("supplier2")
    public List<Supplier2> supplier2(){
        System.out.println(restTemplate);
        String url = "https://api.escuelajs.co/api/v1/products";
        // (URL, method, body+headers, tagastatav andmetüüp)
        Supplier2[] body = restTemplate.exchange(url, HttpMethod.GET, null, Supplier2[].class).getBody();
        return Arrays.stream(body)
                .sorted(Comparator.comparing(Supplier2::getPrice).reversed())
                .toList();
    }

    @GetMapping("supplier3")
    public List<Supplier3Book> supplier3(){
        String url = "https://api.itbook.store/1.0/search/spring?page=1";
        Supplier3 body = restTemplate.exchange(url, HttpMethod.GET, null, Supplier3.class).getBody();
        return body.getBooks();
    }
}
