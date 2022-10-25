package com.itsol.recruit.service.impl;

import com.itsol.recruit.entity.CheckPoint;
import com.itsol.recruit.entity.Transfer;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.CheckPointRepository;
import com.itsol.recruit.service.CheckPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckPointServiceImp implements CheckPointService {

    @Autowired
    CheckPointRepository checkPointRepository;


    @Override
    public List<CheckPoint> findCheckPointByAffectUser(User user, Sort sort) {
        return checkPointRepository.findCheckPointByAffectUser(user,sort);
    }

    @Override
    public CheckPoint save(CheckPoint checkPoint) {
        return checkPointRepository.save(checkPoint);
    }

    @Override
    public List<CheckPoint> findCheckPointByAffectUserAndStatus(Long idUser, Long idStatus) {
        return checkPointRepository.findCheckPointByAffectUserAndStatus(idUser,idStatus);
    }

    @Override
    public List<CheckPoint> findCheckPointByAffectUser(Long idUser) {
        return checkPointRepository.findCheckPointByAffectUser(idUser);
    }



    @Override
    public CheckPoint saveCheckPoint(CheckPoint checkPoint) {






        return null;
    }
}
