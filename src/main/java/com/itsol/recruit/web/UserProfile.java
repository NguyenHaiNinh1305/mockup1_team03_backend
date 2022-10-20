package com.itsol.recruit.web;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.entity.ResponseObject;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.service.IStorageSevice;
import com.itsol.recruit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


@RestController
@RequestMapping(Constants.Api.Path.PUBLIC)
public class UserProfile {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private IStorageSevice storageSevice;

    @GetMapping(value = "/user-profile/{userName}")
    public ResponseEntity<ResponseObject> findUserByUserName(@PathVariable String userName) {
        User user;
        System.out.println(userName);
        try {
            user = userService.findUserByUserName(userName);
            System.out.println(userName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("not found", "khong tim thay user", ""));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("ok", "thanh cong", user));

    }

    @PutMapping("/user-profile")
    public ResponseEntity<ResponseObject> updateUserProfile(@RequestBody User updateUser) {

        try {
            List<String> mess = this.validate(updateUser);
            if(mess.isEmpty()){
                userService.save(updateUser);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ok", "thanh cong", updateUser));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("false", "thanh cong", mess));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("not_found", "khongthanh cong", ""));
        }
    }

    @PutMapping("/user-profile/new-pass")
    public ResponseEntity<ResponseObject> updateUserPass(@RequestBody User updateUser) {
        try {
            if(updateUser.getPassword() != null && validatePass(updateUser.getPassword())){
                userService.updateUserPassword(updateUser.getUserName(),
                        passwordEncoder.encode(updateUser.getPassword()));
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseObject("ok", "Thanh cong", ""));
            }else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(new ResponseObject("false", "Mật khẩu không chính xác", ""));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("false", "Khong Thanh cong", ""));
        }
    }

    @PostMapping("/user-profile/new-avata/{id}")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable long id) {
        try {
            String generatedFileName = storageSevice.storaFile(file);
            int capNhat = userService.updateUserAvatarName(generatedFileName, id);
            System.out.println(generatedFileName);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ok", "Thanh cong", generatedFileName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new ResponseObject("false", e.getMessage(), ""));

        }
    }

    @GetMapping("/user-profile/avata/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            byte[] bytes = storageSevice.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    public List<String> validate(User user) {
        Pattern checkName = Pattern.compile("[a-zA-z]{1,30}$");
        Pattern checkPhone = Pattern.compile("^(84|0[3|5|7|8|9])+([0-9]{8})\\b");
        List<String> mess = new ArrayList<>();
        if (user.getUserName() == null || !checkName.matcher(user.getUserName().trim()).find()) {
            mess.add("Tên bị để trống, hoặc lớn hơn 50 ký tự");
        }

        if (user.getPhoneNumber() == null || !checkPhone.matcher(user.getPhoneNumber()).find()) {
            mess.add("Điện thoại không hợp lệ");
        }

        if (user.getHomeTown() == null || user.getHomeTown().trim().length() > 250) {
            mess.add("Địa chỉ  không hợp lệ");
        }

        String gender = user.getGender();
        if (!(gender.equals("Nam") || gender.equals("Nữ") || gender.equals("Khác"))) {
            mess.add("Giới tính không hợp lệ (Nam,Nữ,Khác)");
        }
        Pattern regexGmail = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b");
        if (user.getEmail() == null || !regexGmail.matcher(user.getEmail()).find()) {
            mess.add("Email không hợp lệ");
        }
        if (user.getPosition() == null) {
            mess.add("Chức vụ không được để trống");
        }
        if (user.getCccd() == null) {
            mess.add("Cccd không được để trống");
        }
        if (user.getBirthDay() == null) {
            mess.add("Birthday không được để trống");
        } else {
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Calendar namSinh = Calendar.getInstance();
            namSinh.setTime(user.getBirthDay());
            int tuoi = calendar.get(Calendar.YEAR) - namSinh.get(Calendar.YEAR);
            System.out.println(user.getBirthDay().toString());
            if (tuoi < 1 || tuoi > 90) {
                mess.add("Tuổi không hợp lệ");
            }
        }
        return mess;
    }
  private boolean  validatePass(String pass){
      Pattern regexPass = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9])(?!.*\\s).{8,16}$");
     return  regexPass.matcher(pass).find();
    }
}
