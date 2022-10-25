package com.itsol.recruit.service.impl;

import com.itsol.recruit.entity.Contract;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.ContractRepository;
import com.itsol.recruit.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    ContractRepository contractRepository;

    @Override
    public List<Contract> getAllContractActive() {
        return contractRepository.getAllContractActive();
    }

    @Override
    public List<Contract> findContractByUser(User user) {
        return contractRepository.findContractByUser(user);
    }
}
