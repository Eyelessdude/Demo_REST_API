package com.digis.test.service;

import com.digis.test.domain.User;
import com.digis.test.dto.UserDto;

public interface UserService {

    /**
     * Obtains {@link UserDto} with information about a user by her login.
     *
     * @param login
     *         login by which search is performed
     * @return information about a user
     */
    UserDto getUserByLogin(String login);

    /**
     * Creates new user using inforamtion from {@link UserDto}.
     *
     * @param userDto
     *         information about user to be created
     */
    void createUser(UserDto userDto);

    /**
     * Updates user information.
     *
     * @param userDto
     *         new user information
     */
    void updateUser(UserDto userDto);

    /**
     * Finds {@link User} but her login.
     *
     * @param login
     *         by which search will be performed
     * @return entity of a found user
     */
    User findUserByLogin(String login);
}
