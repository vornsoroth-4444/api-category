package org.soroth.procuctapi.mapper;

import org.mapstruct.Mapper;
import org.soroth.procuctapi.dto.TagRequest;
import org.soroth.procuctapi.dto.TagResponse;
import org.soroth.procuctapi.entity.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagResponse toResponse(Tag tag);
    Tag toEntity(TagRequest tagRequest);
}
