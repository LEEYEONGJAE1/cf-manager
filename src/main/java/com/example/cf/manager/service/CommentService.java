package com.example.cf.manager.service;

import com.example.cf.manager.domain.CommentInfo;
import com.example.cf.manager.domain.PostingInfo;
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
    public void update(Long code,CommentInfoDto newcomment){
        Optional<CommentInfo> originalcomment=commentRepository.findById(code);
        originalcomment.get().setContents(newcomment.getContents());
        commentRepository.save(originalcomment.get());
    }

    public void delete(Long code){
        commentRepository.deleteById(code);
    }

    public void findAndDeleteByPosting(PostingInfo posting){
        commentRepository.deleteAllByPostinginfo(posting);
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
