package com.company.controller;

import com.company.Util.JwtUtil;
import com.company.dto.AuthDTO;
import com.company.dto.LoginDTO;
import com.company.dto.ProfileDTO;
import com.company.entity.ProfileEntity;
import com.company.repository.ProfileRepository;
import com.company.service.AuthService;
import com.company.validation.ProfileValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("auth/")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDTO dto, HttpServletRequest request){
        return ResponseEntity.ok(authService.login(dto));
    }
}

