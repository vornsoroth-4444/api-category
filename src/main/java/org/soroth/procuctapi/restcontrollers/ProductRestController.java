package org.soroth.procuctapi.restcontrollers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.soroth.procuctapi.dto.request.ProductRequest;
import org.soroth.procuctapi.dto.response.ProductResponse;
import org.soroth.procuctapi.dto.request.UpdateProductRequest;
import org.soroth.procuctapi.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductRestController {
    private final ProductService productService;

//    @GetMapping
//    public List<ProductResponse> getAllProducts(){
//        return productService.findAllProducts();
//    }
    @GetMapping
    public Page<ProductResponse> getAllProducts (
            @RequestParam(required = false, defaultValue = "") String keyword,
            Pageable pageable){
        return productService.findAllProducts(keyword, pageable);
    }

    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }
//    find product by id
//    http://localhost:8080/api/v1/products
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Integer id){
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
