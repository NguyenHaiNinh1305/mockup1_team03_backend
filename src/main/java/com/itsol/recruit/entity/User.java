package com.itsol.recruit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User{
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
    @SequenceGenerator(name = "USERS_SEQ", sequenceName = "USERS_SEQ", allocationSize = 1, initialValue = 1)
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "email")
    String email;

    @Column(name = "user_name")
    String userName;

    @Column(name = "password")
    String password;

    @Column(name = "cccd")
    String cccd;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "home_town")
    String homeTown;

    @Column(name = "avatar")
    String avatarName;

    @Column(name = "salary")
    long salary;

    @Column(name = "gender")
    String gender;

    @Column(name = "position")
    String position;

    @Column(name = "is_leader")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    boolean isLeader;

    @Column(name = "birth_day")
    @JsonFormat(pattern="yyyy-MM-dd")
    Date birthDay;

    @Column(name = "is_delete")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    boolean isDelete;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "permisstion",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private Set<Role> roles = new HashSet<>();

    @Column(name = "activate")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Units unit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}