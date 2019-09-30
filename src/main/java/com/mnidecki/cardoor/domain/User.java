package com.mnidecki.cardoor.domain;

import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.car.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;
    @NotNull
    @Column(name = "first_name")
    private String firstname;
    @NotNull
    @Column(name = "last_name")
    private String lastname;
    @NotNull
    @Email
    @Column(name = "email", unique = true)
    private String email;
    @NotNull
    @Size(min = 4)
    @Column(name = "password")
    private String password;
    @Column(name = "user_name", unique = true)
    private String username;
    @Column(name = "address_line1")
    private String addressLine1;
    @Column(name = "address_line2")
    private String addressLine2;
    @Column(name = "country")
    private String country;
    @Column(name = "state")
    private String state;
    @Column(name = "zip_code")
    private String zipCode;
    @Column(name = "status")
    private Integer status;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "join_user_user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_role_id", referencedColumnName = "user_role_id")})
    private Set<UserRole> roles = new HashSet<>();

    @OneToMany(targetEntity = Booking.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Booking> bookingList = new ArrayList<>();

    @OneToMany(targetEntity = Comment.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<Comment> commentsList = new HashSet<>();

    public User(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public User(@NotNull String firstname, @NotNull String lastname, @NotNull String email, @NotNull String password, Integer status) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public User(Long id, @NotNull String firstname, @NotNull String lastname, @NotNull String email, @NotNull String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public User(Long id, String firstname, String lastname, String email, String password, String username, String addressLine1, String addressLine2, String country, String state, String zipCode, Integer status) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.username = username;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.country = country;
        this.state = state;
        this.zipCode = zipCode;
        this.status = status;


    }

    public User(String firstname, String lastname, String email, String password, String username, String addressLine1, String addressLine2, String country, String state, String zipCode) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.username = username;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.country = country;
        this.state = state;
        this.zipCode = zipCode;
    }

    public User(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
}
