package com.itsol.recruit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itsol.recruit.entity.Units;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class USearchDTO {
    private String name;
    private String email;
    private String literacy;
    private String position;
    private Long salary;
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date birthDay;
    Units unit;
    List<SortByValueUserDTO>  sortByValueUserDTOS;
}