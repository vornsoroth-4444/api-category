package org.soroth.procuctapi.service;

import org.soroth.procuctapi.dto.ProductRequest;
import org.soroth.procuctapi.dto.ProductResponse;
import org.soroth.procuctapi.dto.UpdateProductRequest;
import org.soroth.procuctapi.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
//    For the loosely coupling design
//    this interface will be implemented  by another class
public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest) ;
    List<ProductResponse> findAllProducts();
    ProductResponse findProductById (int id);
    ProductResponse updateProduct (Integer id, UpdateProductRequest updateProductRequest);
    boolean deleteProduct (Integer  id);
}
