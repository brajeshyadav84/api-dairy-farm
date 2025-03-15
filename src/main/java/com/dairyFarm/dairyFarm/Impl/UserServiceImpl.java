package com.dairyFarm.dairyFarm.Impl;

import com.dairyFarm.dairyFarm.Exception.UserPermission;
import com.dairyFarm.dairyFarm.entity.User;
import com.dairyFarm.dairyFarm.repository.UserRepository;
import com.dairyFarm.dairyFarm.service.UserService;
import com.dairyFarm.dairyFarm.utility.RolesName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) throws UserPermission {
        if(user.getName() == null || user.getMobile() == null || user.getEmail() == null) {
            throw new IllegalArgumentException("Name and Mobile number are mandatory");
        }
        user.setHashkey(ThreadLocalRandom.current().nextLong(100000000000L, 999999999999L));
        try {
            user.setPassword(encrypt(user.getPassword(),user.getHashkey().toString()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(user.getRole().equalsIgnoreCase(RolesName.ADMIN)||user.getRole().equalsIgnoreCase(RolesName.SUPER_ADMIN)) {
            throw new UserPermission("you are allowed to create the user with this role");
        }
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setMobile(userDetails.getMobile());
        user.setPassword(userDetails.getPassword());
        user.setState(userDetails.getState());
        user.setLocation(userDetails.getLocation());
        user.setArea(userDetails.getArea());
        user.setHouseNo(userDetails.getHouseNo());
        user.setIsActive(userDetails.getIsActive());
        user.setRole(userDetails.getRole());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private SecretKey getKeyFromString(String key) throws NoSuchAlgorithmException {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        keyBytes = sha.digest(keyBytes);
        return new SecretKeySpec(keyBytes, 0, 16, "AES");
    }

    // Encrypt a password using the given key
    public String encrypt(String password, String key) throws Exception {
        SecretKey secretKey = getKeyFromString(key);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt an encrypted password using the given key
    public String decrypt(String encryptedPassword, String key) throws Exception {
        SecretKey secretKey = getKeyFromString(key);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
