package org.soroth.procuctapi.dto.request;

public record ProductSearchToRequest (
        String name,
        String description,
        String categoryName
){
}
