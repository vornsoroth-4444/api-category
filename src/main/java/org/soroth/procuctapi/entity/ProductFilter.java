package org.soroth.procuctapi.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductFilter {
    private   String name;
    private String code;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean isAvailable;
    private  String categoryId;

    // manytomany
    private List<String> tagName;
}
