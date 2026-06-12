package org.soroth.procuctapi.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "product_tbl")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description ;
    private Float price;
    private  Integer userId ; // user that create the product !

//    private Integer categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "category_id" )
    private Category category;

}