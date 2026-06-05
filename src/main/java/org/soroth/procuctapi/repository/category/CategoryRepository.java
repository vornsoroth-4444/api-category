package org.soroth.procuctapi.repository.category;

import org.soroth.procuctapi.entity.category.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRepository {
    private  final List<Category> categories = new ArrayList<>(){{
        add(new Category(1001, "Beverages", "A beverage is any potable liquid prepared for human consumption  ", 13.4f, 1));
        add(new Category(1002, "Snacks", "Snacks are small portions of food eaten between meals ", 10.2f, 2));
        add( new Category(1003, "Dairy", "Dairy products are foods derived from milk ", 20.5f, 3));
    }};
    public List<Category> getCategoryList(){
        return categories;
    }
    public Category createCategory(Category category){
        categories.add(category);
        return category;
    }
    public Category findCategoryById(Integer id){
        return categories.stream()
                .filter(category -> category.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    public boolean deleteCategoryById(Integer id) {
        return categories.removeIf(category -> category.getId().equals(id));
    }
    public Category updateCategory(Integer id, Category updatedCategory){
        for(int i = 0; i<categories.size(); i++){
            var category = categories.get(i);
            if (category.getId().equals(id)){
                categories.set(i, updatedCategory);
                return updatedCategory;
            }
        }
        return null;
    }
}
