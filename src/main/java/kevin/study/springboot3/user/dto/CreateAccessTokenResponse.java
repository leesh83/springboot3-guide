package kevin.study.springboot3.user.dto;

import lombok.Getter;
@Getter
public class CreateAccessTokenResponse {
    private String accessToken;

    public CreateAccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
