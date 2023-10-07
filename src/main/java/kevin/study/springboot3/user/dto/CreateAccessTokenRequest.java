package kevin.study.springboot3.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateAccessTokenRequest {
    private String refreshToken;

    public CreateAccessTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
