package com.erivan.apitest.domain;

import lombok.*;

import javax.persistence.*;

@Data //getter, setter, equalshascode, tostring
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;
}
