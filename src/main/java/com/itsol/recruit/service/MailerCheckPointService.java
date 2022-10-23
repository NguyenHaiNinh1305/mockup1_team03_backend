package com.itsol.recruit.service;

import com.itsol.recruit.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class MailerCheckPointService {

    @Autowired
    ContractService contractService;

    @Autowired
    CheckPointService checkPointService;

    @Autowired
    NotificationSendMailService notificationSendMailService;

    @Autowired
    UserService userService;

    @Autowired
    ReviewCheckPointService reviewCheckPointService;

//    @Scheduled(fixedRate = 15000)
    @Scheduled(cron = "0 0 9 * * ?")
    public void auToSendMailCheckPoint(){
        List<Contract> list = contractService.getAllContractActive();
        if(list.isEmpty()){
            return;
        }else {
            list.forEach(contract -> {
                checkUserCanCheckPoint(contract);
            });
        }
    }

    private void checkUserCanCheckPoint(Contract contract){
        List<CheckPoint> userCheckPointList = checkPointService
                                              .findCheckPointByAffectUser(contract.getUser()
                                                                          , Sort.by("createDate").descending());
        // neu khong tìm thấy lịch sử checkpoint của user => lần đầu checkpoint
        if(userCheckPointList.isEmpty()){
            sendMailFirstCheckPoint(contract);
        }else {
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Calendar expirationDate = Calendar.getInstance();
            expirationDate.setTime(userCheckPointList.get(0).getCreateDate());
            int year = calendar.get(Calendar.YEAR) - expirationDate.get(Calendar.YEAR);
            int mouth = Math.abs(calendar.get(Calendar.MONTH) - expirationDate.get(Calendar.MONTH)) ;
            int day = calendar.get(Calendar.DATE) - expirationDate.get(Calendar.DATE);

            if(year == 1 && mouth == 0 && day == 0){
                createCheckPoint(contract.getUser());
                try {
                    notificationSendMailService.sendMailToCheckpointUser(contract.getUser());
                }catch( Exception e ){
                    System.out.println(e.getMessage());
                }
            }
             CheckPoint checkPoint = userCheckPointList.get(0);
            if(checkPoint.getStatus().equals(new Status(1l,"new"))){
                expirationDate.add(Calendar.DATE,21);
                 year = calendar.get(Calendar.YEAR) - expirationDate.get(Calendar.YEAR);
                 mouth = Math.abs(calendar.get(Calendar.MONTH) - expirationDate.get(Calendar.MONTH)) ;
                 day = calendar.get(Calendar.DATE) - expirationDate.get(Calendar.DATE);
                System.out.println(expirationDate.get(Calendar.DATE) + "/ ngay tang" + expirationDate.get(Calendar.MONTH));

                System.out.println("ngay hien tai " +calendar.get(Calendar.MONTH));
                if(year == 0 && mouth == 0 && day == 0){
                    checkPoint.setStatus(new Status(2l,"checked"));
                    checkPointService.save(checkPoint);
                try {
                    notificationSendMailService.sendMailToManagerReview(checkPoint.getReviewCheckPoint()
                            .getDivisionManager(),checkPoint.getAffectUser());
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

                }
            }
        }
    }

    // nhan vien lan dau duoc CheckPoint
    private void sendMailFirstCheckPoint(Contract contract) {
        List<Contract> userContractList = contractService.findContractByUser(contract.getUser());
        Set<String> listContractType = userContractList.stream()
                                                       .map(c -> c.getContractType().getName())
                                                       .collect(Collectors.toSet());
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar expirationDate = Calendar.getInstance();
        expirationDate.setTime(contract.getExpirationDate());
        int year = calendar.get(Calendar.YEAR) - expirationDate.get(Calendar.YEAR);
        int mouth = Math.abs(calendar.get(Calendar.MONTH) - expirationDate.get(Calendar.MONTH)) ;
        int day = calendar.get(Calendar.DATE) - expirationDate.get(Calendar.DATE);

        // với nhân viên đã từng ký hợp đồng học việc thời gian lần checkpoint đầu tiên là 6thangs
        if(listContractType.contains("HDDHV")){
            if(year == 0 && mouth == 6 && day == 0){
                createCheckPoint(contract.getUser());
                try {
                    notificationSendMailService.sendMailToCheckpointUser(contract.getUser());
                }catch( Exception e ){
                    System.out.println(e.getMessage());
                }
            }
        }else {
            if(year == 1 && mouth == 0 && day == 0){
                createCheckPoint(contract.getUser());
                try {
                    notificationSendMailService.sendMailToCheckpointUser(contract.getUser());
                }catch( Exception e ){
                    System.out.println(e.getMessage());
                }

            }
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
