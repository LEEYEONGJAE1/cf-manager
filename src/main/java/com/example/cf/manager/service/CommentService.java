package com.example.cf.manager.service;

import com.example.cf.manager.domain.CommentInfo;
import com.example.cf.manager.domain.PostingInfo;
import com.example.cf.manager.domain.UserInfo;
import com.example.cf.manager.dto.CommentInfoDto;
import com.example.cf.manager.dto.PostingInfoDto;
import com.example.cf.manager.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public List<CommentInfo> findByPosting(PostingInfo posting){
        return commentRepository.findByPostinginfo(posting);
    }
    public Optional<CommentInfo> findById(Long id){
        return commentRepository.findById(id);
    }

    public void deleteComment(Long code, UserInfo userInfo){
        if(findById(code).get().getUserinfo().getUserid().equals(userInfo.getUserid()))
            commentRepository.deleteById(code);
    }

    public Long save(CommentInfoDto commentinfodto) {
        return commentRepository.save(
                CommentInfo.builder()
                .userinfo(commentinfodto.getUserinfo())
                .postinginfo(commentinfodto.getPostinginfo())
                .contents(commentinfodto.getContents())
                .addedTime(commentinfodto.getAddedTime())
                .build()).getId();
    }

}
