package demo.service;


import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import demo.model.Prices;
import demo.model.Product;
import demo.repository.PricesRepository;
import demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.nio.file.*;
import java.sql.Timestamp;
import java.util.*;

@Component
@Service
public class WatcherService {

    @Value("${upload.file}")
    private String file;

    @Value("${upload.dir}")
    private String dir;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PricesRepository pricesRepository;

    private WatchService watchService;
    //private FileReader reader;

    @PostConstruct
    public void init() throws IOException {
        watchService = FileSystems.getDefault().newWatchService();
        //reader = null;
        //reader = new FileReader(dir + file);
        startWatcherService();
    }
/*
    @PreDestroy
    public void cleanUp() {
        try {
            System.out.println(reader.getEncoding());
            reader.close();
            watchService.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
*/
    private void startWatcherService() {
        System.out.println("Старт WatcherService...");
        //UploadFromCSV uploadFromCSV = new UploadFromCSV(dir, file);

        Map<Integer,String> logging = new HashMap<>();
        try  {
        //try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            Path path = Paths.get(dir);
            path.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE);
                    //StandardWatchEventKinds.ENTRY_DELETE,
                    //StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    //System.out.println(path + ": " + event.kind() + ": " + event.context());
                    if (event.kind().toString().equals("ENTRY_CREATE") && event.context().toString().equals(file)) {
                        //if (event.context().toString().equals(file)) {
                        System.out.println("загрузка!!! " + event.context().toString());
                        while (true) {
                            boolean ready = Files.isReadable(Paths.get(dir + file));
                            System.out.println(" готов для чтения: " + ready);
                            if (ready) {
                                try (FileWriter fileWriter = new FileWriter(dir + "LoadIntoDB.log", true)) {
                                    Reader reader = Files.newBufferedReader(Paths.get(dir + file));
                                    CsvToBean csvToBean = null;
                                    System.out.println("Загрузка таблицы (Products)...");
                                    List<Product> products = new ArrayList<>();
                                    csvToBean = new CsvToBeanBuilder(reader)
                                            .withType(Product.class)
                                            .withIgnoreLeadingWhiteSpace(true)
                                            .build();
                                    int count = 0;
                                    for (Product product : (Iterable<Product>) csvToBean) {
                                        System.out.println("ID: " + product.getId() + " Name: " + product.getName());
                                        logging.put(product.getId(), product.getName());
                                        fileWriter.append(logging.get(product.getId()) + "\t" + product.getName() + "\n");
                                        fileWriter.append("Обработано записей: " + count + "\n");
                                        fileWriter.flush();
                                        products.add(product);
                                    }
                                    productRepository.saveAll(products);

                                    System.out.println("Загрузка таблицы (Prices)...");
                                    reader = Files.newBufferedReader(Paths.get(dir + file));
                                    List<Prices> prices = new ArrayList<>();
                                    csvToBean = new CsvToBeanBuilder(reader)
                                            .withType(Prices.class)
                                            .withIgnoreLeadingWhiteSpace(true)
                                            .build();
                                    fileWriter.append("\n" + new Timestamp(System.currentTimeMillis()) + " загрузка в БД...\n");

                                    for (Prices price : (Iterable<Prices>) csvToBean) {
                                        System.out.println("price_id: " + price.getId() +
                                                "\tprice: " + price.getPrice() +
                                                "\tproduct_id: " + price.getProductId() +
                                                "\tDate: " + price.getPdate() +
                                                "\tname: " + logging.get(price.getProductId()));
                                        prices.add(price);
                                        fileWriter.append(logging.get(price.getProductId()) + "\t" + price.getPrice() + "\n");
                                        count++;
                                    }
                                    fileWriter.append("Обработано записей: " + count + "\n");
                                    fileWriter.flush();
                                    reader.close();
                                    pricesRepository.saveAll(prices);

                                } catch (NoSuchElementException | IOException e) {
                                    System.out.println(e.getMessage());
                                }
                                System.out.println("Выходим.");
                                break;
                            }
                        }

/*
                            CsvToBean csvToBean = null;

                            System.out.println("Загрузка таблицы (Products)...");
                            List<Product> products = new ArrayList<>();
                            csvToBean = new CsvToBeanBuilder(reader)
                                    .withType(Product.class)
                                    .withIgnoreLeadingWhiteSpace(true)
                                    .build();
                            int count = 0;
                            for (Product product : (Iterable<Product>) csvToBean) {
                                System.out.println("ID: " + product.getId() + " Name: " + product.getName());
                                logging.put(product.getId(),product.getName());
                                fileWriter.append(logging.get(product.getId()) + "\t" + product.getName() + "\n");
                                fileWriter.append("Обработано записей: " + count + "\n");
                                fileWriter.flush();
                                products.add(product);
                            }
                            csvToBean = null;
                            //is.close();
                            //reader.close();
                            //key.cancel();
                            //productRepository.saveAll(products);

                            System.out.println("Загрузка таблицы (Prices)...");
                            //reader = Files.newBufferedReader(Paths.get(fin.toString()));
                            //reader = new FileReader(dir + file);
                            List<Prices> prices = new ArrayList<>();
                            csvToBean = new CsvToBeanBuilder(reader)
                                    .withType(Prices.class)
                                    .withIgnoreLeadingWhiteSpace(true)
                                    .build();
                            fileWriter.append("\n" + new Timestamp(System.currentTimeMillis()) + " загрузка в БД...\n");


                            for (Prices price : (Iterable<Prices>) csvToBean) {
                                System.out.println("price_id: " + price.getId() +
                                        "\tprice: " + price.getPrice() +
                                        "\tproduct_id: " + price.getProductId() +
                                        "\tDate: " + price.getPdate() +
                                        "\tname: " + logging.get(price.getProductId()));
                                prices.add(price);
                                fileWriter.append(logging.get(price.getProductId()) + "\t" + price.getPrice() + "\n");
                                count ++;
                            }
                            fileWriter.append("Обработано записей: " + count + "\n");
                            fileWriter.flush();
                            //fileWriter.close();
                            // close the reader

                            reader.close();
                            //csvToBean = null;
                            //reader.reset();

                            pricesRepository.saveAll(prices);
*/

                    }
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
