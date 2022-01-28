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

@RequiredArgsConstructor
@Service
public class PostingService {

    private final PostingRepository postingRepository;

    public List<PostingInfo> findAll(){
        return postingRepository.findAll();
    }

    public List<PostingInfo> findByUserinfo(UserInfoDto userinfo){
        return postingRepository.findByUserinfo(userinfo);
    }


    public Optional<PostingInfo> findById(Long code){
        Optional<PostingInfo> temp=postingRepository.findById(code);
        return temp;
    }
    public void increaseView(Long code,int view){
        Optional<PostingInfo> temp=postingRepository.findById(code);
        temp.get().setViews(temp.get().getViews()+view);
        postingRepository.save(temp.get());
    }

    public void update(Long code,PostingInfoDto newposting){
        Optional<PostingInfo> originalposting=postingRepository.findById(code);
        originalposting.get().setTitle(newposting.getTitle());
        originalposting.get().setContents(newposting.getContents());
        postingRepository.save(originalposting.get());
    }

    public void delete(Long code, Authentication authentication){
        UserInfo me=(UserInfo) authentication.getPrincipal();
        if(me.getUserid().equals(postingRepository.findById(code).get().getUserinfo().getUserid())) {
            postingRepository.deleteById(code);
            System.out.println("deleted successfully");
        }
        System.out.println("not deleted");
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
