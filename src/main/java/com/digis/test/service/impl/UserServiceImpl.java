package com.digis.test.service.impl;

import com.digis.test.domain.User;
import com.digis.test.dto.UserDto;
import com.digis.test.exception.UserAlreadyExistsException;
import com.digis.test.exception.UserNotFoundException;
import com.digis.test.repository.UserRepository;
import com.digis.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ConversionService conversionService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ConversionService conversionService) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }

    @Override
    public UserDto getUserByLogin(String login) {
        User user = findUserByLogin(login);

        return conversionService.convert(user, UserDto.class);
    }

    @Override
    public void createUser(UserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        String login = user.getLogin();

        if (userRepository.findByLogin(login)
                          .isPresent()) {
            throw new UserAlreadyExistsException("User with login " + login + " already exists");
        }

        userRepository.save(user);
    }

    @Override
    public void updateUser(UserDto userDto) {
        User user = findUserByLogin(userDto.getLogin());

        updateUser(user, userDto);

        userRepository.save(user);
    }

    @Override
    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login)
                             .orElseThrow(() -> {
                                 throw new UserNotFoundException(
                                         "There is no user with login: " + login);
                             });
    }

    private void updateUser(User oldUser, UserDto newUser) {
        oldUser.setFullName(newUser.getFullName());
        oldUser.setDateOfBirth(newUser.getDateOfBirth());
        oldUser.setGender(newUser.getGender());
    }
}
