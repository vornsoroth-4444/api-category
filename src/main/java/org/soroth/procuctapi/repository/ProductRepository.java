package org.soroth.procuctapi.repository;

import org.soroth.procuctapi.entity.Product;
import org.soroth.procuctapi.entity.ProductFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> , JpaSpecificationExecutor<Product> {
//    Page<Product> findProductsByNameContainingIgnoreCase(String name, String description, Pageable pageable , ProductFilter filter);
}
