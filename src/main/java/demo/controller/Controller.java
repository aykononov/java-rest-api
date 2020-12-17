package demo.controller;

import demo.model.Price;
import demo.model.Product;
import demo.repository.PriceRepository;
import demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.sql.Date;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PriceRepository priceRepository;

    @GetMapping("/listProducts")
    public List<Product> listProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/addProduct")
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PostMapping("/saveProducts")
    public List<Product> saveProducts(@RequestBody List<Product> products) {
        return productRepository.saveAll(products);
    }

    @GetMapping("/getProductById/id={id}")
    public Product getProductById(@PathVariable int id) {
        return productRepository.findById(id).orElse(null);
    }

    @GetMapping("/getProductByName/name={name}")
    public Product getProductByName(@PathVariable String name) {
        return productRepository.findByName(name);
    }

    @DeleteMapping("/deleteProductById/id={id}")
    public String deleteProduct(@PathVariable int id) {
        productRepository.deleteById(id);
        return "Product removed id: " + id;
    }

    @DeleteMapping("/removeAll")
    public String removeAll() {
        long cnt = productRepository.count();
        productRepository.deleteAll();
        return "Delete products count: " + cnt;
    }

}
