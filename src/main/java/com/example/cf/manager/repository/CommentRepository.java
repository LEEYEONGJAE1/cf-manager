package com.example.cf.manager.repository;


import com.example.cf.manager.domain.CommentInfo;
import com.example.cf.manager.domain.PostingInfo;
import com.example.cf.manager.dto.PostingInfoDto;
import com.example.cf.manager.dto.UserInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentInfo,Long>{
    List<CommentInfo> findByPostinginfo(PostingInfo postinginfo);
    void deleteAllByPostinginfo(PostingInfo postingInfo);
}
