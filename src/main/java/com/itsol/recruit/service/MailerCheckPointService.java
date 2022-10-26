package com.itsol.recruit.service;

import com.itsol.recruit.entity.*;
import com.itsol.recruit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class MailerCheckPointService {

    @Autowired
    CheckPointService checkPointService;

    @Autowired
    NotificationSendMailService notificationSendMailService;

    @Autowired
    UserService userService;

    @Autowired
    ReviewCheckPointService reviewCheckPointService;


//        @Scheduled(fixedRate = 5000)
    @Scheduled(cron = "0 0 9 * * ?")
    public void auToSendMailCheckPoint()   {
        List<Long> list = userService.getUserCheckpoint();
        List<User> userList = new ArrayList<>();
        if(!list.isEmpty()){
            list.forEach(id -> {
                User user = userService.findById(id);
                userList.add(user);
            });
            Thread thread = new Thread(){
                public void run(){
                    userList.forEach(user -> {

                        try {
                            notificationSendMailService.sendMailToCheckpointUser(user);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        createCheckPoint(user);
                    });

                }
            };
            thread.start();
        }





        // kiem tra neu co checkpoint qua 15 chua lam thu tuc
        List<CheckPoint> checkPointList = checkPointService.findCheckPointOverDays();
            if(!checkPointList.isEmpty()){
                checkPointList.forEach(checkPoint -> {
                    // doi trang thai kho tao thanh dang xet duyet va gui gmal cho dm
                    checkPoint.setStatus(new Status(2l,"checked"));
                    checkPointService.save(checkPoint);
                    try {
                        notificationSendMailService.sendMailToManagerReview(checkPoint.getReviewCheckPoint()
                                .getDivisionManager(), checkPoint.getAffectUser());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                });
            }
    }

    private void createCheckPoint(User user){
        CheckPoint checkPoint = new CheckPoint();
        checkPoint.setAffectUser(user);
        checkPoint.setCreateDate(new Date());
        CheckPointType checkPointType = new CheckPointType();
        // loai checkpoint dinh ky
        checkPointType.setId(1l);
        checkPoint.setCheckPointType(checkPointType);
        checkPoint.setStatus(new Status(1l,"new"));
        ReviewCheckPoint reviewCheckPoint = new ReviewCheckPoint();
        reviewCheckPoint.setDivisionManager(userService.getUserFromUnit(true,user.getUnit()));
        reviewCheckPoint.setDivisionManagerHr(userService.getUserFromUnit(true, new Units(3,"hr",1) ));
        checkPoint.setReviewCheckPoint(reviewCheckPointService.save(reviewCheckPoint));
        checkPointService.save(checkPoint);
    }



}
