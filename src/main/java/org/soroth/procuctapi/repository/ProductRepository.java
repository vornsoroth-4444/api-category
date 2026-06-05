package org.soroth.procuctapi.repository;

import org.soroth.procuctapi.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class ProductRepository {
//    because we don't work with database yet
//    productList = represent the data storage
    private final List<Product> products = new ArrayList<>(){{
        add(new Product(1001, "CocaCola", "nice when cool", 23.4f,2));
        add(new Product(1002, "Sting", "easy to drink", 0.75f,4));
        add(new Product(1003, "ABC", "the best in the world", 26.5f,5));
        add(new Product(1004, "7-up", "cool like that", 15.7f,6));
    }};
    public List<Product> getProductList(){
        return products;
    }
    public Product createProduct(Product product){
        products.add(product);
        return product;
    }
    public Product findProductById(Integer id){
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
//                .orElse(null);
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + id));  // NoSuchElementException
    }
    public boolean deleteProductById(Integer id){
        return products.removeIf(product -> product.getId().equals(id));
    }

    public Product updateProduct(Integer id, Product updatedProduct){
        for(int i = 0; i<products.size(); i++){
            var product = products.get(i);
            if (product.getId() == updatedProduct.getId()){
                products.set(i, updatedProduct);
                return updatedProduct;
            }
        }
        return null;
    }
}
