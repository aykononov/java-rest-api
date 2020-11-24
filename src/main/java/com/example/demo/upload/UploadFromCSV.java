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

@Component
public class UploadFromCSV {

    @Value("${upload.file}")
    private String file;

    @Value("${upload.dir}")
    private String dir;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PricesRepository pricesRepository;

    @PostConstruct
    public void loadIntoDB() throws IOException {
        ListFiles listFiles = new ListFiles();
        for (String checkFile : listFiles.listFilesUsingJavaIO(dir)) {
            System.out.println(dir + checkFile);
            if (file.equalsIgnoreCase(checkFile)) {
                System.out.println(checkFile + " файл существует можно грузить в БД: ");

                System.out.println("загрузка таблицы Products...");
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
                productRepository.saveAll(products);

                System.out.println("загрузка таблицы Prices...");
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
                pricesRepository.saveAll(prices);
            } else System.out.println("Файл " + file + " не найден!!!");
        }
    }
}
