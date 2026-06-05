package org.soroth.procuctapi.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.soroth.procuctapi.dto.ProductRequest;
import org.soroth.procuctapi.dto.ProductResponse;
import org.soroth.procuctapi.dto.UpdateProductRequest;
import org.soroth.procuctapi.entity.Product;
import org.soroth.procuctapi.repository.ProductRepository;
import org.springframework.stereotype.Service;


import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    //inject the repository here
    private final ProductRepository productRepository;
    private  Integer nextId = 1005;
    // mapToEntity -> convert Request to Entity
    private  Product mapToEntity(ProductRequest productRequest){
        Product product = new Product();
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        return product;
    }
    //mapToResponse -> convert Entity to Response
    private ProductResponse mapToResponse (Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
    @Override
    public ProductResponse createProduct( ProductRequest productRequest) {
//        create entity product from the request
        var product = mapToEntity(productRequest);
//        set ID using nextId
        product.setId(nextId++);
        return mapToResponse(productRepository.createProduct(product));

    }

    @Override
    public List<ProductResponse> findAllProducts() {
        return productRepository.getProductList()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductResponse findProductById(int id) {
        var product = productRepository.findProductById(id);
        return mapToResponse(product);
    }

    @Override
    public ProductResponse updateProduct(Integer id, UpdateProductRequest updateProductRequest) {
  // find existing product
        var existingProduct = productRepository.findProductById(id);
        // update the product with new values
        if (updateProductRequest.name() != null){
             existingProduct.setName(updateProductRequest.name());
        }
        if (updateProductRequest.description() != null){
            existingProduct.setDescription(updateProductRequest.description());
        }
        if (updateProductRequest.price() != null){
            existingProduct.setPrice(updateProductRequest.price());
        }
        // save the updated product
        var updatedProduct = productRepository.updateProduct(id, existingProduct);
        return mapToResponse(updatedProduct);
    }

    @Override
    public boolean deleteProduct(Integer id) {
        return productRepository.deleteProductById(id);
    }

}
