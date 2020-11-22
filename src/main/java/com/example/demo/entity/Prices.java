package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "PRICES")
public class Prices {
    @Id
    //@GeneratedValue
    @Column(name = "id")
    private int pid;

    @Column(nullable = false)
    private String price;

    @DateTimeFormat
    @Column(name = "pdate")
    private Date pdate;

    @Column(name = "product_id")
    private int productId;

}