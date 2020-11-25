package demo.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;

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
    @Column(name = "name", nullable = false)
    private String name;

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
/*
    @OneToMany(targetEntity = Prices.class, cascade = CascadeType.ALL, orphanRemoval = true )
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private List<Prices> prices;

 */

}

