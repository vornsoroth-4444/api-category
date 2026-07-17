package org.soroth.procuctapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_tbl")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    //must include this
//    private String keycloakUserId;
    private String username;
    @Column(unique = true, nullable = false)
    private String keycloakId;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Profile profile;
}
