package com.itsol.recruit.web;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.dto.SortByValueUserDTO;
import com.itsol.recruit.dto.USearchDTO;
import com.itsol.recruit.dto.UsserDTO;
import com.itsol.recruit.entity.ResponseObject;
import com.itsol.recruit.entity.Role;
import com.itsol.recruit.entity.Units;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.UserRepository;
import com.itsol.recruit.service.EmailSenderService;
import com.itsol.recruit.service.RoleService;
import com.itsol.recruit.service.UnitService;
import com.itsol.recruit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = Constants.Api.Path.PUBLIC)

public class UserController {

    @Autowired
    UnitService unitService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    EmailSenderService emailSenderService;



    public final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/user")
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok().body(userService.getAllUser());
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> findUserById(@PathVariable("id") Long id) {
        System.out.println(id)
        ;
        return  ResponseEntity.ok().body( userService.findById(id));
    }

    @GetMapping("/getAllUnit")
    public ResponseEntity<List<Units>> getAllUnit() {
        return ResponseEntity.ok().body(unitService.findAll());
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseObject> createNewUser(@RequestParam("idUser") Long id, @RequestBody User user) {
        System.out.println(id)
        ;
        if (userService.findUserByUserName(user.getUserName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Username đã tồn tại", ""));
        }
        if (userService.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Email đã tồn tại", ""));
        }
        if (userService.findByCCCD(user.getCccd()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "CCCD đã tồn tại", ""));
        }
        if (userService.findByPhoneNumber(user.getPhoneNumber()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Phone đã tồn tại", ""));
        }
        String newPass = user.getPassword();
        user.setPassword(passwordEncoder.encode(newPass));
        if (user.getUnit().getId() == 3 && user.isLeader() == true) {
            user.getRoles().add(roleService.findById(5l));
        } else if (user.getUnit().getId() == 3 && user.isLeader() == false) {
            user.getRoles().add(roleService.findById(4l));
        } else if (user.getUnit().getId() != 3 && user.isLeader() == true) {
            user.getRoles().add(roleService.findById(3l));
        } else {
            user.getRoles().add(roleService.findById(2l));
        }

        List<User> userAdmin = userService.getAllUser();
        List<User> listUserEmail = new ArrayList<>();
        for (User x : userAdmin
        ) {if(x.getRoles().stream().anyMatch(role -> (role.getId()==1 || role.getId()==5))==true){
            System.out.println(".......");
            listUserEmail.add(x);
        }

        }
        System.out.println(listUserEmail);


        User userCreate = userService.findById(id)
                ;
        for (Role role : userCreate.getRoles()
        ) {
            if (role.getId() == 3) {
                for(int i=0;i<listUserEmail.size();i++){
                    emailSenderService.sendSimpleEmail(listUserEmail.get(i).getEmail(),
                            "Thông báo việc thêm nhân sự","DM " + userCreate.getName() +" đã thêm nhân viên " + "" +
                                    user.getName() +"vào hệ thống của công ty! Vui lòng phê duyệt ");
                }
            }

        }
        userService.save(user);
        return ResponseEntity.ok().body(new ResponseObject(HttpStatus.OK.toString(), "Tạo user mới thành công", ""));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        User userDB = userService.findById(id)
                ;

        if (userService.findUserByUserName(user.getUserName()) != null && !userDB.getUserName().equals(user.getUserName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Username đã tồn tại", ""));
        }
        if (userService.findByEmail(user.getEmail()) != null && !userDB.getEmail().equals(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Email đã tồn tại", ""));
        }
        if (userService.findByCCCD(user.getCccd()) != null && !userDB.getCccd().equals(user.getCccd())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "CCCD đã tồn tại", ""));
        }
        if (userService.findByPhoneNumber(user.getPhoneNumber()) != null && !userDB.getPhoneNumber().equals(user.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(HttpStatus.BAD_REQUEST.toString(), "Phone đã tồn tại", ""));
        }

        userDB.setName(user.getName());
        userDB.setEmail(user.getEmail());
        userDB.setUserName(user.getUserName());
        userDB.setCccd(user.getCccd());
        userDB.setLiteracy(user.getLiteracy());
        userDB.setPhoneNumber(user.getPhoneNumber());
        userDB.setHomeTown(user.getHomeTown());
        userDB.setSalary(user.getSalary());
        userDB.setGender(user.getGender());
        userDB.setPosition(user.getPosition());
        userDB.setLeader(user.isLeader());
        userDB.setBirthDay(user.getBirthDay());
        userDB.setActive(user.isActive());


        userService.update(userDB);
        return ResponseEntity.ok().body(new ResponseObject(HttpStatus.OK.toString(), "Cập nhật user thành công", ""));
    }

    @PutMapping("/user/sortByKey")
    public ResponseEntity<ResponseObject> sortByKey(@RequestParam int page,
                                                    @RequestParam Long id,
                                                    @RequestParam int size,
                                                    @RequestBody USearchDTO dto) {
        try {
////            if (page < 0) {
////                page = 0;
////            }
////            if (sortByValue.equals("undefined")) {
////                sortByValue = "name";
////            }
//            if (descAsc.equals("desc")) {
//                pageable = PageRequest.of(page, 10, Sort.by(sortByValue).descending());
//            } else {
//                pageable = PageRequest.of(page, 10, Sort.by(sortByValue).ascending());
//
//            }

            page = page < 0? 0:page;
            Pageable pageable;

            List<Sort.Order> orders = new ArrayList<>();
            List<SortByValueUserDTO> sortByValueUserDTOList = dto.getSortByValueUserDTOS();
            System.out.println(sortByValueUserDTOList);
            if(sortByValueUserDTOList.isEmpty()){
                orders.add(new Sort.Order(Sort.Direction.DESC,"name"){

                });
            }else {
                sortByValueUserDTOList.forEach(value ->{
                    orders.add(new Sort.Order(getSortDirection(value.getType()),value.getName()));
                });
            }

            pageable = PageRequest.of(page, size, Sort.by(orders));
            User u = userService.findById(id)
                    ;
            Set<Role> roles = u.getRoles();
            Set<String> listRole = roles.stream().map(role -> role.getCode()).collect(Collectors.toSet());

            Page<User>  pageUse;

            // kiem n ammin hr dm_hr
            if (listRole.contains("ROLE_ADMIN") || listRole.contains("ROLE_HR")
                    || listRole.contains("ROLE_DM_HR")) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(HttpStatus.OK.toString(), "Tìm thấy thành công",
                                userService.sortByKey(pageable, dto.getName(), dto.getEmail(), dto.getLiteracy(), dto.getPosition(),
                                        dto.getSalary(), dto.getBirthDay(), dto.getUnit(), null)));
            } else if (listRole.contains("ROLE_DM")) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(HttpStatus.OK.toString(), "Tìm thấy thành công",
                                userService.sortByKey(pageable, dto.getName(), dto.getEmail(), dto.getLiteracy(), dto.getPosition(),
                                        dto.getSalary(), dto.getBirthDay(), dto.getUnit(), u.getUnit())));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("false", "Không tìm thấy",
                            ""));


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("false", "Không tìm thấy",
                            ""));
        }
    }

    @PutMapping("/user/deactivated/{id}")
    public ResponseEntity<ResponseObject> deactivated(@PathVariable("id") Long id) {
        User u = userService.findById(id)
                ;
        u.setActive(false);
        userService.save(u);
        return ResponseEntity.ok().body(new ResponseObject(HttpStatus.OK.toString(), "Hủy kích hoạt thành công", ""));

    }

    @PutMapping("/user/activated/{id}")
    public ResponseEntity<ResponseObject> activated(@PathVariable("id") Long id) {
        User u = userService.findById(id)
                ;
        u.setActive(true);
        userService.save(u);
        return ResponseEntity.ok().body(new ResponseObject(HttpStatus.OK.toString(), "kích hoạt thành công", ""));

    }

    @PutMapping("/user/isActive")
    public ResponseEntity<ResponseObject> findIsActive(@RequestParam int page,@RequestParam int size,
                                                       @RequestBody USearchDTO dto){

        try{
            page = page < 0? 0:page;
            Pageable pageable;

            List<Sort.Order> oders = new ArrayList<>();
            List<SortByValueUserDTO> sortByValueUserDTOList = dto.getSortByValueUserDTOS();
            if(sortByValueUserDTOList.isEmpty()){
                oders.add(new Sort.Order(Sort.Direction.DESC,"name"){

                });
            }else {
                sortByValueUserDTOList.forEach(value ->{
                    oders.add(new Sort.Order(getSortDirection(value.getType()),value.getName()));
                });
            }

            pageable = PageRequest.of(page,size,Sort.by(oders));
            Page<User> userPage;
            userPage =userService.findByAcive(pageable,false);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("ok", "Thành công", userPage));

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("false", "Không tìm thấy", ""));
        }
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

}
