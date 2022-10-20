package com.itsol.recruit.service.impl;

import com.itsol.recruit.entity.Units;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.UserRepository;
import com.itsol.recruit.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    public final UserRepository userRepository;
    private Long userId;
    private String pass;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }


    @Override
    public int updateUserPassword(String userName, String password) {
        return userRepository.updateUserPassword(userName,password);
    }

    @Override
    public int updateUserAvatarName(String avatarName, long id) {
        return userRepository.updateUserAvatarName(avatarName,id);
    }

    @Override
    public User getUserFromUnit(Boolean isLeader, Units unit) {
        return userRepository.getUserFromUnit(isLeader,unit);
    }


}
