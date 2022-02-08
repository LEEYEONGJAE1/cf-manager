package com.example.cf.manager.repository;

import com.example.cf.manager.domain.PostingInfo;
import com.example.cf.manager.dto.UserInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostingRepository extends JpaRepository<PostingInfo,Long>{
    List<PostingInfo> findByUserinfo(UserInfoDto userinfo);
    List<PostingInfo> findByTitleLike(String title);
    Optional<PostingInfo> findById(Long id);
}
