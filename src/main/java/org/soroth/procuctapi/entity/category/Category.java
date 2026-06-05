package org.soroth.procuctapi.entity.category;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Category {
    private Integer id;
    private String name;
    private  String description;
    private float price;
    private int userId ;
}
