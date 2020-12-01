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
public class WatchService {

    @Value("${upload.file}")
    private String file;

    @Value("${upload.dir}")
    private String dir;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PricesRepository pricesRepository;

    private java.nio.file.WatchService watchService;

    @PostConstruct
    public void init() throws IOException {
        watchService = FileSystems.getDefault().newWatchService();
        startWatchService();
    }
/*
    @PreDestroy
    public void cleanUp() {
        try {
            watchService.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
*/
    private void startWatchService() {
        System.out.println("Старт WatchService...");
        Map<Integer,String> logging = new HashMap<>();
        try {
            Path path = Paths.get(dir);
            path.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    //System.out.println(path + ": " + event.kind() + ": " + event.context());
                    // Грузим только созданные новые файлы!!!
                    if (event.kind().toString().equals("ENTRY_CREATE") && event.context().toString().equals(file)) {
                        System.out.println("\nНовый файл: " + event.context().toString());
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
                                         logging.put(product.getId(), product.getName());
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
                                    fileWriter.append("\nЛог загрузки в БД - " + new Timestamp(System.currentTimeMillis()) + "\n");

                                    for (Prices price : (Iterable<Prices>) csvToBean) {
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
                                //System.out.println("Выходим.");
                                break;
                            }
                        }
                    }
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
