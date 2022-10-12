package com.itsol.recruit.rest.controller;

import com.itsol.recruit.entity.Otp;
import com.itsol.recruit.entity.ResponseObject;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.service.EmailSenderService;
import com.itsol.recruit.service.OtpService;
import com.itsol.recruit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.Random;

@RestController
@RequestMapping("/api/rest/forgotPassword")
public class Fogot_pass_RestController {

    @Autowired
    UserService userService;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    OtpService otpService;

    @GetMapping ("{email}")
    public ResponseEntity<ResponseObject> sendMail(@PathVariable("email") String email) {
        for (User x : userService.findAll()
        ) {
            if (x.getEmail().equals(email) == true && x.isActive() == true) {
                Random random = new Random();
                int otp = random.nextInt(900000) + 100000;

                Otp ot = new Otp();
                ot.setCode(otp);
                ot.setIssueAt(System.currentTimeMillis()+300000);
                ot.setUser(x);
                otpService.save(ot);
                emailSenderService.sendSimpleEmail(email, "OTP code", "Your OTP code is: " + otp);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(HttpStatus.OK,"Gửi OTP thành công. Hết hạn sau 5p",x)
                );
            }

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(HttpStatus.BAD_REQUEST,"Email không tồn tại","")
        );
    }


}

