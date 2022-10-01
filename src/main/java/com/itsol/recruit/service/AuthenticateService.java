package com.itsol.recruit.service;


import com.itsol.recruit.dto.UserDTO;
import com.itsol.recruit.entity.User;

public interface AuthenticateService {
    public User signup(UserDTO dto);
}
