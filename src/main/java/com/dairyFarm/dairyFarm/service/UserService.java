package com.dairyFarm.dairyFarm.service;

import com.dairyFarm.dairyFarm.Exception.UserPermission;
import com.dairyFarm.dairyFarm.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user) throws UserPermission;
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
