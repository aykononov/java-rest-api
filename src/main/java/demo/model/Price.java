package demo.model;

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
public class Price {
    @Id
    @CsvBindByName(column = "price_id")
    @Column(name = "id")
    private int id;

    @CsvBindByName(column = "price")
    @Column(name = "price", nullable = true)
    private double price;

    @CsvBindByName(column = "price_date")
    @DateTimeFormat
    @Column(name = "pdate")
    private Date pdate;


    @CsvBindByName(column = "product_id")
    @Column(name = "product_id", nullable = true)
    private int productId;

    public double getPrice() {
        return this.price;
    }

    public int getProductId() {
        return productId;
    }

}