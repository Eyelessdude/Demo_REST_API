package com.digis.test.convert;

import com.digis.test.domain.User;
import com.digis.test.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User user) {
        UserDto userDto = new UserDto();

        userDto.setLogin(user.getLogin());
        userDto.setFullName(user.getFullName());
        userDto.setDateOfBirth(user.getDateOfBirth());
        userDto.setGender(user.getGender());

        return userDto;
    }
}
