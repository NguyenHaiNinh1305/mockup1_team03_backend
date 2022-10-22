package com.itsol.recruit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Check_Point")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckPoint {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Check_Point_SEQ")
    @SequenceGenerator(name = "Check_Point_SEQ", sequenceName = "Check_Point_SEQ", allocationSize = 1, initialValue = 1)
    long id;

    @ManyToOne
    @JoinColumn(name = "affect_user_id")
    User affectUser;

    @ManyToOne
    @JoinColumn(name = "create_user_id")
    User createUser;

    @ManyToOne
    @JoinColumn(name ="checkPoint_type_id")
    CheckPointType checkPointType;

    @Column(name = "create_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    Date createDate;

    @ManyToOne
    @JoinColumn(name = "status_id")
    Status status;

    @Column(name = "note")
    String note;

    @Column(name = "salary")
    long salary;

    @ManyToOne
    @JoinColumn(name = "reviewCheck_Point_id")
    ReviewCheckPoint reviewCheckPoint;
}
