package com.itsol.recruit.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@EnableScheduling
public class MailerCheckPointService {

//    @Scheduled(fixedRate = 5000)
    @Scheduled(cron = "0 3 22 * * ?")
    public void auToSendMailCheckPoint(){
        System.out.println(new Date().toString());
    }


}
