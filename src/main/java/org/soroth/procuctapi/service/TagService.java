package org.soroth.procuctapi.service;

import org.soroth.procuctapi.dto.request.TagRequest;
import org.soroth.procuctapi.dto.response.TagResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {
    TagResponse createTag(TagRequest request);
    Page<TagResponse> getAllTags(Pageable pageable);
}
