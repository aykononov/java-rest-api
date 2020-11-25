package demo.upload;

import demo.model.Prices;
import demo.model.Product;
import demo.repository.PricesRepository;
import demo.repository.ProductRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

        File fin = new File(dir + file);
        HashMap<Integer,String> logging = new HashMap<>();

        if (fin.exists()) {
            System.out.println("Файл (" + fin.getName() + ") существует, можно грузить в БД: ");
            System.out.println(new Timestamp(System.currentTimeMillis()));

            try (FileWriter fileWriter = new FileWriter(dir + "LoadIntoDB.log")) {
                // create a reader
                Reader reader = Files.newBufferedReader(Paths.get(fin.toString()));
                //reader = Files.newBufferedReader(Paths.get(dir + file));
                CsvToBean csvToBean = null;

                System.out.println("Загрузка таблицы (Products)...");
                ArrayList<Product> products = new ArrayList<>();
                // create csv bean reader
                csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Product.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                // iterate through Products
                for (Product product : (Iterable<Product>) csvToBean) {
                    System.out.println("ID: " + product.getId() + " Name: " + product.getName());
                    logging.put(product.getId(),product.getName());
                    products.add(product);
                }

                productRepository.saveAll(products);
                reader.close();

                System.out.println("Загрузка таблицы (Prices)...");
                // create a reader
                reader = Files.newBufferedReader(Paths.get(fin.toString()));

                ArrayList<Prices> prices = new ArrayList<>();
                // create csv bean reader
                csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Prices.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                fileWriter.append("Загрузка " + new Timestamp(System.currentTimeMillis()) + "\n");
                // iterate through Prices
                for (Prices price : (Iterable<Prices>) csvToBean) {
                    System.out.println("price_id: " + price.getId() +
                                        "\tprice: " + price.getPrice() +
                                        "\tproduct_id: " + price.getProductId() +
                                        "\tDate: " + price.getPdate() +
                                        "\tname: " + logging.get(price.getProductId()));
                    prices.add(price);
                    fileWriter.append(logging.get(price.getProductId()) + "\t" + price.getPrice() + "\n");
                    fileWriter.flush();
                }

                pricesRepository.saveAll(prices);
                // close the reader
                reader.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else System.out.println("Файл (" + file + ") не найден!!!");
    }
 }
