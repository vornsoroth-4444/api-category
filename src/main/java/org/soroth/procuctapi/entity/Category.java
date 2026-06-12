package org.soroth.procuctapi.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity(name = "category_tbl")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private  String description;
    private Boolean isDeleted = false;

//    one category can have many products
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
