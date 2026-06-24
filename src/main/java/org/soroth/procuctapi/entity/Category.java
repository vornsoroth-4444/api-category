package org.soroth.procuctapi.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity(name = "category_tbl")
@Table(name = "category_tbl")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private  String description;
    private Boolean isDeleted = false;
    private String icon;  // store the icon url

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent category id")
    private  Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    List<Category> subCategories = new ArrayList<>();





//    one category can have many products
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
