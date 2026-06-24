package org.soroth.procuctapi.mapper;

import org.mapstruct.Mapper;
import org.soroth.procuctapi.dto.request.ProductRequest;
import org.soroth.procuctapi.dto.response.ProductResponse;
import org.soroth.procuctapi.entity.Product;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class} )
public interface ProductMapper {
    ProductResponse mabToResponse (Product request);
    Product mapToProduct (ProductRequest request);

}
