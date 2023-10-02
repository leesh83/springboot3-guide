package kevin.study.springboot3.config.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kevin.study.springboot3.user.config.jwt.JwtProperties;
import lombok.Builder;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * jwt토큰 테스트를 위한 mocking 객체
 */
public class JwtFactory {
    private String subject = "test@email.com";
    private Date issueAt = new Date();
    private Date expiration = new Date(new Date().getTime() + Duration.ofDays(14).toMillis());
    private Map<String, Object> claims = Collections.emptyMap();

    @Builder
    public JwtFactory(String subject, Date issueAt, Date expiration, Map<String, Object> claims) {
        this.subject = subject != null ? subject : this.subject;
        this.issueAt = issueAt != null ? issueAt : this.issueAt;;
        this.expiration = expiration != null ? expiration : this.expiration;;
        this.claims = claims != null ? claims : this.claims;;
    }

    public static JwtFactory withDefaultValue(){
        return JwtFactory.builder().build();
    }

    public String createToken(JwtProperties jwtProperties){
        return Jwts.builder()
                   .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //헤더 : jwt로 설정
                   .setIssuer(jwtProperties.getIssuer()) // 내용 iss - 토큰 발행자
                   .setIssuedAt(new Date()) // 내용 iat - 토큰 발행일자
                   .setExpiration(expiration) // 내용 exp 토큰 유효일시
                   .setSubject(subject) // 내용 sub
                   .addClaims(claims) // Map<> 타입으로 claim 정보를 추가할때.
                   .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey()) // 서명 : secretKey 로 HS256 방식으로 암호화
                   .compact();
    }


}


