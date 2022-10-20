package com.itsol.recruit.repository;

import com.itsol.recruit.entity.Units;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitsRepository extends JpaRepository<Units, Integer> {

}
