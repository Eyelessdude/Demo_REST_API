package com.digis.test.service;

import com.digis.test.domain.Gender;
import com.digis.test.domain.User;
import com.digis.test.dto.UserDto;
import com.digis.test.exception.UserAlreadyExistsException;
import com.digis.test.exception.UserNotFoundException;
import com.digis.test.repository.UserRepository;
import com.digis.test.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private static final String login = "test@mail.com";
    private static final String fullName = "Test Name";
    private static final LocalDate dateOfBirth = LocalDate.now();
    private static final Gender gender = Gender.MALE;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testGetUserByLoginSuccess() {
        //given
        User user = new User();
        user.setLogin(login);
        UserDto userDto = new UserDto();
        userDto.setLogin(login);

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));
        when(conversionService.convert(user, UserDto.class)).thenReturn(userDto);

        //when
        UserDto actualUserDto = userService.getUserByLogin(login);

        //then
        assertEquals(userDto.getLogin(), actualUserDto.getLogin());
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetUserByLoginNotFound() {
        //given
        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        //when then
        userService.getUserByLogin(login);
    }

    @Test
    public void testCreateUserSuccess() {
        //given
        User user = getParametrizedUser(login, fullName, dateOfBirth, gender);
        UserDto userDto = getParametrizedUserDto(login, fullName, dateOfBirth, gender);

        when(conversionService.convert(userDto, User.class)).thenReturn(user);

        //when
        userService.createUser(userDto);

        //then
        verify(userRepository, times(1)).save(user);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void testCreateUserAlreadyExists() {
        //given
        User firstUser = getParametrizedUser(login, fullName, dateOfBirth, gender);
        UserDto firstUserDto = getParametrizedUserDto(login, fullName, dateOfBirth, gender);
        User secondUser = getParametrizedUser(login, "Not The Same",
                                              LocalDate.of(2007, Month.SEPTEMBER, 1),
                                              Gender.FEMALE);

        when(conversionService.convert(firstUserDto, User.class)).thenReturn(firstUser);
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(secondUser));

        //when then
        userService.createUser(firstUserDto);
    }

    @Test
    public void testUpdateUserSuccess() {
        //given
        User oldUser = getParametrizedUser(login, fullName, dateOfBirth, gender);
        String newName = "New Name";
        LocalDate newDate = LocalDate.of(2006, Month.APRIL, 4);
        Gender newGender = Gender.FEMALE;
        UserDto newUserDto = getParametrizedUserDto(login, newName, newDate, newGender);
        User newUser = getParametrizedUser(login, newName, newDate, newGender);

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(oldUser));

        //when
        userService.updateUser(newUserDto);

        //then
        verify(userRepository, times(1)).save(newUser);
    }

    @Test(expected = UserNotFoundException.class)
    public void testUpdateUserNotFound() {
        //given
        UserDto userDto = getParametrizedUserDto(login, fullName, dateOfBirth, gender);

        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());


        //when then
        userService.updateUser(userDto);
    }

    private User getParametrizedUser(String login, String fullName, LocalDate dateOfBirth,
                                     Gender gender) {
        User user = new User();
        user.setLogin(login);
        user.setFullName(fullName);
        user.setDateOfBirth(dateOfBirth);
        user.setGender(gender);

        return user;
    }

    private UserDto getParametrizedUserDto(String login, String fullName, LocalDate dateOfBirth,
                                           Gender gender) {
        UserDto userDto = new UserDto();
        userDto.setLogin(login);
        userDto.setFullName(fullName);
        userDto.setDateOfBirth(dateOfBirth);
        userDto.setGender(gender);

        return userDto;
    }
}
