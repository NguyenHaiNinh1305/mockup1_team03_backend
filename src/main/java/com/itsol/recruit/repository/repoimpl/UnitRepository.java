package com.itsol.recruit.repository.repoimpl;

import com.itsol.recruit.entity.Transfer;
import com.itsol.recruit.entity.Units;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Units, Integer> {
}
