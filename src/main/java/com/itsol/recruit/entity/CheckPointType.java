package com.itsol.recruit.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity(name = "CheckPoint_Type")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CheckPointType {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CheckPoint_Type_SEQ")
    @SequenceGenerator(name = "CheckPoint_Type_SEQ", sequenceName = "CheckPoint_Type_SEQ", allocationSize = 1, initialValue = 1)
    long id;

    /* 1: PERIODIC(định kỳ)
       2:unexpected (đột xuất)
     */

    @Column(name = "name")
    String name;

    @Column(name = "status")
    int status;

}
