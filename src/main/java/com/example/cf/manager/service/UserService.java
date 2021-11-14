package com.example.cf.manager.service;

import com.example.cf.manager.domain.ProblemInfo;
import com.example.cf.manager.domain.UserInfo;
import com.example.cf.manager.repository.UserRepository;
import com.example.cf.manager.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

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

    public Boolean exists(String userid){
        Boolean ret=userRepository.existsByUserid(userid);
        return ret;
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