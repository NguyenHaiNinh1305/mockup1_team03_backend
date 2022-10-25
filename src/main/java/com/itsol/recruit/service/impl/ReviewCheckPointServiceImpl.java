package com.itsol.recruit.service.impl;

import com.itsol.recruit.entity.ReviewCheckPoint;
import com.itsol.recruit.repository.ReviewCheckPointRepository;
import com.itsol.recruit.service.ReviewCheckPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewCheckPointServiceImpl implements ReviewCheckPointService {

    @Autowired
    ReviewCheckPointRepository reviewCheckPointRepository;

    @Override
    public ReviewCheckPoint save(ReviewCheckPoint reviewCheckPoint) {
        return reviewCheckPointRepository.save(reviewCheckPoint);
    }
}
