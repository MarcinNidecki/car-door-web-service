package com.mnidecki.cardoor.mapper;

import com.mnidecki.cardoor.domain.User;
import com.mnidecki.cardoor.domain.dto.UserEditFormDto;
import com.mnidecki.cardoor.domain.dto.UserRegisterLongFormDto;
import com.mnidecki.cardoor.domain.dto.UserRegisterQuickFormDto;
import com.mnidecki.cardoor.services.DBService.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private UserService userService;

    @Test
    public void mapToEditFormUserDto() {
    }

    @Test
    public void ShouldMapUserQuickFormUserDtoToUser() {
        //Given
        UserRegisterQuickFormDto userDto = new UserRegisterQuickFormDto("John","Kovalsky", "email@wp.pl","password");

        when(bCryptPasswordEncoder.encode(userDto.getPassword())).thenReturn("AvDww872nccDAa2131eszx");

        //When
        User user = userMapper.mapToUser(userDto);

        //Then

        assertEquals("John", user.getFirstname());
        assertEquals("Kovalsky", user.getLastname());
        assertEquals("email@wp.pl", user.getEmail());
        assertEquals("AvDww872nccDAa2131eszx", user.getPassword());
    }

    @Test
    public void ShouldMapUserEditFormDtoToUser() {
        //Given
        User signInUser = new User.UserBuilder()
                .id(2L)
                .password("AvDww872nccDAa2131eszx")
                .firstname("John")
                .lastname("Kovalsky")
                .email("email@wp.pl")
                .addressLine1("old address line 1")
                .addressLine2("old address line 2")
                .country("Poland")
                .state("Opolskie")
                .zipCode("22-233")
                .build();


        UserEditFormDto userDto = new UserEditFormDto.UserEditFormDtoBuilder()
                .firstname("John")
                .lastname("Kovalsky")
                .email("email@wp.pl")
                .addressLine1("address line 1")
                .addressLine2("address line 2")
                .country("Poland")
                .state("Slaskie")
                .zipCode("44-266")
                .build();


        when(userService.getUserFromAuthentication()).thenReturn(signInUser);

        //When
        User user = userMapper.mapToUser(userDto);

        //Then
        assertEquals(Long.valueOf(2), user.getId());
        assertEquals("John", user.getFirstname());
        assertEquals("Kovalsky", user.getLastname());
        assertEquals("email@wp.pl", user.getEmail());
        assertEquals("address line 1", user.getAddressLine1());
        assertEquals("address line 2", user.getAddressLine2());
        assertEquals("Poland", user.getCountry());
        assertEquals("Slaskie", user.getState());
        assertEquals("44-266", user.getZipCode());
        assertEquals("AvDww872nccDAa2131eszx", user.getPassword());

    }

    @Test
    public void ShouldMapUserRegisterLongFormDtoToUser() {
        //Given
        UserRegisterLongFormDto userDto = new UserRegisterLongFormDto.UserLongFormDtoBuilder()
                .firstname("John")
                .lastname("Kovalsky")
                .email("email@wp.pl")
                .password("password")
                .username("username")
                .addressLine1("address line 1")
                .addressLine2("address line 2")
                .country("Poland")
                .state("Slaskie")
                .zipCode("44-266")
                .build();

        when(bCryptPasswordEncoder.encode(userDto.getPassword())).thenReturn("AvDww872nccDAa2131eszx");

        //When
        User user = userMapper.mapToUser(userDto);

        //Then
        assertEquals("John", user.getFirstname());
        assertEquals("Kovalsky", user.getLastname());
        assertEquals("email@wp.pl", user.getEmail());
        assertEquals("address line 1", user.getAddressLine1());
        assertEquals("address line 2", user.getAddressLine2());
        assertEquals("Poland", user.getCountry());
        assertEquals("Slaskie", user.getState());
        assertEquals("44-266", user.getZipCode());
        assertEquals("AvDww872nccDAa2131eszx", user.getPassword());
    }

    @Test
    public void ShouldMapToEditFormUserDto() {
        //Given
        User  user = new User.UserBuilder()
                .id(2L)
                .firstname("John")
                .lastname("Kovalsky")
                .password("AvDww872nccDAa2131eszx")
                .email("email@wp.pl")
                .username("username")
                .addressLine1("address line 1")
                .addressLine2("address line 2")
                .country("Poland")
                .state("Slaskie")
                .zipCode("44-266")
                .build();
        //When
        UserEditFormDto userEditFormDto = userMapper.mapToEditFormUserDto(user);

        //Then
        assertEquals("John", userEditFormDto.getFirstname());
        assertEquals("Kovalsky", userEditFormDto.getLastname());
        assertEquals("email@wp.pl", userEditFormDto.getEmail());
        assertEquals("address line 1", userEditFormDto.getAddressLine1());
        assertEquals("address line 2", userEditFormDto.getAddressLine2());
        assertEquals("Poland", userEditFormDto.getCountry());
        assertEquals("Slaskie", userEditFormDto.getState());
        assertEquals("44-266", userEditFormDto.getZipCode());
    }
}