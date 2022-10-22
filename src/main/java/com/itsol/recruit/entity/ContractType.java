package com.itsol.recruit.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity(name = "ContractTypes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractType {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "CONTRACT_TYPE_SEQ")
    @SequenceGenerator(name="CONTRACT_SEQ",sequenceName = "CONTRACT_SEQ",allocationSize = 1,initialValue = 1)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name="status")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    boolean status;
}
