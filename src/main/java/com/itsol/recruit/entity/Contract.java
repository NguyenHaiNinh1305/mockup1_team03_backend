package com.itsol.recruit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Contracts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Contract {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "CONTRACT_SEQ")
    @SequenceGenerator(name="CONTRACT_SEQ",sequenceName = "CONTRACT_SEQ",allocationSize = 1,initialValue = 1)
    Long id;

    @ManyToOne
    @JoinColumn(name = "Users_id")
    User user;

    @OneToOne
    @JoinColumn(name = "contract_type_id")
    private ContractType contractType;

    @Column(name ="registration_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date registrationDate;

    @Column(name = "expiration_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date expirationDate;

    @Column(name = "img")
    String img;

    @Column(name ="status")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    boolean status;

    @Column(name ="note")
    String note;

    //ngày hết hạn hợp hợp đồng
    @Column(name = "Cancel_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    Date CancelDate;

    @Column(name = "reason_Cancel")
    String reasonCancel;

}
