package com.trunggame.controllers;

import com.trunggame.dto.BaseResponseDTO;
import com.trunggame.dto.JwtResponseDTO;
import com.trunggame.dto.LoginRequestDTO;
import com.trunggame.dto.SignupRequestDTO;
import com.trunggame.enums.ERole;
import com.trunggame.enums.EUserStatus;
import com.trunggame.models.Role;
import com.trunggame.models.User;
import com.trunggame.repository.RoleRepository;
import com.trunggame.repository.UserRepository;
import com.trunggame.security.jwt.JwtUtils;
import com.trunggame.security.services.UserService;
import com.trunggame.security.services.impl.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author congn kma
 * @since 7/12/2023
 */

//@Api(value = "Auth Controller", description = "REST endpoints for authenticating users")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @Value("${default.password}")
    private String defaultPassword;

    @PostMapping("/signin")
    public BaseResponseDTO<JwtResponseDTO> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new BaseResponseDTO<>("Success", 200, 200, new JwtResponseDTO(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles, userDetails.getAddress(), userDetails.getFullName(), userDetails.getPhoneNumber()));
    }

    //  @ApiOperation(value = "Login", response = BaseResponseDTO.class)
    @PostMapping("/signup")
    public BaseResponseDTO<?> registerUser(@Valid @RequestBody SignupRequestDTO signUpRequest) throws MessagingException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new BaseResponseDTO<>("Error: Username is already taken!", 400, 400, null);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new BaseResponseDTO<>("Error: Email is already in use!", 400, 400, null);
        }

        // Create new user's account
        User user = new User(signUpRequest.getNickname(),signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(this.defaultPassword), signUpRequest.getPhoneNumber(), signUpRequest.getFullName(),
                signUpRequest.getAddress(), EUserStatus.ACTIVE);

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        //userService.sendEmailRegister(user);
        return new BaseResponseDTO<>("User registered successfully!", 200, 200, null);
    }
}
