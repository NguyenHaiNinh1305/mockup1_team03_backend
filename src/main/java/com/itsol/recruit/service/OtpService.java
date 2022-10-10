package com.itsol.recruit.service;

import com.itsol.recruit.entity.Otp;

import java.util.List;
public interface OtpService {
     List<Otp> getAllOtp();

     Otp save(Otp otp);

     List<Otp> findAllByUser();


}
