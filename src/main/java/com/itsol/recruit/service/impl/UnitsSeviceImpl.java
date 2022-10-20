package com.itsol.recruit.service.impl;

import com.itsol.recruit.entity.Units;
import com.itsol.recruit.repository.UnitsRepository;
import com.itsol.recruit.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitsSeviceImpl implements UnitService {

    @Autowired
    UnitsRepository unitsRepository;

    @Override
    public List<Units> getAllUnit() {
        return this.unitsRepository.findAll();
    }
}
