package com.example.demojwt.controller;

import com.example.demojwt.dto.request.LoginRequest;
import com.example.demojwt.dto.request.SignupRequest;
import com.example.demojwt.dto.response.JwtResponse;
import com.example.demojwt.dto.response.MessageResponse;
import com.example.demojwt.entity.ERole;
import com.example.demojwt.entity.Role;
import com.example.demojwt.entity.User;
import com.example.demojwt.jwt.JwtProvider;
import com.example.demojwt.repository.RoleRepository;
import com.example.demojwt.repository.UserRepository;
import com.example.demojwt.service.UserDetailsImpl;
import com.example.demojwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired(required = true)
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserService userService;
    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        redisTemplate.opsForValue().set("token", jwt);
//        List<Role> roles = userDetails.getAuthorities().stream()
//                .map().collect(Collectors.toList());
//        }
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signup) {
        if (userRepository.existsByUsername(signup.getUsername())) {
            return new ResponseEntity<>(new MessageResponse("user_existed"), HttpStatus.OK);
        }
        if (userRepository.existsByEmail(signup.getEmail())) {
            return new ResponseEntity<>(new MessageResponse("no_email"), HttpStatus.OK);
        }
        User user = new User();
        Set<String> strRole = signup.getRole();
        Set<Role> roles = new HashSet<>();
        strRole.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(ERole.ADMIN).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(adminRole);
                    break;
                case "user":
                    Role userRole = roleRepository.findByName(ERole.USER).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(userRole);
                    break;
            }
        });
        user.setRoles(roles);
        user.setUsername(signup.getUsername());
        user.setEmail(signup.getEmail());
        user.setPassword(passwordEncoder.encode(signup.getPassword()));
        userService.save(user);
        return ResponseEntity.ok(user);
    }
}
