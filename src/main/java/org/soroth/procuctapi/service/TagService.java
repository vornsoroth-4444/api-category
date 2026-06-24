package org.soroth.procuctapi.service;

import org.soroth.procuctapi.dto.TagRequest;
import org.soroth.procuctapi.dto.TagResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {
    TagResponse createTag(TagRequest request);
    Page<TagResponse> getAllTags(Pageable pageable);
}
