package com.itsol.recruit.service;

import com.itsol.recruit.entity.Units;
import com.itsol.recruit.repository.repoimpl.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UnitServiceImpl implements  UnitService{

    @Autowired
    UnitRepository unitRepository ;

    @Override
    public List<Units> getAllUnit() {
        return unitRepository.findAll();
    }
}
