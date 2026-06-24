package org.soroth.procuctapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.soroth.procuctapi.advisor.ResourceAlreadyExistException;
import org.soroth.procuctapi.dto.request.CategoryRequest;
import org.soroth.procuctapi.dto.response.CategoryResponse;
import org.soroth.procuctapi.dto.request.UpdateCategoryRequest;
import org.soroth.procuctapi.entity.Category;
import org.soroth.procuctapi.mapper.CategoryMapper;
import org.soroth.procuctapi.repository.CategoryRepository;
import org.soroth.procuctapi.repository.ProductRepository;
import org.soroth.procuctapi.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
//    private  final CategoryRepositoryOld categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        //map from request to entity
      Category category = categoryMapper.toEntity(request);
      // if the parent_category_id provided ,we validate id first
        if (request.parentCategoryId()!= null){
            //check if it exists
            var  parentCategory = categoryRepository.findById(request.parentCategoryId()).orElseThrow(
                    ()-> new NoSuchElementException("Parent category with id = "+ request.parentCategoryId()+ "doesn't exists !")
            );
            category.setParentCategory(parentCategory);
        }
        // TODO: check if the name already exist
        // derived query
        if (categoryRepository.existsByName(request.name())){
           throw new ResourceAlreadyExistException("Category with name : " + request.name() + " already exist");
        }
        var newCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(newCategory);
    }

    @Override
    public List<CategoryResponse> findAllCategory() {
//        return categoryRepository.getCategoryList()
//                .stream()
//                .map(this::mapToResponse)
//                .toList();

        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse findCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category with id " + id + " not found"));
        return categoryMapper.toResponse(category);
    }

    @Override
    public Page<CategoryResponse> getAllCategory(Pageable pageable) {
        return  categoryRepository.findAll(pageable).map(categoryMapper::toResponse);
    }

    @Override
    public List<CategoryResponse> findByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse updateCategory(Integer id, UpdateCategoryRequest updateCategoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category with id " + id + " not found"));
        
        if (updateCategoryRequest.name() != null) {
            category.setName(updateCategoryRequest.name());
        }
        if (updateCategoryRequest.description() != null) {
            category.setDescription(updateCategoryRequest.description());
        }
        
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Integer id) {
        if (!categoryRepository.existsById(id)){
            throw new NoSuchElementException("Category with Id : " + id + " does not exist");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryResponse> findParentCategories(String sortDirection) {
        //use "name" of the category to sort
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        if ("desc".equalsIgnoreCase(sortDirection)){
            sort = sort.descending();
        }else sort = sort.ascending();

        return categoryRepository.findByParentCategoryIsNull(sort)
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }
}
