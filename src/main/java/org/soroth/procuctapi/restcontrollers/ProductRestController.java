package org.soroth.procuctapi.restcontrollers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.soroth.procuctapi.dto.ProductRequest;
import org.soroth.procuctapi.dto.ProductResponse;
import org.soroth.procuctapi.dto.UpdateProductRequest;
import org.soroth.procuctapi.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductRestController {
    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> getAllProducts(){
        return productService.findAllProducts();
    }

    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }
//    find product by id
//    http://localhost:8080/api/v1/products
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable int id){
        return productService.findProductById(id);
    }
    // PATCH localhost:8080/api/v1/products
    // Content-Type JSON
    @PatchMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Integer id, @RequestBody UpdateProductRequest updateProductRequest){
        return productService.updateProduct(id, updateProductRequest);
    }
//    DELETE localhost:8080/api/v1/products/{id}
    @DeleteMapping("/{id}")
    public boolean deleteProduct(@PathVariable Integer id){
        return productService.deleteProduct(id);
    }
}
