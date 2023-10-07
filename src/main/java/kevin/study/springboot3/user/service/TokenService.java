package kevin.study.springboot3.user.service;

import kevin.study.springboot3.user.config.jwt.TokenProvider;
import kevin.study.springboot3.user.domain.User;
import kevin.study.springboot3.user.dto.CreateAccessTokenRequest;
import kevin.study.springboot3.user.dto.CreateAccessTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    /**
     * 전달받은 리프레쉬 토큰의 유효성검사를 하고, 유효하다면 리프레시토큰을 통해
     * userId를 검색함. 그리고 새로운 accessToken을 발급해준다.
     */
    public CreateAccessTokenResponse createNewAccressToken(CreateAccessTokenRequest request) {
        if (!tokenProvider.validToken(request.getRefreshToken())) {
            throw new IllegalArgumentException("refreshToken is not valid.");
        }

        Long userId = refreshTokenService.findByRefreshToken(request.getRefreshToken())
                                         .getUserId();

        User user = userService.findById(userId);

        String accessToken = tokenProvider.generateToken(user, Duration.ofHours(2));

        return new CreateAccessTokenResponse(accessToken);
    }
}
