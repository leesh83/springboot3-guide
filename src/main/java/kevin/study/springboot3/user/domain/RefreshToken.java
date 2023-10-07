package kevin.study.springboot3.user.domain;


import jakarta.persistence.*;
import lombok.Getter;

/**
 * 리프레쉬 토큰은 데이터베이스에 저장하는 정보이므로
 * entity 와 repository를 추가해야 한다.
 */
@Entity
@Getter
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "userId", nullable = false, unique = true)
    private Long userId;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    protected RefreshToken() {
    }

    public RefreshToken(Long userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    public RefreshToken update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
        return this;
    }
}
