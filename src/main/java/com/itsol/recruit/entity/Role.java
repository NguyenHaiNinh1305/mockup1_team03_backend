package com.itsol.recruit.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_ROLE_ID")
    @SequenceGenerator(name = "GEN_ROLE_ID", sequenceName = "SEQ_ROLE", allocationSize = 1)
    @Column(name = "id", nullable = false, unique = true)
    Long id;

    @Column(name = "code", nullable = false, unique = true)
    String code;

    @Column(name = "description")
    String description;

    @Column(name = "is_delete")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    boolean isDelete;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
