package demo.controller;

import demo.model.Product;
import demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/listProducts")
    public List<Product> listProducts() {
        return productService.getProducts();
    }

    @GetMapping("/findProductById/id={id}")
    public Product findProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @GetMapping("/findProductByName/name={name}")
    public Product findProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

    @DeleteMapping("/deleteProductById/id={id}")
    public String deleteProductById(@PathVariable int id) {
        return productService.deleteProduct(id);
    }

    @PostMapping("/addProduct")
    public Product addProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PostMapping("/loadProducts")
    public List<Product> loadProducts(@RequestBody List<Product> products) {
        return productService.saveProducts(products);
    }

}