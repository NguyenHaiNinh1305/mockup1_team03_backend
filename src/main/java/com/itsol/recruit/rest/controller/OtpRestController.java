package com.itsol.recruit.rest.controller;

import com.itsol.recruit.entity.Otp;
import com.itsol.recruit.entity.ResponseObject;
import com.itsol.recruit.repository.OtpRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/otp")
public class OtpRestController {
    @Autowired
    OtpRepository otpRepository;
    @GetMapping
    public ResponseEntity<?> findByUserId(@RequestParam("users_id") Long userId,
                                       @RequestParam("code") Integer code){
        Otp otp = otpRepository.findByUserId(userId,code);
        if(otpRepository.findByUserId(userId,code) == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.toString(),"Mã OTP không đúng","")
            );
        } else if (otp.getIssueAt() <= new Date().getTime()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Otp này đã hết hạn!", ""));
        }
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
