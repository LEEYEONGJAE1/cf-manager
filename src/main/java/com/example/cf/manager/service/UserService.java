package com.example.cf.manager.service;

import com.example.cf.manager.domain.UserInfo;
import com.example.cf.manager.repository.UserRepository;
import com.example.cf.manager.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.eclipse.jdt.internal.compiler.problem.ProblemSeverities.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    public static Boolean tableExistVariable=Boolean.FALSE;
    /**
     * Spring Security 필수 메소드 구현
     *
     * @param userid 아이디
     * @return UserDetails
     * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
     */
    @Override // 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 UserInfo로 반환 타입 지정 (자동으로 다운 캐스팅됨)
    public UserInfo loadUserByUsername(String userid) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
        return userRepository.findByUserid(userid)
                .orElseThrow(() -> new UsernameNotFoundException((userid)));
    }

    public Boolean isUserExistById(String userid){
        Boolean ret = userRepository.existsByUserid(userid);
        return ret;
    }

    public List<UserInfo> getAllUserInfo(){
        return userRepository.findAll();
    }

    public void deleteUser(Long code){
        userRepository.deleteById(code);
    }

    public Long save(UserInfoDto infoDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        infoDto.setPassword(encoder.encode(infoDto.getPassword()));

        return userRepository.save(UserInfo.builder()
                .userid(infoDto.getUserid())
                .cfid(infoDto.getCfid())
                .username(infoDto.getUsername())
                .auth(infoDto.getAuth())
                .password(infoDto.getPassword()).build()).getCode();
    }


}