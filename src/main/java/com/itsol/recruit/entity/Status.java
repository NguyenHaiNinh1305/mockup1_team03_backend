package com.itsol.recruit.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Status")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Status {

        @Id
        @Column(nullable = false)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STATUS_SEQ")
        @SequenceGenerator(name = "STATUS_SEQ", sequenceName = "STATUS_SEQ", allocationSize = 1, initialValue = 1)
        Long id;

        @Column(name = "name")
        String name;

        /*
        * id  1 : new (chờ xét duyệt, vừa khởi tạo)
        * id  2 : checked ( đang xét duyệt)
        * id  3 : agree  (đồng ý)
        * id  4 : refuse (bị từ chối)
        * id  5 : cancel (huy, chấm dứt hợp đồng)
        * id  6 : succeed(thành công)
        * id  7 : active
        * id  8 : deactivate
        * id  9 : save ( Luu ban ghi khong submit)
        * id 10 : submit
        * */

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Status status = (Status) o;
                return Objects.equals(id, status.id);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id);
        }
}
