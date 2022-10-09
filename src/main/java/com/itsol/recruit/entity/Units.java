package com.itsol.recruit.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;


@Entity(name = "UNITS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Units {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UNITS_SEQ")
    @SequenceGenerator(name = "UNITS_SEQ", sequenceName = "UNITS_SEQ", allocationSize = 1, initialValue = 1)
    private int id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "STATUS")
    private int status;
}
