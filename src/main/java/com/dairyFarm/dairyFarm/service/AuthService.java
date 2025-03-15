package com.dairyFarm.dairyFarm.service;

import com.dairyFarm.dairyFarm.Impl.UserServiceImpl;
import com.dairyFarm.dairyFarm.config.JwtUtil;
import com.dairyFarm.dairyFarm.entity.User;
import com.dairyFarm.dairyFarm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    UserServiceImpl userServiceImpl;


    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String authenticate(String username, String password) throws Exception {
        Optional<User> userOpt = userRepository.findByEmail(username);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String decryptedPassword = userServiceImpl.decrypt(user.getPassword(),user.getHashkey().toString());
            user.getPassword().equals(decryptedPassword);
            return jwtUtil.generateToken(user.getName(), user.getRole());
        }else{
            throw new Exception("User not found");
        }
    }
}
