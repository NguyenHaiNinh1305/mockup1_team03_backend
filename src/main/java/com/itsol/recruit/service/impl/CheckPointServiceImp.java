package com.itsol.recruit.service.impl;

import com.itsol.recruit.entity.*;
import com.itsol.recruit.repository.CheckPointRepository;
import com.itsol.recruit.service.CheckPointService;
import com.itsol.recruit.service.NotificationSendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckPointServiceImp implements CheckPointService {

    @Autowired
    CheckPointRepository checkPointRepository;

    @Autowired
    NotificationSendMailService notificationSendMailService;


    @Override
    public List<CheckPoint> findCheckPointByAffectUser(User user, Sort sort) {
        return checkPointRepository.findCheckPointByAffectUser(user, sort);
    }

    @Override
    public CheckPoint save(CheckPoint checkPoint) {
        return checkPointRepository.save(checkPoint);
    }

    @Override
    public List<CheckPoint> findCheckPointByAffectUserAndStatus(Long idUser, Long idStatus) {
        return checkPointRepository.findCheckPointByAffectUserAndStatus(idUser, idStatus);
    }

    @Override
    public List<CheckPoint> findCheckPointByAffectUser(Long idUser) {
        return checkPointRepository.findCheckPointByAffectUser(idUser);
    }


    @Override
    public CheckPoint saveCheckPoint(CheckPoint checkPoint, User user) {
        Status status = checkPoint.getStatus();
        if (status.equals(new Status(1l, "new"))) {
            return this.checkPointRepository.save(checkPoint);
        }
        try {
            ReviewCheckPoint reviewCheckPoint = checkPoint.getReviewCheckPoint();
            if (status.equals(new Status(2l, "checked"))
                    && reviewCheckPoint.getDivisionManager().equals(user)) {
                if (reviewCheckPoint.getStatusDivisionManager().equals(new Status(10l, "save"))) {
                    return this.checkPointRepository.save(checkPoint);
                }
                if (reviewCheckPoint.getStatusDivisionManager().equals(new Status(9l, "submit"))) {
                    CheckPoint checkPoint1 = this.checkPointRepository.save(checkPoint);

                    notificationSendMailService.sendMailToManagerReview(
                            checkPoint1.getReviewCheckPoint().getDivisionManagerHr()
                            , checkPoint1.getAffectUser());
                    return checkPoint1;
                }
            }
        } catch (Exception e) {
            return null;
        }


        return null;
    }


    @Override
    public List<CheckPoint> findCheckPointOverDays() {
        return checkPointRepository.findCheckPointOverDays();
    }
}
