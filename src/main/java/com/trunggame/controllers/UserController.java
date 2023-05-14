package com.trunggame.controllers;

import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.UserDeleteDTO;
import com.trunggame.dto.UserUpdateDTO;
import com.trunggame.models.User;
import com.trunggame.repository.RoleRepository;
import com.trunggame.repository.UserRepository;
import com.trunggame.repository.impl.UserRepositoryCustom;
import com.trunggame.security.jwt.JwtUtils;
import com.trunggame.security.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;



/**
 * @author congn kma
 * @since 7/12/2023
 */


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepositoryCustom userRepositoryCustom;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    private static final Logger logger = LogManager.getLogger(UserController.class);


    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<Page<User>> getListUser(Pageable pageable, String search) {
        Page<User> users = userRepositoryCustom.getListUser(search, pageable);
        return new BaseResponseDTO<>("Success", 200,200,users);
    }


    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> deleteUserByIds(@RequestBody UserDeleteDTO userDeleteDTO) {
        if (userDeleteDTO.getIds().isEmpty()) {
            return new BaseResponseDTO<>("Error deleting user", 403,403,null);
        }
        userService.deleteUserByIds(userDeleteDTO.getIds());
        return new BaseResponseDTO<>("Delete users successfully", 200,200,null);
    }

    @PostMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> activeUserByIds(@RequestBody UserDeleteDTO userDeleteDTO) {
        if (userDeleteDTO.getIds().isEmpty()) {
            return new BaseResponseDTO<>("User not found", 403,403,null);
        }
        userService.activeUserByIds(userDeleteDTO.getIds());
        return new BaseResponseDTO<>("Active users successfully", 200,200,null);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponseDTO<?> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        var userOPT = userRepository.findById(userUpdateDTO.getId());

        if (userOPT.isPresent()) {
           var currentUser = userOPT.get();
            currentUser.setAddress(userUpdateDTO.getAddress());
            currentUser.setFullName(userUpdateDTO.getFullName());
            currentUser.setPhoneNumber(userUpdateDTO.getPhoneNumber());
            userRepository.save(currentUser);
            return new BaseResponseDTO<>("Success", 200,200,null);

        }

        return new BaseResponseDTO<>("Error deleting user", 400,400,null);
    }





}
