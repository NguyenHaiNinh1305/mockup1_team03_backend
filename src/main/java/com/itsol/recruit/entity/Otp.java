package com.itsol.recruit.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Otps")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_OTP_ID")
    @SequenceGenerator(name = "OTP_UEQ", sequenceName = "OTP_SEQ", allocationSize = 1)
    Long id;

    @Column(name = "code")
    Integer code;

    @ManyToOne
    @JoinColumn(name = "Users_id")
    User user;

    @Column(name ="issue_at")
    private Long issueAt;
    @Column(name = "status")
    boolean status;
}
