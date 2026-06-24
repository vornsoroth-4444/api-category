package org.soroth.procuctapi.mapper;

import org.mapstruct.Mapper;
import org.soroth.procuctapi.dto.request.CategoryRequest;
import org.soroth.procuctapi.dto.response.CategoryResponse;
import org.soroth.procuctapi.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toResponse(Category category);
   Category toEntity(CategoryRequest request);
}
