package com.itsol.recruit.repository;

import com.itsol.recruit.entity.Transfer;
import com.itsol.recruit.entity.Units;
import com.itsol.recruit.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    List<Transfer> findAll();

    @Query("SELECT t FROM Transfer t" +
            " where ((t.unitOld = :unitDm or t.unitNew = :unitDm) or :unitDm is null) and " +
            "(( lower(t.name)  like '%' ||  lower(:name) || '%' or :name is null) " +
            "and ( lower(t.reason)  like '%' ||  lower(:reason) || '%' or :reason is null) " +
            "and (t.unitOld =:unitOld or :unitOld is null ) " +
            "and (t.unitNew =:unitNew or :unitNew is null ) " +
            "and (t.succeeDay =:succeeDay or :succeeDay is null ))")
    Page<Transfer> findTransfer(
               Pageable pageable,
               @Param("name") String name,
               @Param("reason") String reason,
               @Param("unitOld") Units unitOld,
               @Param("unitNew") Units unitNew,
               @Param("unitDm" )Units unitDm,
               @Param("succeeDay") Date succeeDay
               );


    @Query( value = "SELECT * FROM Transfer  WHERE TRANSFER_USER = ?1 and SUCCEE_DAY is not null",
            nativeQuery = true)
    List<Transfer> findTransferOfUser(Long id);


    @Query( value = "SELECT * FROM Transfer  \n" +
            "WHERE TRANSFER_USER = ?1 and (STATUS_TRANSFER = ?2 or STATUS_TRANSFER = ?3)",
            nativeQuery = true)
    List<Transfer> findActiveTransferOfUser(Long id, long statusNew, Long statusChecked);
}
