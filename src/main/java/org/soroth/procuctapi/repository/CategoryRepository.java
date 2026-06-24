package org.soroth.procuctapi.repository;

import org.soroth.procuctapi.entity.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category , Integer> {
    Boolean existsByName (String name);
    List<Category> findByNameContainingIgnoreCase(String name);

    //pagination
    List<Category>findByParentCategoryIsNull(Sort sort);
}
