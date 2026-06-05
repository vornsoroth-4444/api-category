package org.soroth.procuctapi.service.category;

import org.soroth.procuctapi.dto.category.CategoryRequest;
import org.soroth.procuctapi.dto.category.CategoryResponse;
import org.soroth.procuctapi.dto.category.UpdateCategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    List<CategoryResponse> findAllCategory();
    CategoryResponse findCategoryById(int id);
    CategoryResponse updateCategory(Integer id, UpdateCategoryRequest updateCategoryRequest);
    boolean deleteCategory(Integer id);
}
