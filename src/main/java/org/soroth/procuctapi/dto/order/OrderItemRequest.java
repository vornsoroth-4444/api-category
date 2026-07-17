package org.soroth.procuctapi.dto.order;

public record OrderItemRequest (
        Integer productId,
        Integer qty
){
}
