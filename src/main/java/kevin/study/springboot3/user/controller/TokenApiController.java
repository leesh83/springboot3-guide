package kevin.study.springboot3.user.controller;

import kevin.study.springboot3.user.dto.CreateAccessTokenRequest;
import kevin.study.springboot3.user.dto.CreateAccessTokenResponse;
import kevin.study.springboot3.user.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenApiController {
    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(
            @RequestBody CreateAccessTokenRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(tokenService.createNewAccressToken(request));
    }
}
