package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> saveProducts(List<Product> products) {
        return repository.saveAll(products);
    }

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public Product getProductById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Product getProductByName(String name) {
        return repository.findByName(name);
    }

    public String deleteProduct(int id) {
        repository.deleteById(id);
        return "Product removed id: " + id;
    }

    public Product updateProduct(Product product) {
        Product existingProduct = repository.findById(product.getId()).orElse(null);
        existingProduct.setName(product.getName());
        return repository.save(existingProduct);
    }

    @PostConstruct
    public List<Product> uploadDB() {
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            // create a reader
            Reader reader = null;
            reader = Files.newBufferedReader(Paths.get("d:/gith/products.csv"));

            // create csv bean reader
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Product.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            // iterate through products
            for (Product product : (Iterable<Product>) csvToBean) {
                System.out.println("ID: " + product.getId() + " Name: " + product.getName());
                products.add(product);
            }

            // close the reader
            reader.close();

        } catch (
                IOException ex) {
            ex.printStackTrace();
        }

        return repository.saveAll(products);
    }
}

