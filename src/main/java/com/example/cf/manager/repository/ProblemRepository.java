package com.example.cf.manager.repository;

import com.example.cf.manager.domain.ProblemInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<ProblemInfo,Long> {
    List<ProblemInfo> findByUserid(String userid);
}
