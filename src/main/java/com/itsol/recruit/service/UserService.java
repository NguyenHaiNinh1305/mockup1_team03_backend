package com.itsol.recruit.service;

import com.itsol.recruit.entity.Units;
import com.itsol.recruit.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface UserService {

    public List<User> getAllUser();

    public User findById(Long id);

    public User findUserByUserName(String userName);

    public List<User> findAll();

    public User update(User user);

    User findByEmail(String email);

    User findByPhoneNumber(String sdt);

    User findByCCCD(String cccd);
    
    public  User save(User user);

    int updateUserPassword(String userName,String password);

    int updateUserAvatarName(String avatarName,
                             long id);
    

    User findByUserName1(String userName);
    Page<User> sortByKey(Pageable pageable,
                         String name,
                         String email,
                         String literacy,
                         String position,
                         Long salary,
                         Date birthDay,
                         Units unit,
                         Units unitDm);

    User getUserFromUnit(Boolean isLeader, Units unit);



}
