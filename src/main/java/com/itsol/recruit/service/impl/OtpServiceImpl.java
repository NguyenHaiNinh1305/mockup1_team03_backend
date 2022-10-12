package com.itsol.recruit.service.impl;

import com.itsol.recruit.entity.Otp;
import com.itsol.recruit.repository.OtpRepository;
import com.itsol.recruit.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OtpServiceImpl implements OtpService {

//    public final OtpRepository otpRepository;
//
//    public OtpServiceImpl(OtpRepository otpRepository) {
//        this.otpRepository = otpRepository;
//    }

    @Autowired
    OtpRepository otpRepository;

    @Override
    public List<Otp> getAllOtp() {
        return otpRepository.findAll();
    }

    @Override
    public Otp save(Otp otp) {
       return otpRepository.save(otp);
    }

    @Override
    public List<Otp> findAllByUser() {
        return null;
    }


}
