package com.itsol.recruit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itsol.recruit.entity.Units;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferSearchDTO {

    private Units unitOld;
    private Units unitNew;
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd")
    Date succeeDay;
    private String reason;
    private String userTransferName;

}
