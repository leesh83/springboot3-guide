package kevin.study.springboot3.user.config.jwt;

import io.jsonwebtoken.*;
import kevin.study.springboot3.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Service
public class TokenProvider {
    private final String issuer;
    private final String secretKey;

    public TokenProvider(@Value("${jwt.issuer}") String issuer,
                         @Value("${jwt.secretKey}") String secretKey) {
        //@Value 어노테이션으로 applicationl.yml 값 조회
        this.issuer = issuer;
        this.secretKey = secretKey;
    }

    public String generateToken(User user, Duration expiredAt){
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiredAt.toMillis());
        //expiry : 현재시간 + 설정한 토큰 유효시간
        return makeToken(expiry, user);
    }

    //jwt 토큰 생성 - 헤더,내용,서명에 들어갈 정보 입력
    private String makeToken(Date expiry, User user){
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //헤더 : jwt로 설정
                .setIssuer(issuer)
                .setIssuedAt(new Date()) // 내용 iat
                .setExpiration(expiry) // 내용 exp (토큰 유효일시)
                .setSubject(user.getEmail()) // 내용 sub
                .claim("id", user.getId()) // 클레임id 로 user.id 설정 (유저 식별용)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                   // 서명 : secretKey 로 HS256 방식으로 암호화
                .compact();
    }

    //jwt 토큰 유효성 검증
    public boolean validToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
        } catch (Exception e) {
            return false; //exception 발생시 유효하지 않은 토큰으로 판단.
        }
        return true;
    }

    //토큰에서 클레임을 추출 (토큰에서 유저정보를 식별하기 위해)
    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    //토큰 클레임에서 id(userId) 조회
    private Long getUserId(String token){
        return getClaims(token)
                .get("id", Long.class);
    }

    //토큰 기반으로 인증정보를 가져오는 메소드
    public Authentication getAuthentication(String token){
        Claims claims = getClaims(token);

        Set<SimpleGrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(
                        claims.getSubject(), "", authorities),
                token, authorities);
    }



}
