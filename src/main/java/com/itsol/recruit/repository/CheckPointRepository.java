package com.itsol.recruit.repository;

import com.itsol.recruit.entity.CheckPoint;
import com.itsol.recruit.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckPointRepository extends JpaRepository<CheckPoint, Long> {
    List<CheckPoint> findCheckPointByAffectUser(User user, Sort sort);

    @Query( value = "SELECT * FROM check_point\n" +
            "where affect_user_id = ?1 and status_id = ?2",
            nativeQuery = true)
    List<CheckPoint> findCheckPointByAffectUserAndStatus(Long idUser,Long idStatus);

    @Query( value = "SELECT * FROM check_point\n" +
            "where affect_user_id = ?1 ",
            nativeQuery = true)
    List<CheckPoint> findCheckPointByAffectUser(Long idUser);
}
