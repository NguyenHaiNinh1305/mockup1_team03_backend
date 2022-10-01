package com.itsol.recruit.service;

import com.itsol.recruit.entity.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUser();

    public User findById(Long id);

    public User findUserByUserName(String userName);
}
