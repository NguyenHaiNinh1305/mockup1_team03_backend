package com.itsol.recruit.rest.controller;

import com.itsol.recruit.entity.Otp;
import com.itsol.recruit.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
public class OtpRestController {
    @Autowired
    OtpRepository otpRepository;
    @GetMapping()
    public Otp findByUserId(@RequestParam("users_id") Long userId,
                            @RequestParam("code") Integer code){

        return otpRepository.findByUserId(userId,code);
    }
}
