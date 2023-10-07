package kevin.study.springboot3.user.service;

import kevin.study.springboot3.user.domain.RefreshToken;
import kevin.study.springboot3.user.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                                     .orElseThrow(() -> new IllegalArgumentException("not found refreshToken"));
    }
}
