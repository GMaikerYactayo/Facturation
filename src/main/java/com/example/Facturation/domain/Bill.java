package com.example.Facturation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "bill")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bill_id;
    private LocalDate date_creation;
    @Column(name = "total")
    private float total;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "pay_mode_id")
    private PayMode pay_mode;
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private List<Detail> detail = new ArrayList<>();

}
