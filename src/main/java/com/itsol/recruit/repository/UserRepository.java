package com.itsol.recruit.repository;

import com.itsol.recruit.entity.Units;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.repoext.UserRepositoryExt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
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


    @Query("select u from Users u where u.email = ?1")
    User findByEmail1(String email);

    @Query("select u from Users u where u.phoneNumber =?1")
    User findByPhoneNumber (String phoneNumber);

    @Query("select u from Users u where u.cccd =?1")
    User findByCCCD (String cccd);

    @Query("select u from Users u where u.userName =?1")
    User findByUName (String username);
//
//    @Query("SELECT u FROM Users u" +
//            " where (u.unit = :unitDm  or :unitDm is null) and " +
//            " (( lower(u.name)  like '%' ||  lower(:name) || '%' or :name is null) " +
//            "and ( lower(u.email)  like '%' ||  lower(:email) || '%' or :email is null) " +
//            "and ( lower(u.literacy)  like '%' ||  lower(:literacy) || '%' or :literacy is null) " +
//            "and ( lower(u.position)  like '%' ||  lower(:position) || '%' or :position is null) " +
//            "and (u.salary =:salary or :salary is null ) " +
//            "and (u.unit =:unit or :unit is null ) " +
//            "and (u.birthDay =:birthDay or :birthDay is null ))")
//    Page<User> findByKey(
//            Pageable pageable,
//            @Param("name") String name,
//            @Param("email") String email,
//            @Param("literacy") String literacy,
//            @Param("position" )String position,
//            @Param("salary") Long salary,
//            @Param("birthDay") Date birthDay,
//            @Param("unitDm") Units unitDm
//
//    );

    @Query("SELECT u FROM Users u where " +
             "(u.unit = :unitDm  or :unitDm is null) and"+
             "(lower(u.name)  like '%' ||  lower(:name) || '%' or :name is null)" +
             " and (lower(u.email)  like '%' ||  lower(:email) || '%' or :email is null)" +
             " and (lower(u.literacy)  like '%' ||  lower(:literacy) || '%' or :literacy is null)" +
             " and (lower(u.position)  like '%' ||  lower(:position) || '%' or :position is null)" +
             " and  (u.salary  = :salary  or :salary is null)" +
             " and (u.birthDay = :birthDay  or :birthDay is null)" +
            "and   (u.unit  =:unit or :unit is null)")
    Page<User> findByKey(
            Pageable pageable,
            @Param("name") String name,
            @Param("email") String email,
            @Param("literacy") String literacy,
            @Param("position" )String position,
            @Param("salary") Long salary,
            @Param("birthDay") Date birthDay,
            @Param("unit") Units unit,
            @Param("unitDm") Units unitDm
    );

    @Query("SELECT u from  Users u where  u.unit = :unit and u.isLeader = :isLeader" )
    User getUserFromUnit(@Param("isLeader") Boolean isLeader,
                     @Param("unit") Units unit);

    @Query( value = "WITH t1 AS (\n" +
            "    SELECT u.id\n" +
            "    FROM users u JOIN contracts c ON u.id =c.users_id LEFT JOIN check_point ch on u.id = ch.affect_user_id\n" +
            "    where c.CONTRACT_TYPE_ID = 1 and ch.CREATE_DATE is NULL \n" +
            ")\n" +
            "SELECT c.users_id FROM t1 u JOIN contracts c ON u.id =c.users_id\n" +
            "where c.CONTRACT_TYPE_ID =3 and c.status = 1 AND TO_CHAR (c.EXPIRATION_DATE) =  TO_CHAR (ADD_MONTHS(SYSDATE, -6)) \n" +
            "UNION\n" +
            "SELECT u.id FROM \n" +
            "            users u JOIN contracts c ON u.id =c.users_id\n" +
            "            JOIN check_point ch on u.id = ch.affect_user_id\n" +
            "            where c.CONTRACT_TYPE_ID =3 and c.status = 1 and   TO_CHAR (ch.CREATE_DATE) =  TO_CHAR (ADD_MONTHS(SYSDATE, -12))",
            nativeQuery = true)
    List<Long> getUserCheckpoint();

    @Query("select u from Users u where u.isActive =?1")
    List<User> findIsActive(Boolean isActive);

    @Query("SELECT u FROM Users u where u.isActive =:isAcive")
    Page<User> findByActive(Pageable pageable, @Param("isAcive") Boolean isAcive);

}
