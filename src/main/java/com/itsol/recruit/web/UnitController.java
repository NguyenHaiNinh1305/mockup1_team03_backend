package com.itsol.recruit.web;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.entity.ResponseObject;
import com.itsol.recruit.entity.Transfer;
import com.itsol.recruit.entity.Units;
import com.itsol.recruit.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Constants.Api.Path.PUBLIC)
public class UnitController {

    @Autowired
    UnitService unitService;

    @GetMapping("/units")
    public ResponseEntity<ResponseObject> getAllTransfer() {
        try{
            List<Units> units = unitService.getAllUnit();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ok", "Thành công", units));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("false", "Không tìm thấy", ""));
        }
    }
}
