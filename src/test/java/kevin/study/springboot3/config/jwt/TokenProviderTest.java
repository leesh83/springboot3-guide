package kevin.study.springboot3.config.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kevin.study.springboot3.user.config.jwt.JwtProperties;
import kevin.study.springboot3.user.config.jwt.TokenProvider;
import kevin.study.springboot3.user.domain.User;
import kevin.study.springboot3.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Date;

@SpringBootTest
public class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProperties jwtProperties;

    @Test
    @DisplayName("generateToken() 검증 - 유저정보와 만료기간을 전달해 토큰 생성 검증")
    void getnerateToken(){
        //given
        User testUser = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());

        //when
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));


        //then
        Long userId = Jwts.parser()
                          .setSigningKey(jwtProperties.getSecretKey())
                          .parseClaimsJws(token)
                          .getBody()
                          .get("id", Long.class);

        Assertions.assertEquals(testUser.getId(), userId,"testUser의 id와 생성된 token에서 파싱한 id가 같아야 한다.");
    }

    @Test
    @DisplayName("validToken() 검증 : 만료된 토큰일 경우 유효성 검증에 실패한다.")
    void validToken() {
        //given
        Date expireDate = new Date(new Date().getTime() - Duration.ofDays(1).toMillis());
        String token = JwtFactory.builder()
                                 .expiration(expireDate)
                                 .build()
                                 .createToken(jwtProperties);

        //when
        Boolean result = tokenProvider.validToken(token);

        //then
        Assertions.assertEquals(result, false);
    }


    @Test
    @DisplayName("getAuthentication() 검증 : 토큰기반으로 인증정보를 가져올 수 있다.")
    void getAuthentication(){

    }


}
