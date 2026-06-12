package org.soroth.procuctapi.restcontrollers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.soroth.procuctapi.dto.CategoryRequest;
import org.soroth.procuctapi.dto.CategoryResponse;
import org.soroth.procuctapi.dto.UpdateCategoryRequest;
import org.soroth.procuctapi.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/categories")
public class CategoryRestController {
    private  final CategoryService categoryService;

//    @GetMapping
//    public List<CategoryResponse> getAllCategories(){
//        return categoryService.findAllCategory();
//    }
    @GetMapping
    public Page<CategoryResponse> getAllCategory(Pageable pageable){
        return categoryService.getAllCategory(pageable);
    }
    @GetMapping("/search")
    public List<CategoryResponse> getCategoryByName(@RequestParam String name){
        return categoryService.findByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        return categoryService.createCategory(categoryRequest);
    }
    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable int id){
        return  categoryService.findCategoryById(id);
    }
    @PatchMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Integer id, @RequestBody UpdateCategoryRequest updateCategoryRequest){
        return  categoryService.updateCategory(id,updateCategoryRequest);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Integer id){
         categoryService.deleteCategory(id);
    }
}
