package com.itsol.recruit.repository;

import com.itsol.recruit.entity.Otp;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<Otp,Long>   {

    @Query("select o from Otps o where o.user.id =?1 and o.code =?2")
    Otp findByUserId(Long userId, Integer code);
}
