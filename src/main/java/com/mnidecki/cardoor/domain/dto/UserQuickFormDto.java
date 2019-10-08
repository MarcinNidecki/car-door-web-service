package com.mnidecki.cardoor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserQuickFormDto implements UserDto {
    @NotNull
    @Length(min = 3, message = "The fistsname must be at least {min} characters long")
    private String firstname;
    @NotNull
    @Length(min = 3, message = "The lastname must be at least {min} characters long")
    private String lastname;
    @NotNull
    @Length(min = 3, message = "The email must be at least {min} characters long")
    private String email;
    @NotNull
    @Length(min = 5, message = "The password is too short. Please try again.")
    private String password;

    @Override
    public String getEmail() {
        return email;
    }
}
