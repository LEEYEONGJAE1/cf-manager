package com.example.cf.manager.service;

import com.example.cf.manager.domain.PostingInfo;
import com.example.cf.manager.domain.ProblemInfo;
import com.example.cf.manager.domain.UserInfo;
import com.example.cf.manager.dto.PostingInfoDto;
import com.example.cf.manager.dto.ProblemInfoDto;
import com.example.cf.manager.dto.UserInfoDto;
import com.example.cf.manager.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.zip.CheckedInputStream;

@RequiredArgsConstructor
@Service
public class PostingService {

    private final PostingRepository postingRepository;

    public List<PostingInfo> findAllPosting(){
        return postingRepository.findAll();
    }

    public List<PostingInfo> findPostingByKeyword(String keyword){
        return postingRepository.findByTitleLike("%"+keyword+"%");
    }

    public Optional<PostingInfo> findById(Long code){
        Optional<PostingInfo> Posting=postingRepository.findById(code);
        return Posting;
    }
    public void increaseView(Long code,int view){
        Optional<PostingInfo> temp=postingRepository.findById(code);
        temp.get().setViews(temp.get().getViews()+view);
        postingRepository.save(temp.get());
    }

    public void updatePosting(Long code,PostingInfoDto newposting,UserInfo userInfo) {
        Optional<PostingInfo> OriginalPosting = postingRepository.findById(code);
        if (postingRepository.findById(code).get().getUserinfo().getUserid().equals(userInfo.getUserid())) {
            OriginalPosting.get().setTitle(newposting.getTitle());
            OriginalPosting.get().setContents(newposting.getContents());
            postingRepository.save(OriginalPosting.get());
        }
    }

    public void deletePosting(Long code, UserInfo userInfo){
        if(postingRepository.findById(code).get().getUserinfo().getUserid().equals(userInfo.getUserid())||userInfo.getAuth().equals("ROLE_ADMIN")) {
            System.out.println("deleted");
            postingRepository.deleteById(code);
        }
    }

    public Boolean checkUserValid(Long code,UserInfo userInfo){
        return postingRepository.findById(code).get().getUserinfo().getUserid().equals(userInfo.getUserid());
    }

    public Long save(PostingInfoDto postingDto) {

        return postingRepository.save(PostingInfo.builder()
                .userinfo(postingDto.getUserinfo())
                .title(postingDto.getTitle())
                .contents(postingDto.getContents())
                .addedTime(postingDto.getAddedTime())
                .views(postingDto.getViews())
                .likes(postingDto.getLikes())
                .build()).getCode();
    }

}
