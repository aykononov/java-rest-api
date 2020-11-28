package demo.upload;

import demo.repository.PricesRepository;
import demo.repository.ProductRepository;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.*;
@ToString
@Component
public class FileEventListener {
    //public static void main(String[] args) throws InterruptedException {
    @Value("${upload.file}")
    private String file;

    @Value("${upload.dir}")
    private String dir;

    public String listFile;

    @PostConstruct
    public void runListener() {
        System.out.println("Старт листенера...");
        UploadFromCSV uploadFromCSV = new UploadFromCSV(dir, file);
        try (WatchService service = FileSystems.getDefault().newWatchService()) {
            Path path = Paths.get(dir);
            path.register(service,StandardWatchEventKinds.ENTRY_CREATE);
            /*
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

             */
            boolean poll = true;
            while (poll) {
                WatchKey key = service.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    System.out.println(path + ": " + event.kind() + ": " + event.context());
                    listFile = event.context().toString();
                    System.out.println(listFile + " : " + file);
                    if (listFile.equals(file)) {
                        System.out.println("загрузка!!! " + listFile);
                        uploadFromCSV.loadIntoDB();
                    }
                    //file = (String) event.context();
                }
                poll = key.reset();
            }
         } catch (Exception e) {
            //System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
        }
   }
}