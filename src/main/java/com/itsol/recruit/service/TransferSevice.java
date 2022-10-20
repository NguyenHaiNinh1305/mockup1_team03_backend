package com.itsol.recruit.service;

import com.itsol.recruit.entity.Transfer;
import com.itsol.recruit.entity.Units;
import com.itsol.recruit.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TransferSevice {

     List<Transfer> findAll();

     Transfer save(Transfer transfer);

     Transfer findById(Long id);

     Page<Transfer> findTransfer(
             Pageable pageable,
              String name,
              String reason,
              Units unitOld,
              Units unitNew,
              Units unitDm,
              Date succeeDay
     );

     List<Transfer> findTransferOfUser(Long user);

     List<Transfer> findActiveTransferOfUser(Long id, long statusNew, Long statusChecked);
}
