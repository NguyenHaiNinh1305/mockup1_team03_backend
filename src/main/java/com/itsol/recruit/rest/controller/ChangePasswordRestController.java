package com.itsol.recruit.rest.controller;

import com.itsol.recruit.entity.User;
import com.itsol.recruit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/changePassword")
public class ChangePasswordRestController {

    @Autowired
    UserService userService;
    private final PasswordEncoder passwordEncoder;

    public ChangePasswordRestController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PutMapping("{id}")
    public User update(@PathVariable("id") Long id, @RequestBody String pass) {
        User u = userService.findById(id);
        u.setPassword(passwordEncoder.encode(pass));
        return userService.update(u);
    }
}
