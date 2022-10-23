package com.itsol.recruit.repository;

import com.itsol.recruit.entity.Contract;
import com.itsol.recruit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("SELECT c from  Contracts c where  c.status = true " )
    List<Contract> getAllContractActive();

    List<Contract> findContractByUser(User user);

}
