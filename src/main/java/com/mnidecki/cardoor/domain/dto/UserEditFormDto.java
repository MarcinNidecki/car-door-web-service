package com.mnidecki.cardoor.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class UserEditFormDto implements UserDto {

    private Long id;
    @Length(min = 3, message = "The fistsname must be at least {min} characters long")
    private String firstname;
    @NotNull
    @Length(min = 3, message = "The lastname must be at least {min} characters long")
    private String lastname;
    @Email
    @Length(min = 6, message = "The email must be at least {min} characters long")
    private String email;
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
    @NotNull
    @Length(min = 5, message = "The zipcode must be at least {min} characters long")
    private String zipCode;

    public UserEditFormDto(Long id, @NotNull @Length(min = 3, message = "The fistsname must be at least {min} characters long") String firstname, @NotNull @Length(min = 3, message = "The lastname must be at least {min} characters long") String lastname, @Email @Length(min = 6, message = "The email must be at least {min} characters long") String email, @NotNull @Length(min = 3, message = "The username must be at least {min} characters long") String username, @NotNull @Length(min = 8, message = "The address must be at least {min} characters long") String addressLine1, String addressLine2, @NotNull @Length(min = 3, message = "The country must be at least {min} characters long") String country, @Length(min = 6, message = "The state must be at least {min} characters long") String state, @NotNull @Length(min = 5, message = "The zipcode must be at least {min} characters long") String zipCode) {
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

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "UserEditFormDto{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }

    public static class UserEditFormDtoBuilder {
        private Long id;
        private String firstname;
        private String lastname;
        private String email;
        private String username;
        private String addressLine1;
        private String addressLine2;
        private String country;
        private String state;
        private String zipCode;

        public UserEditFormDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserEditFormDtoBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public UserEditFormDtoBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public UserEditFormDtoBuilder email(String email) {
            this.email = email;
            return this;
        }


        public UserEditFormDtoBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserEditFormDtoBuilder addressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        public UserEditFormDtoBuilder addressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        public UserEditFormDtoBuilder country(String country) {
            this.country = country;
            return this;
        }

        public UserEditFormDtoBuilder state(String state) {
            this.state = state;
            return this;
        }

        public UserEditFormDtoBuilder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public UserEditFormDto build() {
            return new UserEditFormDto(id, firstname, lastname, email, username, addressLine1, addressLine2, country, state, zipCode);
        }
    }
}



