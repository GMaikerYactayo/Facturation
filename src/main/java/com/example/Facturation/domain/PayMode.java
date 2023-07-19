package com.example.Facturation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pay_mode")
public class PayMode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pay_mode_id;
    private String name;
    private String other_detail;

}
