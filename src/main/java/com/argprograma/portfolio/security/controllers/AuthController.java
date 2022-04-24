package com.argprograma.portfolio.security.controllers;

import com.argprograma.portfolio.security.dto.JWTResponseDTO;
import com.argprograma.portfolio.security.dto.LoginDTO;
import com.argprograma.portfolio.security.dto.RegisterDTO;
import com.argprograma.portfolio.security.entities.PortfolioUser;
import com.argprograma.portfolio.security.entities.Role;
import com.argprograma.portfolio.security.repositories.IPortfolioUserRepository;
import com.argprograma.portfolio.security.repositories.IRoleRepository;
import com.argprograma.portfolio.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IPortfolioUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JWTResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return new ResponseEntity<>(new JWTResponseDTO(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDTO) {
        if(userRepository.existsByUsername(registerDTO.getUsername())) {
            return new ResponseEntity<>("Username all ready exists", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(registerDTO.getEmail())) {
            return new ResponseEntity<>("Email all ready exists", HttpStatus.BAD_REQUEST);
        }

        PortfolioUser newUser = new PortfolioUser();

        newUser.setUsername(registerDTO.getUsername());
        newUser.setEmail(registerDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Role newUserRoles = roleRepository.findByName("ROLE_USER").get();

        newUser.setRoles(Collections.singleton(newUserRoles));

        userRepository.save(newUser);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}