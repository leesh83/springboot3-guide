package kevin.study.springboot3.user.service;

import kevin.study.springboot3.user.domain.User;
import kevin.study.springboot3.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//UserDetailsService = 스프링시큐리티에서 사용자 정보를 가져오는 인터페이스
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    //loadUserByUsername 를 오버라이딩 하여 userRepository에서 유저정보를 가져오도록 수정
    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                             .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
