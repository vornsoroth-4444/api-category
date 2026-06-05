package org.soroth.procuctapi.restcontrollers.category;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.soroth.procuctapi.dto.category.CategoryRequest;
import org.soroth.procuctapi.dto.category.CategoryResponse;
import org.soroth.procuctapi.dto.category.UpdateCategoryRequest;
import org.soroth.procuctapi.service.category.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/categories")
public class CategoryRestController {
    private  final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> getAllCategories(){
        return categoryService.findAllCategory();
    }
    @PostMapping
    public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        return categoryService.createCategory(categoryRequest);
    }
    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable int id){
        return  categoryService.findCategoryById(id);
    }
    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Integer id, @RequestBody UpdateCategoryRequest updateCategoryRequest){
        return  categoryService.updateCategory(id,updateCategoryRequest);
    }
    @DeleteMapping("/{id}")
    public boolean deleteCategory(@PathVariable Integer id){
        return categoryService.deleteCategory(id);
    }
}
