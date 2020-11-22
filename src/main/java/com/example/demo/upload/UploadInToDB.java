package com.example.demo.upload;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class UploadInToDB {

    @Autowired
    private ProductRepository repository;

    @Value("${upload.file}")
    private String file;

    @Value("${upload.dir}")
    private String dir;

    @PostConstruct
    public List<Product> uploadDB() throws IOException {

        ListFiles lf = new ListFiles();
        for (String vals : lf.listFilesUsingJavaIO(dir)) {
             System.out.println(dir + vals);
        }

        ArrayList<Product> products = new ArrayList<Product>();
        try {
             // create a reader
            Reader reader = null;
            reader = Files.newBufferedReader(Paths.get(dir + file));

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

        //return products;
        return repository.saveAll(products);
    }
}
