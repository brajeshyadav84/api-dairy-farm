package com.dairyFarm.dairyFarm.controller;

import com.dairyFarm.dairyFarm.config.JwtUtil;
import com.dairyFarm.dairyFarm.entity.LoginRequest;
import com.dairyFarm.dairyFarm.service.AuthService;
import com.dairyFarm.dairyFarm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> authenticate(@RequestParam String username, @RequestParam String password) {
//        // Hardcoded User Authentication (Replace with DB Lookup)
//        if ("admin".equals(username) && "password".equals(password)) {
//            String token = jwtUtil.generateToken(username, "ROLE_ADMIN");
//            return ResponseEntity.ok(token);
//        } else if ("user".equals(username) && "password".equals(password)) {
//            String token = jwtUtil.generateToken(username, "ROLE_USER");
//            return ResponseEntity.ok(token);
//        } else {
//            return ResponseEntity.status(401).body("Invalid Credentials");
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest loginRequest) throws Exception {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        String token = authService.authenticate(username, password);
        return ResponseEntity.ok(token);
    }
}
