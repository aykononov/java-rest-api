package com.example.demo.model;

import com.opencsv.bean.CsvBindByName;
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
    @CsvBindByName(column = "price_id")
    @Column(name = "id")
    private int id;

    @CsvBindByName(column = "price")
    @Column(name = "price", nullable = true)
    private double price;

    @CsvBindByName(column = "product_id")
    @Column(name = "product_id", nullable = true)
    private int productId;

    @CsvBindByName(column = "price_date")
    @DateTimeFormat
    @Column(name = "pdate")
    private Date pdate;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Date getPdate() {
        return this.pdate;
    }

    public void setPdate(Date pdate) {
        this.pdate = pdate;
    }
}