package ir.shop.application.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Customer customer;
    @ManyToMany
    @JoinTable
    private Set<Product> product;
}
