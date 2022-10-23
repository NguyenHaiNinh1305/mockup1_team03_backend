package com.itsol.recruit.service;

import com.itsol.recruit.entity.CheckPoint;
import com.itsol.recruit.entity.Transfer;
import com.itsol.recruit.entity.User;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CheckPointService {

    List<CheckPoint> findCheckPointByAffectUser(User user, Sort sort);

    CheckPoint save(CheckPoint checkPoint);

    List<CheckPoint> findCheckPointByAffectUserAndStatus(Long idUser, Long idStatus);

    List<CheckPoint> findCheckPointByAffectUser(Long idUser);

    CheckPoint saveCheckPoint(CheckPoint checkPoint);

}
