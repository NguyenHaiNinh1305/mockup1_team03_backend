package com.itsol.recruit.repository;

import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.repoext.UserRepositoryExt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryExt {

    User findByUserName(String userName);

    Optional<User> findByEmail(String username);

    @Modifying
    @Query("update Users u set u.password = :password where u.userName = :userName")
    int updateUserPassword(@Param("userName") String userName,
                                   @Param("password") String password);

    @Modifying
    @Query("update Users u set u.avatarName = :avatarName where u.id = :id")
    int updateUserAvatarName(@Param("avatarName") String avatarName,
                           @Param("id") long id);

}
