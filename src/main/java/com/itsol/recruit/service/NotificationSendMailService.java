package com.itsol.recruit.service;

import com.itsol.recruit.entity.Transfer;
import com.itsol.recruit.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class NotificationSendMailService {

    @Autowired
    EmailSenderService emailSenderService;

    @Async
    public void sendMailToDmTransfer(Transfer transfer, User user) throws MailException, InterruptedException {
        emailSenderService.sendSimpleEmail(user.getEmail(),
                "Thông báo đợt điều chuyển",
                createBodyEmail(transfer));
    }

    @Async
    public void sendMailCancelTransfer(Transfer transfer, User refuseUser) throws MailException, InterruptedException {
        emailSenderService.sendSimpleEmail(transfer.getCreateUser().getEmail(),
                "Thông báo từ chối đợt điều chuyển",
                createBodyRefuseEmail(transfer, refuseUser));
    }

    @Async
    public void sendMailToCheckpointUser(User user) throws MailException, InterruptedException {
        emailSenderService.sendSimpleEmail(user.getEmail(),
                "Thông báo đợt đợt checkpoint",
                createBodyCheckPointEmail(user));
    }

    @Async
    public void sendMailToManagerReview(User manager,User user) throws MailException, InterruptedException {
        emailSenderService.sendSimpleEmail(manager.getEmail(),
                "Thông báo đợt đợt checkpoint",
                "Nhân viên "+ user.getUserName()
                        +" đang trong kỳ checkpoint và cần được xử lý"
                        + ".\n Vui lòng xử lý.");
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

    private String createBodyCheckPointEmail(User user) {
        StringBuilder builder = new StringBuilder();
        builder.append("Thông báo đợt đợt checkpoint: \n");
        builder.append("Họ Tên Nhân viên: ");
        builder.append(user.getName());
        builder.append("\nAnh/chị đã tới hạn checkpoint định kỳ vui lòng làm thủ tục checkpoint");
        return builder.toString();
    }
}
