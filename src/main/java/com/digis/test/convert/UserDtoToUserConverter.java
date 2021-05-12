package com.digis.test.convert;

import com.digis.test.domain.User;
import com.digis.test.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserConverter implements Converter<UserDto, User> {

    @Override
    public User convert(UserDto userDto) {
        User user = new User();

        user.setLogin(userDto.getLogin());
        user.setFullName(userDto.getFullName());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setGender(userDto.getGender());

        return user;
    }
}
