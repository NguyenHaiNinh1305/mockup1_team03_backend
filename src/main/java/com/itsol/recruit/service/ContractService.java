package com.itsol.recruit.service;

import com.itsol.recruit.entity.Contract;
import com.itsol.recruit.entity.User;

import java.util.List;

public interface ContractService {

    List<Contract> getAllContractActive();

    List<Contract> findContractByUser(User user);
}
