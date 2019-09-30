package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.User;
import com.mnidecki.cardoor.domain.dto.UserDto;
import com.mnidecki.cardoor.domain.dto.UserQuickRegistrationFormDto;
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

    public UserDto maptoUserDto(User user) {
        if (user.getAddressLine1() == null) {
            return new UserDto(
                    user.getId(),
                    user.getFirstname(),
                    user.getLastname(),
                    user.getEmail()
            );
        } else {
            return new UserDto(
                    user.getId(),
                    user.getFirstname(),
                    user.getLastname(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getAddressLine1(),
                    user.getAddressLine2(),
                    user.getCountry(),
                    user.getState(),
                    user.getZipCode()


            );
        }
    }

    public User mapToUser(UserDto userDto) {

        if (userDto.getId() != null && userDto.getAddressLine1() != null) {
            User user = userService.findUserById(userDto.getId());
            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            user.setUsername(userDto.getUsername());
            user.setAddressLine1(userDto.getAddressLine1());
            user.setAddressLine2(userDto.getAddressLine2());
            user.setCountry(userDto.getCountry());
            user.setState(userDto.getState());
            user.setZipCode(userDto.getZipCode());
            return user;

        } else {
            return new User(
                    userDto.getFirstname(),
                    userDto.getLastname(),
                    userDto.getEmail(),
                    bCryptPasswordEncoder.encode(userDto.getPassword()),
                    userDto.getUsername(),
                    userDto.getAddressLine1(),
                    userDto.getAddressLine2(),
                    userDto.getCountry(),
                    userDto.getState(),
                    userDto.getZipCode());
        }
    }

    public User mapToUser(UserQuickRegistrationFormDto userDto) {
        return new User(
                userDto.getFirstname(),
                userDto.getLastname(),
                userDto.getEmail(),
                bCryptPasswordEncoder.encode(userDto.getPassword()));

    }
}
