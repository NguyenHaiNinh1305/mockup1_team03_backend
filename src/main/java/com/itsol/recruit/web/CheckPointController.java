package com.itsol.recruit.web;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.entity.CheckPoint;
import com.itsol.recruit.entity.CheckPointType;
import com.itsol.recruit.entity.ResponseObject;
import com.itsol.recruit.service.CheckPointService;
import com.itsol.recruit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.Api.Path.PUBLIC)
public class CheckPointController {

    @Autowired
    CheckPointService checkPointService;

    @Autowired
    UserService userService;

    @GetMapping("/checkpoint/list-Checkpoint/{idUser}")
    public ResponseEntity<ResponseObject> getCheckpointByIdUser(@PathVariable Long idUser){

         List<CheckPoint> checkPointList = checkPointService
                                          .findCheckPointByAffectUser(idUser);

        if(checkPointList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("false","Không tìm thấy checkpoint",""));
        }
        return ResponseEntity.status(HttpStatus.OK).
                body(new ResponseObject("ok","Thành công ",checkPointList)) ;
    }

    @PostMapping("/checkpoint/new-Checkpoint")
    public ResponseEntity<ResponseObject> saveCheckpoint(@RequestBody CheckPoint checkPoint){

        if (checkPoint.getCheckPointType().equals(new CheckPointType(2l,"",1))){

        }else {
              CheckPoint checkPointSave = checkPointService.saveCheckPoint(checkPoint);
        }
                return null;
    }


}

