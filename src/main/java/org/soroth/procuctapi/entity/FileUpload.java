package org.soroth.procuctapi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "file_tbl")
public class FileUpload {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String caption;
    @Column(nullable = false)
    private Long size;   // bytes
    @Column(nullable = false , length = 15)
    private String extension;  // png
    @Column(nullable = false)
    private String mediaType;  // file, images ,documents

}
