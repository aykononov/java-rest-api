package com.example.demo.entity;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    //@GeneratedValue
    @CsvBindByName(column = "product_id")
    @Column(name = "product_id")
    private int id;

    @CsvBindByName(column = "product_name")
    @Column(name = "product_name", nullable = false)
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
   public void upload() {
       try {
           // create a reader
           Reader reader = null;
           reader = Files.newBufferedReader(Paths.get("d:/gith/products.csv"));

           // create csv bean reader
           CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                   .withType(Product.class)
                   .withIgnoreLeadingWhiteSpace(true)
                   .build();

           // iterate through users
           for (Product product : (Iterable<Product>) csvToBean) {
               System.out.println("ID: " + product.getId());
               System.out.println("Name: " + product.getName());
               product.setId(product.getId());
               product.setName(product.getName());
           }

           // close the reader
           reader.close();

       } catch (IOException ex) {
           ex.printStackTrace();
       }

   }

 */
}
