package org.soroth.procuctapi.restcontrollers;

import lombok.RequiredArgsConstructor;
import org.soroth.procuctapi.dto.request.TagRequest;
import org.soroth.procuctapi.dto.response.TagResponse;
import org.soroth.procuctapi.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tags")
public class TagRestController {
    private final TagService tagService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TagResponse create(@RequestBody TagRequest tagRequest){
        return tagService.createTag(tagRequest);
    }
    @GetMapping
    public Page<TagResponse> getAll(Pageable pageable){
        return tagService.getAllTags(pageable);
    }
}
