package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.User;
import com.mnidecki.cardoor.domain.dto.UserDto;
import com.mnidecki.cardoor.domain.dto.UserEditFormDto;
import com.mnidecki.cardoor.domain.dto.UserLongFormDto;
import com.mnidecki.cardoor.domain.dto.UserQuickFormDto;
import com.mnidecki.cardoor.services.DBService.DBUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private DBUserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserLongFormDto mapToUserDto(User user) {
        if (user.getAddressLine1() == null) {
            return new UserLongFormDto.UserLongFormDtoBuilder()
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .build();
        } else {
            return new UserLongFormDto.UserLongFormDtoBuilder()
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .addressLine1(user.getAddressLine1())
                    .addressLine2(user.getAddressLine2())
                    .country(user.getCountry())
                    .state(user.getState())
                    .zipCode(user.getZipCode())
                    .build();
        }
    }


    public UserEditFormDto mapToEditFormUserDto(User user) {

        return new UserEditFormDto.UserEditFormDtoBuilder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .username(user.getUsername())
                .addressLine1(user.getAddressLine1())
                .addressLine2(user.getAddressLine2())
                .country(user.getCountry())
                .state(user.getState())
                .zipCode(user.getZipCode())
                .build();
    }

    public User mapToUser(UserDto userDto) {
        User user = new User();

        if (userDto instanceof UserLongFormDto) {
            UserLongFormDto longUser = (UserLongFormDto) userDto;
            user = new User.UserBuilder()
                    .firstname(longUser.getFirstname())
                    .lastname(longUser.getLastname())
                    .email(longUser.getEmail())
                    .password(bCryptPasswordEncoder.encode(longUser.getPassword()))
                    .username(longUser.getUsername())
                    .addressLine1(longUser.getAddressLine1())
                    .addressLine2(longUser.getAddressLine2())
                    .country(longUser.getCountry())
                    .zipCode(longUser.getZipCode())
                    .build();
        }
        if (userDto instanceof UserEditFormDto) {
            UserEditFormDto userEditFormDto = (UserEditFormDto) userDto;
            user = userService.getUserFromAuthentication();
            user.setFirstname(userEditFormDto.getFirstname());
            user.setLastname(userEditFormDto.getLastname());
            user.setUsername(userEditFormDto.getUsername());
            user.setAddressLine1(userEditFormDto.getAddressLine1());
            user.setAddressLine2(userEditFormDto.getAddressLine2());
            user.setCountry(userEditFormDto.getCountry());
            user.setState(userEditFormDto.getState());
            user.setZipCode(userEditFormDto.getZipCode());
        }

        if (userDto instanceof UserQuickFormDto) {
            UserQuickFormDto userQuickFormDto = (UserQuickFormDto) userDto;
            user = new User.UserBuilder()
                    .firstname(userQuickFormDto.getFirstname())
                    .lastname(userQuickFormDto.getLastname())
                    .email(userQuickFormDto.getEmail())
                    .password(bCryptPasswordEncoder.encode(userQuickFormDto.getPassword()))
                    .build();
        }
        return user;
    }
}