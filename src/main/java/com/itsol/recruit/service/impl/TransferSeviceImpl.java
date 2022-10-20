package com.itsol.recruit.service.impl;

import com.itsol.recruit.entity.Transfer;
import com.itsol.recruit.entity.Units;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.TransferRepository;
import com.itsol.recruit.service.TransferSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransferSeviceImpl implements TransferSevice {

    @Autowired
    TransferRepository transferRepository;

    @Override
    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }

    @Override
    public Transfer save(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    @Override
    public Transfer findById(Long id) {
        return transferRepository.findById(id).get();
    }

    @Override
    public Page<Transfer> findTransfer(Pageable pageable, String name, String reason
                                       , Units unitOld, Units unitNew, Units unitDm, Date succeeDay) {
        return transferRepository.findTransfer(pageable,name,reason,unitOld,unitNew,unitDm,succeeDay);
    }

    @Override
    public List<Transfer> findTransferOfUser(Long user) {
        return transferRepository.findTransferOfUser(user);
    }

    @Override
    public List<Transfer> findActiveTransferOfUser(Long id, long statusNew, Long statusChecked) {
        return transferRepository.findActiveTransferOfUser(id,statusNew,statusChecked);
    }

//    @Override
//    public List<Transfer> findTransferOfUser(User user, Sort sort) {
//        return transferRepository.findTransferOfUser(user,sort);
    }
//}
