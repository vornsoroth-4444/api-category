package org.soroth.procuctapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soroth.procuctapi.dto.request.ProductRequest;
import org.soroth.procuctapi.dto.response.ProductResponse;
import org.soroth.procuctapi.entity.Product;
import org.soroth.procuctapi.entity.Tag;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class} )
public interface ProductMapper {
    // turn tags object into pure string
    // ["iphone","17 pro max" , "apple"]
    // [{"id":1,....
    //@Mapping(target = "tags", ignore = true)
    @Mapping(target = "tags",source = "tags")
    ProductResponse mabToResponse (Product request);
    Product mapToProduct (ProductRequest request);

    //method for converting the Set<Tag> to Set<String>
    default Set<String> mapToString(Set<Tag> tags){
        return  tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());
    }
}
