package com.itsol.recruit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Transfer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transfer {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANSFER_SEQ")
    @SequenceGenerator(name = "TRANSFER_SEQ", sequenceName = "TRANSFER_SEQ", allocationSize = 1, initialValue = 1)
    Long id;

    @ManyToOne
    @JoinColumn(name = "transfer_User")
    User transferUser;

    @ManyToOne
    @JoinColumn(name = "create_User")
    User createUser;

    @ManyToOne
    @JoinColumn(name = "unit_old")
    private Units unitOld;

    @ManyToOne
    @JoinColumn(name = "unit_new")
    private Units unitNew;

    @Column(name = "name")
    private String name;

    @Column(name = "succee_day")
    @JsonFormat(pattern="yyyy-MM-dd")
    Date succeeDay;

    @ManyToOne
    @JoinColumn(name = "admin_Review")
    User adminReview;

    @Column(name = "adminReview_day")
    @JsonFormat(pattern="yyyy-MM-dd")
    Date adminReviewDay;

    @Column(name = "cancle_day")
    @JsonFormat(pattern="yyyy-MM-dd")
    Date cancleDay;

    @Column(name = "cread_day")
    @JsonFormat(pattern="yyyy-MM-dd")
    Date creadDay;

    @Column(name = "reason")
    private String reason;

    @ManyToOne
    @JoinColumn(name = "dm_unit_Old")
    User divisionManagerUnitOld;

    @ManyToOne
    @JoinColumn(name = "dm_unit_New")
    User divisionManagerUnitNew;

    @Column(name = "cread_day_dm_unit_Old")
    @JsonFormat(pattern="yyyy-MM-dd")
    Date creadDayDivisionManagerUnitOld;

    @Column(name = "cread_day_dm_unit_new")
    @JsonFormat(pattern="yyyy-MM-dd")
    Date creadDayDivisionManagerUnitNew;

    @ManyToOne
    @JoinColumn(name = "status_transfer")
    private Status statusTransfer;

    @ManyToOne
    @JoinColumn(name = "status_transfer_dm_old")
    private Status statusReviewDivisionManagerUnitOld;

    @ManyToOne
    @JoinColumn(name = "status_transfer_dm_new")
    private Status statusReviewDivisionManagerUnitNew;

}
