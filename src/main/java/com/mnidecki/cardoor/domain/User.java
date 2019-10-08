package com.mnidecki.cardoor.domain;

import com.mnidecki.cardoor.domain.booking.Booking;
import com.mnidecki.cardoor.domain.car.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Entity(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "last_name")
    private String lastname;
    @Column(name = "email")
    private String email;
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

    public User(Long id, String firstname, String lastname, String email, String password, String username,
                String addressLine1, String addressLine2, String country, String state, String zipCode, Integer status, Set<UserRole> roles, List<Booking> bookingList, Set<Comment> commentsList) {
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
        this.roles = roles;
        this.bookingList = bookingList;
        this.commentsList = commentsList;
    }

    public static class UserBuilder {

        private String firstname;
        private String lastname;
        private String email;
        private String password;
        private Integer status;
        private Long id;
        private String username;
        private String addressLine1;
        private String addressLine2;
        private String country;
        private String state;
        private String zipCode;
        private Set<UserRole> roles;
        private List<Booking> bookingList;
        private Set<Comment> commentsList;

        public UserBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public UserBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder status(Integer status) {
            this.status = status;
            return this;
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder addressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        public UserBuilder addressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        public UserBuilder country(String country) {
            this.country = country;
            return this;
        }

        public UserBuilder state(String state) {
            this.state = state;
            return this;
        }

        public UserBuilder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public UserBuilder roles(Set<UserRole> roles) {
            this.roles = roles;
            return this;
        }

        public UserBuilder bookingList(List<Booking> bookingList) {
            this.bookingList = bookingList;
            return this;
        }

        public UserBuilder commentsList(Set<Comment> commentsList) {
            this.commentsList = commentsList;
            return this;
        }

        public User build() {
            return new User(id, firstname, lastname, email, password, username, addressLine1, addressLine2, country, state, zipCode, status, roles, bookingList, commentsList);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", status=" + status +
                ", roles=" + roles +
                ", bookingList=" + bookingList +
                ", commentsList=" + commentsList +
                '}';
    }
}
