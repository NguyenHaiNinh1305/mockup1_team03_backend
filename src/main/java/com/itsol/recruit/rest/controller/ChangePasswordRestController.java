package com.itsol.recruit.rest.controller;

import com.itsol.recruit.entity.ResponseObject;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/changePassword")
public class ChangePasswordRestController {

    @Autowired
    UserService userService;
    private final PasswordEncoder passwordEncoder;

    public ChangePasswordRestController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseObject> update(@PathVariable("id") Long id, @RequestBody String pass) {
        try {
            User u = userService.findById(id);
            u.setPassword(passwordEncoder.encode(pass));
            userService.update(u);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(HttpStatus.OK, "Thay đổi mật khẩu thành công", u)
            );
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST,"Thay đổi mật khẩu thất bại",""));
        }
    }
}
