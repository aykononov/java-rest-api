package demo.dto;

import demo.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductRequest {
    private Product product;

    public Product getProduct() {
        return getProduct();
    }
}
