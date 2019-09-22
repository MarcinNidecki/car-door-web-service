package com.mnidecki.cardoor.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "user_role")
public class UserRole {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_role_id")
    private Long id;

    @NotNull
    @Column(name = "role_name")
    private String roleName;

    @NotNull
    @Column(name = "role_desc")
    private String roleDesc;
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}

