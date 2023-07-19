package com.example.Facturation.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Table(name = "detail")
public class Detail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int detail_id;
    private float price;
    private int amount;
    @ManyToOne
    @JoinColumn(name = "bill_id")
    @JsonBackReference
    private Bill bill;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
