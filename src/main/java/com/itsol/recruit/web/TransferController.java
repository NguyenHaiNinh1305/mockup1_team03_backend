package com.itsol.recruit.web;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.dto.TransferSearchDTO;
import com.itsol.recruit.entity.*;
import com.itsol.recruit.service.EmailSenderService;
import com.itsol.recruit.service.TransferSevice;
import com.itsol.recruit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Constants.Api.Path.PUBLIC)
public class TransferController {

    @Autowired
    TransferSevice transferSevice;

    @Autowired
    UserService userService;

    @Autowired
    EmailSenderService emailSenderService;

    @GetMapping("/transfer/{id}")
    public ResponseEntity<ResponseObject> getTransferById(@PathVariable Long id) {
        try {
            Transfer transfer = transferSevice.findById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ok", "Thành công", transfer));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("false", "Không tìm thấy", ""));
        }
    }

    @GetMapping("/transfer/list-transfer-of-user/{userId}")
    public ResponseEntity<ResponseObject> getAllTransfer(@PathVariable long userId) {

        List<Transfer> transfer = transferSevice.findTransferOfUser(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "Thành công", transfer));

    }

    @GetMapping("/transfer")
    public ResponseEntity<ResponseObject> getAllTransfer() {

            List<Transfer> transfer = transferSevice.findAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ok", "Thành công"
                            , transfer));
    }

    @PutMapping("/transfer/page")
    public ResponseEntity<ResponseObject> getPageTransfer(@RequestParam int page
                                                        , @RequestParam Long userID
                                                        , @RequestParam(required = true) String sortByValue
                                                        , @RequestParam(required = true) String descAsc
                                                        , @RequestBody TransferSearchDTO transferSearchDTO) {
        try {
            page =   page < 0? 0:page;
            Pageable pageable;
            if (sortByValue.equals("undefined")) {
                sortByValue = "creadDay";
            }

            if (descAsc.equals("desc")) {
                pageable = PageRequest.of(page, 5, Sort.by(sortByValue).descending());
            } else {
                pageable = PageRequest.of(page, 5, Sort.by(sortByValue).ascending());
            }

            User user = userService.findById(userID);
            Set<Role> roles = user.getRoles();

            Set<String> listRole = roles.stream().map(role -> role.getCode()).collect(Collectors.toSet());
            Page<Transfer> pageTransfer;
            if (listRole.contains("ROLE_ADMIN") || listRole.contains("ROLE_HR")
                    || listRole.contains("ROLE_DM_HR")) {

                pageTransfer = transferSevice
                        .findTransfer(pageable, transferSearchDTO.getName()
                                , transferSearchDTO.getReason()
                                , transferSearchDTO.getUnitOld()
                                , transferSearchDTO.getUnitNew()
                                , null
                                , transferSearchDTO.getSucceeDay()
                        );
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ok", "Thành công", pageTransfer));
            } else if (listRole.contains("ROLE_DM")) {
                pageTransfer = transferSevice
                        .findTransfer(pageable, transferSearchDTO.getName()
                                , transferSearchDTO.getReason()
                                , transferSearchDTO.getUnitOld()
                                , transferSearchDTO.getUnitNew()
                                , user.getUnit()
                                , transferSearchDTO.getSucceeDay()
                        );
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ok", "Thành công", pageTransfer));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("false", "Bạn không có quyền xem thông tin", ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("false", "Không tìm thấy", ""));
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<ResponseObject> saveTransfer(@RequestBody Transfer transfer) {
        Role roleAdmin = new Role();
        List<Transfer> listTransfer = transferSevice.findActiveTransferOfUser(transfer.getTransferUser().getId()
                , 1l, 2l);

        if (!listTransfer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("false"
                            , "Nhân viên đang trong một đợt điều chuyển khác", null));
        }

        User createUser = transfer.getCreateUser();
        Set<Role> roles = createUser.getRoles();

        Set<String> listRole = roles.stream().map(role -> role.getCode()).collect(Collectors.toSet());
        try {
            transfer.setId(0l);

            // equals unit -> false
            if (transfer.getUnitOld().equals(transfer.getUnitNew())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseObject("false", "Không chuyển cùng đơn vị", null));
            }
            // kiem tra nguoi tao la admin hoac hr hay khong, neu phai tao  dot chuyen
            if (listRole.contains("ROLE_ADMIN") || listRole.contains("ROLE_HR")
                    || listRole.contains("ROLE_DM_HR")) {

                User leadUnitOld = userService.getUserFromUnit(true, transfer.getUnitOld());
                User leadUnitNew = userService.getUserFromUnit(true, transfer.getUnitNew());
                if (leadUnitOld == null || leadUnitNew == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ResponseObject("false", "Không có trưởng đơn vị trong dơn vị", null));
                }
                transfer.setDivisionManagerUnitOld(leadUnitOld);
                transfer.setDivisionManagerUnitNew(leadUnitNew);
                transfer.setCreadDay(new Date());
                transfer.setStatusTransfer(new Status(1l, "new"));

                Transfer saveTransfer = transferSevice.save(transfer);
                // send email
                emailSenderService.sendSimpleEmail(leadUnitOld.getEmail(),
                        "Thông báo đợt điều chuyển",
                        createBodyEmail(saveTransfer));

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ok", "Thanh công", saveTransfer));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("false", "Không có quyền thêm đợt chuyển", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("false", "Không thành công", null));
        }
    }

    @PutMapping("/transfer/cancel-Transfer/{transferId}")
    public ResponseEntity<ResponseObject> cancelTransfer(@PathVariable Long transferId
            , @RequestBody User cancleUser) {

        try {
            Transfer transfer = transferSevice.findById(transferId);
            // kiem tra nguoi huy la nguoi tao  moi duoc huy
            if (transfer.getCreateUser().equals(cancleUser)) {
                transfer.setStatusTransfer(new Status(5l, "cancel"));
                transfer.setCancleDay(new Date());
                transfer.setSucceeDay(null);
                Transfer upDateTransfer = transferSevice.save(transfer);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ok", "Hủy thành  công", upDateTransfer));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("false", "Không có quyền xóa", ""));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("false", "Không thành công", null));
        }
    }

    @PutMapping("/transfer/update/{id}")
    public ResponseEntity<ResponseObject> updateTransfer(@PathVariable Long id
            , @RequestBody Transfer transfer) {
        try {
            User updateUser = userService.findById(id);

            // kiem tra nguoi cap nhat la nguoi tao va dot chuyen  la moi tao
            // neu dung cho sua
            if (transfer.getCreateUser().equals(updateUser)
                    && transfer.getStatusTransfer().equals(new Status(1l, "new"))) {
                Transfer updateTransfer = transferSevice.save(transfer);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ok", "Update thành  công", updateTransfer));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("false", " Không thành công", ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("false", "Không thành công", null));
        }
    }

    @PutMapping("/transfer/review-transfer/{idUser}")
    public ResponseEntity<ResponseObject> reviewTransfer(@RequestBody Transfer transfer,
                                                         @PathVariable Long idUser
    ) {

        try {
            Transfer updateTransfer = transferSevice.save(transfer);
            if (updateTransfer.getStatusTransfer().equals(new Status(4l, "refuse "))) {
                User refuseUser = userService.findById(idUser);
                emailSenderService.sendSimpleEmail(transfer.getCreateUser().getEmail(),
                        "Thông báo từ chối đợt điều chuyển",
                        createBodyRefuseEmail(updateTransfer, refuseUser));
            }

            if (transfer.getStatusTransfer().equals(new Status(2l, "checked"))) {
                emailSenderService.sendSimpleEmail(transfer.getDivisionManagerUnitNew().getEmail(),
                        "Thông báo đợt điều chuyển",
                        createBodyEmail(updateTransfer));
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ok", "Update thành  công", updateTransfer));


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("false", "Không thành công", null));
        }
    }


    private String createBodyEmail(Transfer transfer) {
        StringBuilder builder = new StringBuilder();
        builder.append("Thông báo đợt điều chuyển: \n");
        builder.append("Họ tên nhân viên: ");
        builder.append(transfer.getTransferUser().getUserName());
        builder.append("\nĐơn vị cũ: ");
        builder.append(transfer.getUnitOld().getName());
        builder.append("\nĐơn vị sẽ chuyển tới : ");
        builder.append(transfer.getUnitNew().getName());
        builder.append("\n Người tại đợt điều chuyển: ");
        builder.append(transfer.getCreateUser().getName());
        builder.append("\n Lý do chuyển: ");
        builder.append(transfer.getReason());
        builder.append("\n Vui lòng thực hiện xét duyệt.: ");
        builder.append("http://localhost:4200/admin/transfer-information/" + transfer.getId());
        return builder.toString();
    }

    private String createBodyRefuseEmail(Transfer transfer, User user) {
        StringBuilder builder = new StringBuilder();
        builder.append("Thông báo đợt điều chuyển bị từ chối: \n");
        builder.append("Người từ chối: ");
        builder.append(user.getName());
        builder.append("\nĐơn vị : ");
        builder.append(user.getUnit().getName());
        builder.append("\nThông tin về đợt chuyển: \n");
        builder.append("Họ tên nhân viên: ");
        builder.append(transfer.getTransferUser().getUserName());
        builder.append("\nĐơn vị cũ: ");
        builder.append(transfer.getUnitOld().getName());
        builder.append("\nĐơn vị sẽ chuyển tới : ");
        builder.append(transfer.getUnitNew().getName());
        builder.append("\n Lý do chuyển: ");
        builder.append(transfer.getReason());

        return builder.toString();
    }

}
