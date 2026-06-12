package org.soroth.procuctapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.soroth.procuctapi.advisor.ResourceAlreadyExistException;
import org.soroth.procuctapi.dto.CategoryRequest;
import org.soroth.procuctapi.dto.CategoryResponse;
import org.soroth.procuctapi.dto.UpdateCategoryRequest;
import org.soroth.procuctapi.entity.Category;
import org.soroth.procuctapi.mapper.CategoryMapper;
import org.soroth.procuctapi.repository.CategoryRepository;
import org.soroth.procuctapi.repository.CategoryRepositoryOld;
import org.soroth.procuctapi.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collector;

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
      Category category = categoryMapper.toEntity(request);
        // TODO: check if the name already exist
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
}
