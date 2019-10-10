package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.User;
import com.mnidecki.cardoor.domain.dto.UserDto;
import com.mnidecki.cardoor.domain.dto.UserEditFormDto;
import com.mnidecki.cardoor.domain.dto.UserRegisterLongFormDto;
import com.mnidecki.cardoor.domain.dto.UserRegisterQuickFormDto;
import com.mnidecki.cardoor.services.DBService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

        if (userDto instanceof UserRegisterLongFormDto) {
            UserRegisterLongFormDto longUser = (UserRegisterLongFormDto) userDto;
            user = new User.UserBuilder()
                    .firstname(longUser.getFirstname())
                    .lastname(longUser.getLastname())
                    .email(longUser.getEmail())
                    .password(bCryptPasswordEncoder.encode(longUser.getPassword()))
                    .username(longUser.getUsername())
                    .addressLine1(longUser.getAddressLine1())
                    .addressLine2(longUser.getAddressLine2())
                    .country(longUser.getCountry())
                    .state(longUser.getState())
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

        if (userDto instanceof UserRegisterQuickFormDto) {
            UserRegisterQuickFormDto userRegisterQuickFormDto = (UserRegisterQuickFormDto) userDto;
            user = new User.UserBuilder()
                    .firstname(userRegisterQuickFormDto.getFirstname())
                    .lastname(userRegisterQuickFormDto.getLastname())
                    .email(userRegisterQuickFormDto.getEmail())
                    .password(bCryptPasswordEncoder.encode(userRegisterQuickFormDto.getPassword()))
                    .build();
        }
        return user;
    }
}