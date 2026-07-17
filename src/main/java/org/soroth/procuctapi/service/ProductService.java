package org.soroth.procuctapi.service;

import org.soroth.procuctapi.dto.request.ProductRequest;
import org.soroth.procuctapi.dto.response.ProductResponse;
import org.soroth.procuctapi.dto.request.UpdateProductRequest;
import org.soroth.procuctapi.entity.ProductFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
//    For the loosely coupling design
//    this interface will be implemented  by another class
public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest) ;
    List<ProductResponse> findAllProducts();

    // for the pagination support when get all products
    Page<ProductResponse> findAllProducts(Pageable pageable);
//    Page<ProductResponse> findAllProducts(String keyword, Pageable pageable );
    Page<ProductResponse> findAllProducts(Pageable pageable , ProductFilter filter);
    ProductResponse findProductById (Integer id);
    ProductResponse updateProduct (Integer id, UpdateProductRequest updateProductRequest);
    boolean deleteProduct (Integer  id);

}
