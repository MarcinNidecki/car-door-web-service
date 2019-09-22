package com.mnidecki.cardoor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {


    private Long id;
    @NotNull
    @Length(min = 3, message = "The fistsname must be at least {min} characters long")
    private String firstname;
    @NotNull
    @Length(min = 3, message = "The lastname must be at least {min} characters long")
    private String lastname;
    @Email
    @Length(min = 6, message = "The email must be at least {min} characters long")
    private String email;
    @NotNull
    @Length(min = 6, message = "The password must be at least {min} characters long")
    private String password;
    @NotNull
    @Length(min = 3, message = "The username must be at least {min} characters long")
    private String username;
    @NotNull
    @Length(min = 8, message = "The address must be at least {min} characters long")
    private String addressLine1;
    private String addressLine2;
    @NotNull
    @Length(min = 3, message = "The country must be at least {min} characters long")
    private String country;
    @Length(min = 6, message = "The state must be at least {min} characters long")
    private String state;
    @NotNull @Length(min = 5, message = "The zipcode must be at least {min} characters long")
    private String zipCode;
    private Integer status;

    public UserDto(Long id, String firstname, String lastname,  String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;

    }
    public UserDto(Long id, String firstname, String lastname, String email, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
    public UserDto(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public UserDto(String firstname, String lastname, String email, String password, String username, String addressLine1, String addressLine2, String country, String state, String zipCode) {
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

    public UserDto(Long id, String firstname, String lastname, String email, String username, String addressLine1, String addressLine2, String country, String state, String zipCode) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.country = country;
        this.state = state;
        this.zipCode = zipCode;
    }
}



