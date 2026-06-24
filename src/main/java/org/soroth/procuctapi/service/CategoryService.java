package org.soroth.procuctapi.service;

import org.soroth.procuctapi.dto.CategoryRequest;
import org.soroth.procuctapi.dto.CategoryResponse;
import org.soroth.procuctapi.dto.UpdateCategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    List<CategoryResponse> findAllCategory();
    CategoryResponse findCategoryById(Integer id);

    //    get all with Pagination (follow product sample)
    //    soft delete category(changing the value of isDeleted)
    Page<CategoryResponse> getAllCategory(Pageable pageable);

   List<CategoryResponse> findByName(String name);
    CategoryResponse updateCategory(Integer id, UpdateCategoryRequest updateCategoryRequest);
    void deleteCategory(Integer id);


    // asc, desc
    List<CategoryResponse> findParentCategories(String sortDirection);
}
