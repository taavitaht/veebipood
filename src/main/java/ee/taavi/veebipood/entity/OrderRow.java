package ee.taavi.veebipood.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    // @ManyToMany - ei sobi, peaks olema List<Product>
    // @ManyToOne - kellegi teise order rowl voib olla sama toode
    // @OneToMany - ei sobi, peaks olema List<Product>
    // @OneToOne - kelelgi teisel ei tohi olla
    @ManyToOne
    private Product product;
}
