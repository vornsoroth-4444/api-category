package org.soroth.procuctapi.dto;

public record ProductSearchToRequest (
        String name,
        String description,
        String categoryName
){
}
