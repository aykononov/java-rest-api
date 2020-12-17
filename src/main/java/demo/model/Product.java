package demo.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    //@GeneratedValue
    @CsvBindByName(column = "product_id")
    @Column(name = "id")
    private int id;

    @CsvBindByName(column = "product_name")
    @Column(name = "name")
    //@Column(name = "name", nullable = false)
    private String name;

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    // (+) Одностороння связь @OneToMany
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private List<Price> prices = new ArrayList<>();
}

