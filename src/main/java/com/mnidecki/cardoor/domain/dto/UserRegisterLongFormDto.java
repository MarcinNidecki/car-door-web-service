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
public class UserRegisterLongFormDto implements UserDto {

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
    @Length(min = 5, message = "The password is too short. Please try again.")
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
    @NotNull
    @Length(min = 5, message = "The zipcode must be at least {min} characters long")
    private String zipCode;

    public UserRegisterLongFormDto(@NotNull @Length(min = 3, message = "The fistsname must be at least {min} characters long") String firstname, @NotNull @Length(min = 3, message = "The lastname must be at least {min} characters long") String lastname, @Email @Length(min = 6, message = "The email must be at least {min} characters long") String email, @NotNull @Length(min = 5, message = "The password is too short. Please try again.") String password, @NotNull @Length(min = 3, message = "The username must be at least {min} characters long") String username, @NotNull @Length(min = 8, message = "The address must be at least {min} characters long") String addressLine1, String addressLine2, @NotNull @Length(min = 3, message = "The country must be at least {min} characters long") String country, @Length(min = 6, message = "The state must be at least {min} characters long") String state, @NotNull @Length(min = 5, message = "The zipcode must be at least {min} characters long") String zipCode) {
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

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "UserLongFormDto{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }

    public static class UserLongFormDtoBuilder {
        private String firstname;
        private String lastname;
        private String email;
        private String password;
        private String username;
        private String addressLine1;
        private String addressLine2;
        private String country;
        private String state;
        private String zipCode;

        public UserLongFormDtoBuilder firstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public UserLongFormDtoBuilder lastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public UserLongFormDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserLongFormDtoBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserLongFormDtoBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserLongFormDtoBuilder addressLine1(String addressLine1) {
            this.addressLine1 = addressLine1;
            return this;
        }

        public UserLongFormDtoBuilder addressLine2(String addressLine2) {
            this.addressLine2 = addressLine2;
            return this;
        }

        public UserLongFormDtoBuilder country(String country) {
            this.country = country;
            return this;
        }

        public UserLongFormDtoBuilder state(String state) {
            this.state = state;
            return this;
        }

        public UserLongFormDtoBuilder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public UserRegisterLongFormDto build() {
            return new UserRegisterLongFormDto(firstname, lastname, email, password, username, addressLine1, addressLine2, country, state, zipCode);
        }
    }
}



