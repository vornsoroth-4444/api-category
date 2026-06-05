package org.soroth.procuctapi.service.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.soroth.procuctapi.dto.category.CategoryRequest;
import org.soroth.procuctapi.dto.category.CategoryResponse;
import org.soroth.procuctapi.dto.category.UpdateCategoryRequest;
import org.soroth.procuctapi.entity.category.Category;
import org.soroth.procuctapi.repository.category.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private  final  CategoryRepository categoryRepository;
    private  Integer nextId = 1004;

//    mapToEntity -> convert Request to Entity
    private Category mapToEntity(CategoryRequest categoryRequest){
        Category category = new Category();
        category.setName(categoryRequest.name());
        category.setDescription(categoryRequest.description());
        category.setPrice(categoryRequest.price());
        return category;
    }
//    mapToResponse -> convert Entity to Response
    private  CategoryResponse mapToResponse (Category category){
        return  new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getPrice()
        );
    }
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
      var category = mapToEntity(categoryRequest);
      category.setId(nextId++);
      return mapToResponse(categoryRepository.createCategory(category));
    }

    @Override
    public List<CategoryResponse> findAllCategory() {
        return categoryRepository.getCategoryList()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CategoryResponse findCategoryById(int id) {
         var category = categoryRepository.findCategoryById(id);
         if(category == null ){
             log.info("Category with id {} not found", id);
             throw new NoSuchElementException("Category with id " + id + " not found");
         }
         return mapToResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(Integer id, UpdateCategoryRequest updateCategoryRequest) {
        var existingCategory = categoryRepository.findCategoryById(id);
        if(existingCategory  == null ) {
            log.info("Category with id {} not found", id);
            throw new NoSuchElementException("Category with id " + id + " not found");
        }
        if(updateCategoryRequest.name() != null)
            existingCategory.setName(updateCategoryRequest.name());
        if (updateCategoryRequest.description() != null )
            existingCategory.setDescription(updateCategoryRequest.description());
        if (updateCategoryRequest.price() != null )
            existingCategory.setPrice(updateCategoryRequest.price());
//        save the update category
        var updateCategory = categoryRepository.updateCategory(id,existingCategory);
        return mapToResponse(updateCategory);
    }

    @Override
    public boolean deleteCategory(Integer id) {
      return  categoryRepository.deleteCategoryById(id);
    }
}
