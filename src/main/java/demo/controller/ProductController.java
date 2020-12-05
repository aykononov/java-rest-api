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

    @GetMapping("/products")
    public List<Product> findAllProducts() {
        return productService.getProducts();
    }

    @GetMapping("/product/id={id}")
    public Product findProductById(@PathVariable int id) {
        return productService.getProductById(id);
    }

    @GetMapping("/product/name={name}")
    public Product findProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

    @DeleteMapping("/delete/id={id}")
    public String deleteProduct(@PathVariable int id) {
        return productService.deleteProduct(id);
    }

    @PostMapping("/addProduct")
    public Product addProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PostMapping("/addProducts")
    public List<Product> addProducts(@RequestBody List<Product> products) {
        return productService.saveProducts(products);
    }
/*
    @PostMapping("/placeProduct")
    public Product placeProduct(@RequestBody ProductRequest request) {
        //return productRepository.save(request.getProduct());
        return productService.save(request.getProduct());
    }
*/
}