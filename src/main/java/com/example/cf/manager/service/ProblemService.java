package com.example.cf.manager.service;

import com.example.cf.manager.domain.ProblemInfo;
import com.example.cf.manager.domain.UserInfo;
import com.example.cf.manager.dto.ProblemInfoDto;
import com.example.cf.manager.dto.UserInfoDto;
import com.example.cf.manager.repository.ProblemRepository;
import com.example.cf.manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProblemService {
    private final ProblemRepository problemRepository;
    public List<ProblemInfo> getAllProblemsByUserInfo(UserInfo userInfo){
        return problemRepository.findByUserinfo(userInfo);
    }
    public void deleteProblemByCode(Long code){
        problemRepository.deleteById(code);
    }

    public void addBookmarkByCode(Long code){
        ProblemInfo temp=problemRepository.getById(code);
        temp.setBookmarked(Boolean.TRUE);
        problemRepository.save(temp);
    }
    public void deleteBookmarkByCode(Long code){
        ProblemInfo temp=problemRepository.getById(code);
        temp.setBookmarked(Boolean.FALSE);
        problemRepository.save(temp);
    }

    public Long save(ProblemInfoDto problemDto) {
        return problemRepository.save(ProblemInfo.builder()
                .userInfo(problemDto.getUserInfo())
                .problemName(problemDto.getProblemName())
                .problemLink(problemDto.getProblemLink())
                .addedTime(problemDto.getAddedTime())
                .bookmarked(problemDto.getBookmarked()).build()).getCode();
    }

}
