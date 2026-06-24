package org.soroth.procuctapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.soroth.procuctapi.dto.ProductRequest;
import org.soroth.procuctapi.dto.ProductResponse;
import org.soroth.procuctapi.dto.UpdateProductRequest;
import org.soroth.procuctapi.entity.Tag;
import org.soroth.procuctapi.mapper.ProductMapper;
import org.soroth.procuctapi.repository.CategoryRepository;
import org.soroth.procuctapi.repository.ProductRepository;
import org.soroth.procuctapi.repository.TagRepository;
import org.soroth.procuctapi.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    //inject the repository here
//    private final ProductRepositoryOld productRepository;
 private  final ProductRepository productRepository;
 private final CategoryRepository categoryRepository;
 private final TagRepository tagRepository;
 private final ProductMapper productMapper;


//    // mapToEntity -> convert Request to Entity
//    private  Product mapToEntity(ProductRequest productRequest){
//        Product product = new Product();
//        product.setName(productRequest.name());
//        product.setDescription(productRequest.description());
//        product.setPrice(productRequest.price());
//        return product;
//    }
//    //mapToResponse -> convert Entity to Response
//    private ProductResponse mapToResponse (Product product){
//        return new ProductResponse(
//                product.getId(),
//                product.getName(),
//                product.getDescription(),
//                product.getPrice()
//        );
//    }

    @Override
    public ProductResponse createProduct( ProductRequest productRequest) {
//        create entity product from the request
        var product = productMapper.mapToProduct(productRequest);
//        check if the category exist
      var category = categoryRepository.findById(productRequest.categoryId()).orElseThrow(
              ()-> new NoSuchElementException("Category with id = " + productRequest.categoryId() + "not found")
      );
      product.setCategory(category);
      //convert Set<Long> to Set<Tag>
      // getReferenceById vs findById
      if (productRequest.tagIds() != null && !productRequest.tagIds().isEmpty()){
          Set<Tag> tags = productRequest.tagIds().stream()
                  .map(tagId -> tagRepository.getReferenceById(tagId))
                  .collect(Collectors.toSet());
          product.setTags(tags);
      }
//        set ID using nextId
        product.setUserId(1);
//        insert the data to the table only need to
//        repository.save(entity) = insert
        return productMapper.mabToResponse(productRepository.save(product));

    }
    @Override
    public List<ProductResponse> findAllProducts() {
//        repository.finAll()
        return productRepository.findAll()
                .stream()
                .map(productMapper::mabToResponse)
                .toList();
    }
    @Override
    public Page<ProductResponse> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::mabToResponse);
    }

    @Override
    public Page<ProductResponse> findAllProducts(String keyword, Pageable pageable) {
        return productRepository.findProductsByNameContainingIgnoreCase(keyword, keyword, pageable)
                .map(productMapper::mabToResponse);
    }

    @Override
    public ProductResponse findProductById(Integer id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product with ID = " + id + " not found"));
        return  productMapper.mabToResponse(product);
    }
    @Override
    public ProductResponse updateProduct(Integer id, UpdateProductRequest updateProductRequest) {
  // find existing product
        var existingProduct = productRepository.findById(id).orElseThrow(() -> new  NoSuchElementException("Product with ID = "+id+" not found"));
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
      productRepository.save(existingProduct);
        return productMapper.mabToResponse(existingProduct);
    }
    @Override
    public boolean deleteProduct(Integer id) {
       if (productRepository.existsById(id)){
           productRepository.deleteById(id);
           return  true;
       }
       return false;
    }
}
