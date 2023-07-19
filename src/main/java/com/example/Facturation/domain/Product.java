package com.example.Facturation.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int product_id;
    private String name;
    private float price;
    private int stock;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

}
