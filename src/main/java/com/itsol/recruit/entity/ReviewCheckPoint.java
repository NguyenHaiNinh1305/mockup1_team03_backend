package com.itsol.recruit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Review_CheckPoint")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewCheckPoint {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Review_CheckPoint_SEQ")
    @SequenceGenerator(name = "Review_CheckPoint_SEQ", sequenceName = "Review_CheckPoint_SEQ", allocationSize = 1, initialValue = 1)
    Long id;

    @ManyToOne
    @JoinColumn(name = "dm_id")
    User divisionManager;

    @ManyToOne
    @JoinColumn(name = "dm_hr_id")
    User divisionManagerHr;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    User admin;

    @ManyToOne
    @JoinColumn(name = "authorize_hr_id")
    User authorizeHr;


    @Column(name = "adminReview_day")
    @JsonFormat(pattern="yyyy-MM-dd")
    Date adminReviewDay;

    @Column(name = "dm_Review_day")
    @JsonFormat(pattern="yyyy-MM-dd")
    Date divisionManagerReviewDay;

    @Column(name = "dm_hr_Review_day")
    @JsonFormat(pattern="yyyy-MM-dd")
    Date divisionManagerHrReviewDay;

    @Column(name = "hr_Review_day")
    @JsonFormat(pattern="yyyy-MM-dd")
    Date hrReviewDay;

    @Column(name = "dm_comment_checkpoint")
    String divisionManagerCommentCheckpoint;

    @Column(name = "dm_hr_comment_checkpoint")
    String divisionManagerHrCommentCheckpoint;

    @Column(name = "dm_comment_affect_user")
    String divisionManagerCommentAffectUser;

    @Column(name = "dm_hr_comment_affect_user")
    String divisionManagerHrCommentAffectUser;

    @Column(name = "meet_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    Date meetDay;

    @Column(name = "dm_review_salary")
    Long divisionManagerReviewSalary;

    @Column(name = "dm_hr_review_salary")
    Long divisionManagerHrReviewSalary;

    @Column(name = "hr_review_salary")
    Long hrReviewSalary;

    @ManyToOne
    @JoinColumn(name = "status_dm")
    Status statusDivisionManager;

    @ManyToOne
    @JoinColumn(name = "status_dm_hr")
    Status statusDivisionManagerHr;

    @ManyToOne
    @JoinColumn(name = "status_hr")
    Status statusHr;

    @ManyToOne
    @JoinColumn(name = "status_admin")
    Status statusAdmin;

    @Column(name = "reason_Refuse")
    String reasonRefuse;
}
