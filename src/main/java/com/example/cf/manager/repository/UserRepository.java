package com.example.cf.manager.repository;

import com.example.cf.manager.domain.UserInfo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByUserid(String userid);
}