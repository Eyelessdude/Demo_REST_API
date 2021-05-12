package com.digis.test.dto;

import com.digis.test.domain.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserDto {

    private String login;
    private String fullName;
    private LocalDate dateOfBirth;
    private Gender gender;

}
