package com.itsol.recruit.repository;

import com.itsol.recruit.entity.ReviewCheckPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewCheckPointRepository extends JpaRepository<ReviewCheckPoint, Long> {

}