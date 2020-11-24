package com.example.demo.upload;

import com.example.demo.model.Prices;
import com.example.demo.model.Product;
import com.example.demo.repository.PricesRepository;
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
public class LoadIntoDBFromCSV {
/*
    @Value("${upload.file}")
    private String file;

    @Value("${upload.dir}")
    private String dir;


    @Autowired
    private PricesRepository pricesRepository;

    @PostConstruct
    public List<Prices> loadPriceDB() throws IOException {

        ListFiles lf = new ListFiles();
        for (String existFile : lf.listFilesUsingJavaIO(dir)) {
            System.out.println(dir + existFile);
            if (file.equalsIgnoreCase(existFile)) {
                System.out.println(file + " = " + existFile);
            }
        }
        ArrayList<Prices> prices = new ArrayList<Prices>();
        try {
            // create a reader
            Reader reader = null;
            reader = Files.newBufferedReader(Paths.get(dir + file));

            // create csv bean reader
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Prices.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            // iterate through products
            for (Prices price : (Iterable<Prices>) csvToBean) {
                System.out.println("price_id: " + price.getId() +
                                   " price: " + price.getPrice() +
                                   " product_id: " + price.getProductId() +
                                   " pDate: " + price.getPdate()
                                   );
                prices.add(price);
            }

            // close the reader
            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //return products;
        return pricesRepository.saveAll(prices);
    }

    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public List<Product> loadProductDB() throws IOException {

        ListFiles lf = new ListFiles();
        for (String existFile : lf.listFilesUsingJavaIO(dir)) {
            System.out.println(dir + existFile);
            if (file.equalsIgnoreCase(existFile)) {
                System.out.println(file + " = " + existFile);
            }
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

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //return products;
        return productRepository.saveAll(products);
    }

 */
}
