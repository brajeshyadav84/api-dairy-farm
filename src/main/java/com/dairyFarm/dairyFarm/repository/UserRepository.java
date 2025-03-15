package com.dairyFarm.dairyFarm.repository;

import com.dairyFarm.dairyFarm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String name);
}
