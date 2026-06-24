package org.soroth.procuctapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.soroth.procuctapi.dto.request.TagRequest;
import org.soroth.procuctapi.dto.response.TagResponse;
import org.soroth.procuctapi.mapper.TagMapper;
import org.soroth.procuctapi.repository.TagRepository;
import org.soroth.procuctapi.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagResponse createTag(TagRequest request) {
       var tag =  tagMapper.toEntity(request);
        return tagMapper.toResponse(tagRepository.save(tag));
    }

    @Override
    public Page<TagResponse> getAllTags(Pageable pageable) {
      return  tagRepository
              .findAll(pageable)
              .map(tagMapper::toResponse);
    }
}
