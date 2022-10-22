package com.itsol.recruit.service.impl;

import com.itsol.recruit.entity.Units;
import com.itsol.recruit.repository.UnitRepository;
import com.itsol.recruit.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    UnitRepository unitRepository;

    @Override
    public List<Units> findAll() {
        return unitRepository.findAll();
    }

    @Override
    public List<Units> getAllUnit() {
        return unitRepository.findAll();
    }

}
