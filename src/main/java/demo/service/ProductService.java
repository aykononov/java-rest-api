package demo.service;

import demo.model.Product;
import demo.repository.ProductRepository;
import demo.upload.UploadFromCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
/*
    public void uploadCSV () throws IOException {
        UploadFromCSV uploadFromCSV = new UploadFromCSV();
        uploadFromCSV.loadIntoDB();
    }
*/
    /*
    @PostConstruct
    public List<Product> upload() {
        UploadInToDB uploadInToDB = new UploadInToDB();
        return repository.saveAll(uploadInToDB.uploadDB());
    }

 */

}
