package com.itsol.recruit.dto;

import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class UserDTO {

    String fullName;

    String email;

    String userName;

    String password;

    String phoneNumber;

    String homeTown;

    String avatarName;

    String gender;

    String newPassword;

    Date birthDay;
}
